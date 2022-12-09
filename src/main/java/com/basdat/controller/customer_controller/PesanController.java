package com.basdat.controller.customer_controller;

import com.basdat.App;
import com.basdat.db_models.*;
import com.basdat.repository.DBConnect;
import com.basdat.util.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EventListener;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.basdat.repository.DBConnect.connectionUrl;
import static com.basdat.repository.DBConnect.getConnection;

public class PesanController implements Initializable {

    @FXML
    private Button baruBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button buyBtn;
    @FXML
    private Button backBtn;

    @FXML
    private TextField searchTF;
    @FXML
    private TextField tglTF;
    @FXML
    private TextField IdPsnTF;
    @FXML
    private TextField produkTF;
    @FXML
    private TextField kuantitasTF;

    @FXML
    private TableView<Mobil> mobilTblView;
    @FXML
    private TableColumn<Mobil, String> mobilIdClm;
    @FXML
    private TableColumn<Mobil, String> mobilNamaClm;
    @FXML
    private TableColumn<Mobil, String> mobilMerkClm;
    @FXML
    private TableColumn<Mobil, String> mobilYearClm;
    @FXML
    private TableColumn<Mobil, String> mobilHargaClm;

    @FXML
    private TableView<SukuCadang> SKTblView;
    @FXML
    private TableColumn<SukuCadang, String> SKIdClm;
    @FXML
    private TableColumn<SukuCadang, String> SKNamaClm;
    @FXML
    private TableColumn<SukuCadang, String> SKMerkClm;
    @FXML
    private TableColumn<SukuCadang, String> SKYearClm;
    @FXML
    private TableColumn<SukuCadang, String> SKHargaClm;

    @FXML
    private TableView<DaftarPesananTotal> DaftarPesananTblView;
    @FXML
    private TableColumn<DaftarPesananTotal, String> produkClm;
    @FXML
    private TableColumn<DaftarPesananTotal, String> kuantitasClm;
    @FXML
    private TableColumn<DaftarPesananTotal, String> subTotalClm;


    private ObservableList<Mobil> dataMobil = FXCollections.observableArrayList();
    private ObservableList<Mobil> selectMobil;
    private ObservableList<SukuCadang> dataSK = FXCollections.observableArrayList();
    private ObservableList<SukuCadang> selectSK;
    private ObservableList<DaftarPesananTotal> dataDaftarPesanan = FXCollections.observableArrayList();
    private ObservableList<DaftarPesananTotal> selectDaftarPesanan;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Insert Data Mobil to TableView
        updateTblMobil();

        // Insert Data Suku Cadang to TableView
        updateTblSK();

        // Search Text Field
        searchOnEnter();

        // Insert DaftarPesanan
        updateTblDaftarPesanan();
        filterDaftarPesanan();


    }

    @FXML
    private void backBtnAction() throws IOException{
        App.setRoot("fxml/customer_menu/menuCustomer");
    }

    @FXML
    private void deleteBtnAction() throws IOException{
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
    }



    @FXML
    private void baruBtnAction() throws IOException{
        insertDate();
        String ID_Pembeli = LoginCustomerController.getID_Pembeli();
        String tglPesanan = tglTF.getText().trim();
        String status = "Belum Terbayar";

        Connection con = DBConnect.getConnection();
        String query = "INSERT INTO Pesanan values (?, ?, ?, NULL, NULL)";

        try(PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, ID_Pembeli);
            ps.setString(2, tglPesanan);
            ps.setString(3, status);
            ps.executeUpdate();

            insertIdPesananTF();
            Notification.Information("Information", "PESANAN BARU DIBUAT SILAHKAN PILIH BARANG");
        }
        catch (SQLException e) {
            e.printStackTrace();
            Notification.Error("ERROR", "GAGAL MEMBUAT PESANAN FAILED");
        }

    }

    @FXML
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


    @FXML
    private void buyBtnAction() throws IOException{
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


            Notification.Information("Information", "ADD to BUY SUCCESS");
            updateTblDaftarPesanan();
        }
        catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ADD FAILED");
        }


    }


    @FXML
    private void searchOnEnter() {

        // 1. Wrap the ObservableList in a FilteredList
        FilteredList<Mobil> filteredData = new FilteredList<>(dataMobil, p -> true);
        FilteredList<SukuCadang> filteredDataSK = new FilteredList<>(dataSK, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(mobil -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(mobil.getNama().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if(mobil.getID().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if(mobil.getMerk().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;

            });

            filteredDataSK.setPredicate(SK -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(SK.getNama().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if(SK.getID().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if(SK.getMerk().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;

            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Mobil> sortedData = new SortedList<>(filteredData);
        SortedList<SukuCadang> sortedDataSK = new SortedList<>(filteredDataSK);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(mobilTblView.comparatorProperty());
        sortedDataSK.comparatorProperty().bind(SKTblView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        mobilTblView.setItems(sortedData);
        SKTblView.setItems(sortedDataSK);
    }

    private void updateTblMobil() {
        pullDBMobil();
        addDataToTableMobil();
        mobilTblView.refresh();
    }

    private void updateTblSK() {
        pullDBSK();
        addDataToTableSK();
        SKTblView.refresh();
    }

    private void updateTblDaftarPesanan() {
        pullDBDaftarPesanan();
        addDataToTableDaftarPesanan();
        DaftarPesananTblView.refresh();
    }

    private void pullDBMobil() {
        Connection con = DBConnect.getConnection();
        ResultSet resultSet;
        String query = "SELECT id_produk, nama_Produk, merk, tahun_Produksi,harga from Produk WHERE jenis_Produk = ? ORDER BY id_produk";

        try(PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, "Mobil");

            // Create and execute a SELECT SQL statement.
            resultSet = ps.executeQuery();

            // Add result to list
            while (resultSet.next()) {
                dataMobil.add(new Mobil(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void pullDBSK() {
        Connection con = getConnection();
        ResultSet resultSet;
        String query = "SELECT id_produk, nama_Produk, merk, tahun_Produksi,harga from Produk WHERE jenis_Produk = ? ORDER BY id_produk";

        try(PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, "Suku Cadang");

            // Create and execute a SELECT SQL statement.
            resultSet = ps.executeQuery();

            // Add result to list
            while (resultSet.next()) {
                dataSK.add(new SukuCadang(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));


            }

        }
        catch (SQLException e) {
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

    private void addDataToTableMobil() {
        mobilIdClm.setCellValueFactory(new PropertyValueFactory<>("ID"));
        mobilNamaClm.setCellValueFactory(new PropertyValueFactory<>("nama"));
        mobilMerkClm.setCellValueFactory(new PropertyValueFactory<>("merk"));
        mobilYearClm.setCellValueFactory(new PropertyValueFactory<>("tahun"));
        mobilHargaClm.setCellValueFactory(new PropertyValueFactory<>("harga"));

        mobilTblView.setItems(dataMobil);
    }

    private void addDataToTableSK() {
        SKIdClm.setCellValueFactory(new PropertyValueFactory<>("ID"));
        SKNamaClm.setCellValueFactory(new PropertyValueFactory<>("nama"));
        SKMerkClm.setCellValueFactory(new PropertyValueFactory<>("merk"));
        SKYearClm.setCellValueFactory(new PropertyValueFactory<>("tahun"));
        SKHargaClm.setCellValueFactory(new PropertyValueFactory<>("harga"));

        SKTblView.setItems(dataSK);
    }

    @FXML
    private void MobilTblClicked() {
        ObservableList<Mobil> Mobilselect;
        Mobilselect = mobilTblView.getSelectionModel().getSelectedItems();
        this.selectMobil = Mobilselect;
        this.selectSK = null;

    }

    @FXML
    private void MobilTblPressed() {
        ObservableList<Mobil> Mobilselect;
        Mobilselect = mobilTblView.getSelectionModel().getSelectedItems();
        this.selectMobil = Mobilselect;
        this.selectSK = null;

//        idTF.setText(Mobilselect.get(0).getID());
//        produkTF.setText(Mobilselect.get(0).getNama());
//        merkTF.setText(Mobilselect.get(0).getMerk());
//        tahunTF.setText(Mobilselect.get(0).getTahun());
//        hargaTF.setText(Mobilselect.get(0).getHarga());
    }



    private void addDataToTableDaftarPesanan() {
        produkClm.setCellValueFactory(new PropertyValueFactory<>("ID_Produk"));
        kuantitasClm.setCellValueFactory(new PropertyValueFactory<>("kuantitas"));
        subTotalClm.setCellValueFactory(new PropertyValueFactory<>("SubTotal"));

        DaftarPesananTblView.setItems(dataDaftarPesanan);
    }

    private void insertIdPesananTF() {
        IdPsnTF.clear();
        Connection con = DBConnect.getConnection();
        ResultSet resultSet;
        String ID_Pembeli = LoginCustomerController.getID_Pembeli();

        String query = "select MAX(ID_Pesanan) from Pesanan where ID_Pembeli = ?";

        try(PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, ID_Pembeli);

            // Create and execute a SELECT SQL statement.
            resultSet = ps.executeQuery();

            // Add result to list
            if (resultSet.next()) {
                IdPsnTF.setText(resultSet.getString(1));
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void insertDate() {
        tglTF.clear();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE ;
        String date = currentDate.format(formatter);
        tglTF.setText(date);

    }


}
