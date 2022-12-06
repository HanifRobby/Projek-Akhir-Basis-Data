package com.basdat.controller.admin_controller;

import com.basdat.App;
import com.basdat.db_models.DaftarPesanan;
import com.basdat.db_models.Pesanan;
import com.basdat.repository.DBConnect;
import com.basdat.util.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.basdat.repository.DBConnect.getConnection;

public class PesananAdminController implements Initializable {

    @FXML
    private TextField searchTF;
    @FXML
    private TextField IdPsnTF;
    @FXML
    private TextField IdPblTF;
    @FXML
    private TextField TglTF;
    @FXML
    private TextField statTF;
    @FXML
    private TextField IdPgwTF;
    @FXML
    private TextField IdPmbTF;
    @FXML
    private TextField produkTF;
    @FXML
    private TextField kuantitasTF;

    @FXML
    private TableView<Pesanan> PesananTblView;
    @FXML
    private TableColumn<Pesanan, String> IdPsnClm;
    @FXML
    private TableColumn<Pesanan, String> IdPblClm;
    @FXML
    private TableColumn<Pesanan, String> TglClm;
    @FXML
    private TableColumn<Pesanan, String> StatClm;
    @FXML
    private TableColumn<Pesanan, String> IdPgwClm;
    @FXML
    private TableColumn<Pesanan, String> IdPmbClm;

    @FXML
    private TableView<DaftarPesanan> DaftarPesananTblView;
    @FXML
    private TableColumn<DaftarPesanan, String> produkClm;
    @FXML
    private TableColumn<DaftarPesanan, String> kuantitasClm;


    private ObservableList<Pesanan> dataPesanan = FXCollections.observableArrayList();
    private ObservableList<Pesanan> selectPesanan;
    private ObservableList<DaftarPesanan> dataDaftarPesanan = FXCollections.observableArrayList();
    private ObservableList<DaftarPesanan> selectDaftarPesanan;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTblPesanan();
        updateTblDaftarPesanan();

        searchOnEnter();
        filterDaftarPesanan();
    }

    @FXML
    private void serveBtnAction() throws IOException {
        String IdPesanan = IdPsnTF.getText().trim();
        String ID_Pegawai = LoginAdminController.getID_Pegawai();

        Connection con = DBConnect.getConnection();
        String query = "UPDATE Pesanan SET ID_Pegawai = ? WHERE ID_Pesanan = ?";

        try(PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, ID_Pegawai);
            ps.setString(2, IdPesanan);

            ps.executeUpdate();

            Notification.Information("Information", "SUCCESS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            Notification.Error("ERROR", "FAILED");
        }

        App.setRoot("fxml/admin_menu/PesananAdmin");
    }

    @FXML
    private void deleteBtnAction() throws IOException {
        String IdPesanan = IdPsnTF.getText().trim();
        String IdProduk = produkTF.getText().trim();

        Connection con = DBConnect.getConnection();
        String query = "DELETE FROM Daftar_Pesanan WHERE ID_Pesanan = ? AND ID_Produk = ?";

        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, IdPesanan);
            ps.setString(2, IdProduk);

            ps.executeUpdate();

            Notification.Information("Information", "DELETE SUCCESS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            Notification.Error("ERROR", "DELETE FAILED");
        }

        App.setRoot("fxml/admin_menu/PesananAdmin");
    }

    @FXML
    private void addBtnAction() throws IOException {
        String IdPesanan = IdPsnTF.getText().trim();
        String IdProduk = produkTF.getText().trim();
        String kuantitas = kuantitasTF.getText().trim();

        Connection con = DBConnect.getConnection();
        String query = "INSERT INTO Daftar_Pesanan values (?,?,?)";

        try(PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, IdPesanan);
            ps.setString(2, IdProduk);
            ps.setString(3, kuantitas);

            ps.executeUpdate();

            Notification.Information("Information", "ADD SUCCESS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ADD FAILED");
        }

        App.setRoot("fxml/admin_menu/PesananAdmin");
    }

    @FXML
    private void editBtnAction() throws IOException {
        String IdPesanan = IdPsnTF.getText().trim();
        String IdProduk = produkTF.getText().trim();
        String kuantitas = kuantitasTF.getText().trim();

        Connection con = DBConnect.getConnection();
        String query = "UPDATE Daftar_Pesanan SET jumlah_Pembelian WHERE ID_Pesanan = ? AND ID_Produk = ?";

        try(PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, kuantitas);
            ps.setString(2, IdPesanan);
            ps.setString(3, IdProduk);

            ps.executeUpdate();

            Notification.Information("Information", "EDIT SUCCESS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            Notification.Error("ERROR", "EDIT FAILED");
        }

        App.setRoot("fxml/admin_menu/PesananAdmin");
    }

    @FXML
    private void deleteBtnAction1() throws IOException {
        String IdPesanan = IdPsnTF.getText().trim();

        Connection con = getConnection();
        String query = "DELETE FROM Pesanan WHERE ID_Pesanan = ?";

        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, IdPesanan);

            ps.executeUpdate();

            Notification.Information("Information", "DELETE SUCCESS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            Notification.Error("ERROR", "DELETE FAILED");
        }

        App.setRoot("fxml/admin_menu/PesananAdmin");
    }

    @FXML
    private void backBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/menuAdmin");
    }

    @FXML
    private void searchOnEnter() {

        // 1. Wrap the ObservableList in a FilteredList
        FilteredList<Pesanan> filteredData = new FilteredList<>(dataPesanan, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(pesanan -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            if(pesanan.getID_Pesanan().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            else return pesanan.getID_Pembeli().toLowerCase().contains(lowerCaseFilter);

        }));

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Pesanan> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(PesananTblView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        PesananTblView.setItems(sortedData);

    }

    private void filterDaftarPesanan() {
        // 1. Wrap the ObservableList in a FilteredList
        FilteredList<DaftarPesanan> filteredData = new FilteredList<>(dataDaftarPesanan, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        IdPsnTF.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(daftarPesanan -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            return daftarPesanan.getID_Pesanan().contains(lowerCaseFilter);

        }));

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<DaftarPesanan> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(DaftarPesananTblView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        DaftarPesananTblView.setItems(sortedData);
    }

    @FXML
    private void PesananTblClicked() {
        selectPesanan = PesananTblView.getSelectionModel().getSelectedItems();

        IdPsnTF.setText(selectPesanan.get(0).getID_Pesanan());
        IdPblTF.setText(selectPesanan.get(0).getID_Pembeli());
        TglTF.setText(selectPesanan.get(0).getTanggal_Pesanan().toString());
        statTF.setText(selectPesanan.get(0).getStatus());
        IdPgwTF.setText(selectPesanan.get(0).getID_Pegawai());
        IdPmbTF.setText(selectPesanan.get(0).getID_Pembayaran());

        produkTF.clear();
        kuantitasTF.clear();
    }

    @FXML
    private void PesananTblPressed() {
        selectPesanan = PesananTblView.getSelectionModel().getSelectedItems();

        IdPsnTF.setText(selectPesanan.get(0).getID_Pesanan());
        IdPblTF.setText(selectPesanan.get(0).getID_Pembeli());
        TglTF.setText(selectPesanan.get(0).getTanggal_Pesanan().toString());
        statTF.setText(selectPesanan.get(0).getStatus());
        IdPgwTF.setText(selectPesanan.get(0).getID_Pegawai());
        IdPmbTF.setText(selectPesanan.get(0).getID_Pembayaran());

        produkTF.clear();
        kuantitasTF.clear();
    }

    @FXML
    private void DaftarPesananTblClicked() {
        selectDaftarPesanan = DaftarPesananTblView.getSelectionModel().getSelectedItems();

        produkTF.setText(selectDaftarPesanan.get(0).getID_Produk());
        kuantitasTF.setText("" + selectDaftarPesanan.get(0).getKuantitas());
    }

    @FXML
    private void DaftarPesananTblPressed() {
        selectDaftarPesanan = DaftarPesananTblView.getSelectionModel().getSelectedItems();

        produkTF.setText(selectDaftarPesanan.get(0).getID_Produk());
        kuantitasTF.setText("" + selectDaftarPesanan.get(0).getKuantitas());
    }

    private void updateTblPesanan() {
        pullDBPesanan();
        addDataToTablePesanan();
        PesananTblView.refresh();
    }

    private void updateTblDaftarPesanan() {
        pullDBDaftarPesanan();
        addDataToTableDaftarPesanan();
        DaftarPesananTblView.refresh();
    }

    private void pullDBPesanan() {
        Connection con = DBConnect.getConnection();
        ResultSet resultSet;
        String query = "SELECT ID_Pesanan, ID_Pembeli, tanggal_Pesanan, Status, ID_Pegawai, ID_Pembayaran FROM Pesanan ORDER BY ID_Pesanan";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            // Create and execute a SELECT SQL statement.
            resultSet = ps.executeQuery();

            // Add result to list
            while (resultSet.next()) {
                dataPesanan.add(new Pesanan(resultSet.getString(1),
                        resultSet.getString(2),
                        java.sql.Date.valueOf(resultSet.getString(3)),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                ));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void pullDBDaftarPesanan() {
        Connection con = DBConnect.getConnection();
        ResultSet resultSet;

        String query = "SELECT ID_Pesanan, ID_Produk, SUM(jumlah_Pembelian) as jumlah_Pembelian FROM Daftar_Pesanan \n " +
                "GROUP BY ID_Pesanan, ID_Produk\n " +
                "ORDER BY ID_Pesanan";
        String query1 = "DELETE FROM Daftar_Pesanan";
        String query2 = "INSERT INTO Daftar_Pesanan values (?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(query);
        PreparedStatement ps1 = con.prepareStatement(query1);
        PreparedStatement ps2 = con.prepareStatement(query2)) {

            // Create and execute a SELECT SQL statement.
            resultSet = ps.executeQuery();

            // Add result to list
            List<DaftarPesanan> entities = new ArrayList<>();
            while (resultSet.next()) {
                dataDaftarPesanan.add(new DaftarPesanan(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                ));

                DaftarPesanan entity = new DaftarPesanan();
                entity.setID_Pesanan(resultSet.getString(1));
                entity.setID_Produk(resultSet.getString(2));
                entity.setKuantitas(resultSet.getInt(3));

                entities.add(entity);
            }

            ps1.executeUpdate();

            for (DaftarPesanan daftarPesanan : entities) {
                ps2.setString(1, daftarPesanan.getID_Pesanan());
                ps2.setString(2, daftarPesanan.getID_Produk());
                ps2.setInt(3, daftarPesanan.getKuantitas());

                ps2.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDataToTablePesanan() {
        IdPsnClm.setCellValueFactory(new PropertyValueFactory<>("ID_Pesanan"));
        IdPblClm.setCellValueFactory(new PropertyValueFactory<>("ID_Pembeli"));
        TglClm.setCellValueFactory(new PropertyValueFactory<>("tanggal_Pesanan"));
        StatClm.setCellValueFactory(new PropertyValueFactory<>("status"));
        IdPgwClm.setCellValueFactory(new PropertyValueFactory<>("ID_Pegawai"));
        IdPmbClm.setCellValueFactory(new PropertyValueFactory<>("ID_Pembayaran"));

        PesananTblView.setItems(dataPesanan);
    }

    private void addDataToTableDaftarPesanan() {
        produkClm.setCellValueFactory(new PropertyValueFactory<>("ID_Produk"));
        kuantitasClm.setCellValueFactory(new PropertyValueFactory<>("kuantitas"));

        DaftarPesananTblView.setItems(dataDaftarPesanan);
    }

}