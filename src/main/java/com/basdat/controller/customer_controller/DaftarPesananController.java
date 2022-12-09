package com.basdat.controller.customer_controller;

import com.basdat.App;
import com.basdat.db_models.*;
import com.basdat.repository.DBConnect;
import com.basdat.util.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.time.LocalDateTime;
import java.util.Date;

import static com.basdat.repository.DBConnect.connectionUrl;

public class DaftarPesananController implements Initializable {

    @FXML
    private TextField searchTF;
    @FXML
    private TextField IdPsnTF;
    @FXML
    private TextField IdPblTF;
    @FXML
    private TextField tglTF;
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
    private Label totalLabel;

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
    private TableView<DaftarPesananTotal> DaftarPesananTblView;
    @FXML
    private TableColumn<DaftarPesananTotal, String> produkClm;
    @FXML
    private TableColumn<DaftarPesananTotal, String> kuantitasClm;
    @FXML
    private TableColumn<DaftarPesananTotal, String> subTotalClm;

    @FXML
    private TableView<Pembayaran> PembayaranTbl;
    @FXML
    private TableColumn<Pembayaran, String> idPembayaranClm;
    @FXML
    private TableColumn<Pembayaran, String> tglPembayaranClm;

    @FXML
    private TableView<Total> totalTblView;
    @FXML
    private TableColumn<Total, String> totalTblClm;



    private ObservableList<Pesanan> dataPesanan = FXCollections.observableArrayList();
    private ObservableList<Pesanan> selectPesanan;
    private ObservableList<DaftarPesananTotal> dataDaftarPesanan = FXCollections.observableArrayList();
    private ObservableList<DaftarPesananTotal> selectDaftarPesanan;
    private ObservableList<Pembayaran> dataPembayaran = FXCollections.observableArrayList();
    private ObservableList<Pembayaran> selectPembayaran = FXCollections.observableArrayList();
    private ObservableList<Total> dataTotal = FXCollections.observableArrayList();
    private ObservableList<Total> selectTotal = FXCollections.observableArrayList();

    private ArrayList<Long> Total= new ArrayList<Long>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        insertDate();
        updateTblPesanan();
        updateTblDaftarPesanan();
        updateTblPembayaran();

        searchOnEnter();
        filterDaftarPesanan();
        updateTblTotal();
        filterTotal();
    }

    @FXML
    private void bayarBtnAction() throws IOException {
        String IdPesanan = IdPsnTF.getText().trim();
        String tglPesanan = tglTF.getText().trim();
        String terbayar = "Terbayar";
        String IdPembeli = LoginCustomerController.getID_Pembeli();

        Connection con = DBConnect.getConnection();
        String query = "begin transaction \n" +
                "\tINSERT INTO Pembayaran values (?) \n" +
                "\tdeclare @ID_Pembayaran numeric(18,0) \n" +
                "\tSET @ID_Pembayaran = IDENT_CURRENT('dbo.Pembayaran') \n" +
                "\tUPDATE Pesanan SET Status = ?, ID_Pembayaran = @ID_Pembayaran WHERE ID_Pesanan = ? AND ID_Pembeli = ? \n" +
                "commit transaction";

        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, tglPesanan);
            ps.setString(2, terbayar);
            ps.setString(3, IdPesanan);
            ps.setString(4, IdPembeli);
            ps.executeUpdate();

            Notification.Information("Information", "BAYAR SUCCESS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            Notification.Error("ERROR", "BAYAR FAILED");
        }

        App.setRoot("fxml/customer_menu/daftarPesanan");
    }

    @FXML
    private void backBtnAction() throws IOException {
        App.setRoot("fxml/customer_menu/menuCustomer");
    }

    @FXML
    private void printBtnAction() throws IOException{

    }

    @FXML
    private void searchOnEnter() {

        // 1. Wrap the ObservableList in a FilteredList
        FilteredList<Pesanan> filteredData = new FilteredList<>(dataPesanan, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(pesanan -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(pesanan.getID_Pesanan().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if(pesanan.getID_Pembeli().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;

            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Pesanan> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(PesananTblView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        PesananTblView.setItems(sortedData);

    }

    private void filterDaftarPesanan() {
        // 1. Wrap the ObservableList in a FilteredList
        FilteredList<DaftarPesananTotal> filteredData = new FilteredList<>(dataDaftarPesanan, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        IdPsnTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(daftarPesanan -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(daftarPesanan.getID_Pesanan().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;

            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<DaftarPesananTotal> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(DaftarPesananTblView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        DaftarPesananTblView.setItems(sortedData);
    }

    private void filterTotal() {
        totalTblView.addEventHandler(MouseEvent.MOUSE_CLICKED,mouseEvent -> {
            filterDaftarPesanan();
            updateTblTotal();});
    }

    @FXML
    private void PesananTblClicked() {
        selectPesanan = PesananTblView.getSelectionModel().getSelectedItems();

        IdPsnTF.setText(selectPesanan.get(0).getID_Pesanan());
        updateTblTotal();
    }

    @FXML
    private void PesananTblPressed() {
        selectPesanan = PesananTblView.getSelectionModel().getSelectedItems();

        IdPsnTF.setText(selectPesanan.get(0).getID_Pesanan());
        updateTblTotal();
    }

    @FXML
    private void DaftarPesananTblClicked() {
        selectDaftarPesanan = DaftarPesananTblView.getSelectionModel().getSelectedItems();

    }

    @FXML
    private void DaftarPesananTblPressed() {
        selectDaftarPesanan = DaftarPesananTblView.getSelectionModel().getSelectedItems();

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

    private void updateTblPembayaran() {
        pullDBPembayaran();
        addDataToTablePembayaran();
        PembayaranTbl.refresh();
    }

    private void updateTblTotal() {
        pullDBTotal();
        addDataToTableTotal();
        totalTblView.refresh();
    }

    private void pullDBPesanan() {
        Connection con = DBConnect.getConnection();
        ResultSet resultSet;
        String query = "SELECT ID_Pesanan, ID_Pembeli, tanggal_Pesanan, Status, ID_Pegawai, ID_Pembayaran FROM Pesanan WHERE ID_Pembeli = ? ORDER BY ID_Pesanan";

        String ID_Pembeli = LoginCustomerController.getID_Pembeli();

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, ID_Pembeli);

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

    private void pullDBPembayaran() {
        Connection con = DBConnect.getConnection();
        ResultSet resultSet;
        String query = "SELECT ps.ID_Pembeli, p.ID_Pembayaran, p.tanggal_Pembayaran\n" +
                "FROM Pesanan ps \n" +
                "JOIN Pembayaran p ON ps.ID_Pembayaran = p.ID_Pembayaran  \n" +
                "WHERE ps.ID_Pembeli = ? \n" +
                "ORDER BY ID_Pembayaran";

        String ID_Pembeli = LoginCustomerController.getID_Pembeli();

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, ID_Pembeli);

            // Create and execute a SELECT SQL statement.
            resultSet = ps.executeQuery();

            // Add result to list
            while (resultSet.next()) {
                dataPembayaran.add(new Pembayaran(resultSet.getString(1),
                        resultSet.getString(2),
                        java.sql.Date.valueOf(resultSet.getString(3))
                ));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void pullDBDaftarPesanan() {
        Connection con = DBConnect.getConnection();
        ResultSet resultSet;

        String query = "SELECT d.ID_Pesanan, d.ID_Produk, jumlah_Pembelian, d.jumlah_Pembelian*p.harga as SubTotal\n " +
                "FROM Daftar_Pesanan d \n" +
                "JOIN Produk p ON d.ID_Produk = p.ID_Produk \n"+
                "JOIN Pesanan pe on pe.ID_Pesanan = d.ID_Pesanan \n" +
                "WHERE ID_Pembeli = ? \n" +
                "ORDER BY ID_Pesanan";

        String ID_Pembeli = LoginCustomerController.getID_Pembeli();

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, ID_Pembeli);

            // Create and execute a SELECT SQL statement.
            resultSet = ps.executeQuery();


            // Add result to list
            while (resultSet.next()) {
                dataDaftarPesanan.add(new DaftarPesananTotal(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getLong(4)
                ));


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
        subTotalClm.setCellValueFactory(new PropertyValueFactory<>("SubTotal"));

        DaftarPesananTblView.setItems(dataDaftarPesanan);
    }

    private void addDataToTablePembayaran() {
        idPembayaranClm.setCellValueFactory(new PropertyValueFactory<>("ID_Pembayaran"));
        tglPembayaranClm.setCellValueFactory(new PropertyValueFactory<>("tanggal_Pembayaran"));

        PembayaranTbl.setItems(dataPembayaran);
    }

    private void pullDBTotal() {
        Connection con = DBConnect.getConnection();
        ResultSet resultSet;

        String query = "SELECT SUM(d.jumlah_Pembelian*p.harga) as Total \n" +
                    "FROM Daftar_Pesanan d \n" +
                    "JOIN Produk p ON d.ID_Produk = p.ID_Produk \n" +
                    "JOIN Pesanan pe on pe.ID_Pesanan = d.ID_Pesanan \n" +
                    "WHERE ID_Pembeli = ? and d.ID_Pesanan = ? \n" +
                    "GROUP BY d.ID_Pesanan";

        String ID_Pembeli = LoginCustomerController.getID_Pembeli();
        String ID_Pesanan = IdPsnTF.getText().trim();

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, ID_Pembeli);
            ps.setString(2, ID_Pesanan);

            // Create and execute a SELECT SQL statement.
            resultSet = ps.executeQuery();


            // Add result to list
            dataTotal.clear();
            while (resultSet.next()) {
                dataTotal.add(new Total(resultSet.getLong(1)
                ));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDataToTableTotal() {
        totalTblClm.setCellValueFactory(new PropertyValueFactory<>("Total"));

        totalTblView.setItems(dataTotal);
    }

    private void insertDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE ;
        String date = currentDate.format(formatter);
        tglTF.setText(date);

    }


}
