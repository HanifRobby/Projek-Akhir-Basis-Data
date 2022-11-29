package com.basdat.controller;

import com.basdat.App;
import com.basdat.db_models.Mobil;
import com.basdat.db_models.SukuCadang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.EventListener;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.basdat.repository.DBConnect.AltConnectionUrl;
import static com.basdat.repository.DBConnect.connectionUrl;

public class StokAdminController implements Initializable {

    @FXML
    private TextField searchTF;
    @FXML
    private TextField idTF;
    @FXML
    private TextField produkTF;
    @FXML
    private TextField merkTF;
    @FXML
    private TextField tahunTF;
    @FXML
    private TextField stokTF;
    @FXML
    private TextField hargaTF;

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
    private TableColumn<Mobil, String> mobilStokClm;
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
    private TableColumn<SukuCadang, String> SKStokClm;
    @FXML
    private TableColumn<SukuCadang, String> SKHargaClm;


    private ObservableList<Mobil> dataMobil = FXCollections.observableArrayList();
    private ObservableList<Mobil> selectMobil;
    private ObservableList<SukuCadang> dataSK = FXCollections.observableArrayList();
    private ObservableList<SukuCadang> selectSK;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Insert Data Mobil to TableView
        pullDBMobil();
        addDataToTableMobil();

        // Insert Data Suku Cadang to TableView
        pullDBSK();
        addDataToTableSK();

        // Search Text Field
        searchOnEnter();

    }

    @FXML
    private void addBtnAction() {

    }

    @FXML
    private void editBtnAction() {

    }

    @FXML
    private void deleteBtnAction() {

    }

    @FXML
    private void mobilTblClicked() {
        ObservableList<Mobil> Mobilselect;
        Mobilselect = mobilTblView.getSelectionModel().getSelectedItems();
        this.selectMobil = Mobilselect;
        this.selectSK = null;

        idTF.setText(Mobilselect.get(0).getID());
        produkTF.setText(Mobilselect.get(0).getNama());
        merkTF.setText(Mobilselect.get(0).getMerk());
        tahunTF.setText(Mobilselect.get(0).getTahun());
        stokTF.setText(Mobilselect.get(0).getHarga());
        hargaTF.setText(Mobilselect.get(0).getHarga());
    }

    @FXML
    private void mobilTblPressed() {
        ObservableList<Mobil> Mobilselect;
        Mobilselect = mobilTblView.getSelectionModel().getSelectedItems();
        this.selectMobil = Mobilselect;
        this.selectSK = null;

        idTF.setText(Mobilselect.get(0).getID());
        produkTF.setText(Mobilselect.get(0).getNama());
        merkTF.setText(Mobilselect.get(0).getMerk());
        tahunTF.setText(Mobilselect.get(0).getTahun());
        stokTF.setText(Mobilselect.get(0).getHarga());
        hargaTF.setText(Mobilselect.get(0).getHarga());
    }

    @FXML
    private void SKTblClicked() {
        ObservableList<SukuCadang> SKselect;
        SKselect = SKTblView.getSelectionModel().getSelectedItems();
        this.selectSK = SKselect;
        this.selectMobil = null;

        idTF.setText(SKselect.get(0).getID());
        produkTF.setText(SKselect.get(0).getNama());
        merkTF.setText(SKselect.get(0).getMerk());
        tahunTF.setText(SKselect.get(0).getTahun());
        stokTF.setText(SKselect.get(0).getHarga());
        hargaTF.setText(SKselect.get(0).getHarga());

    }

    @FXML
    private void SKTblPressed() {
        ObservableList<SukuCadang> SKselect;
        SKselect = SKTblView.getSelectionModel().getSelectedItems();
        this.selectSK = SKselect;
        this.selectMobil = null;

        idTF.setText(SKselect.get(0).getID());
        produkTF.setText(SKselect.get(0).getNama());
        merkTF.setText(SKselect.get(0).getMerk());
        tahunTF.setText(SKselect.get(0).getTahun());
        stokTF.setText(SKselect.get(0).getHarga());
        hargaTF.setText(SKselect.get(0).getHarga());
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

    @FXML
    private void backBtnAction() throws IOException{
        App.setRoot("menuAdmin");
    }


    private void pullDBMobil() {
        ResultSet resultSet;

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            Statement statement = connection.createStatement()) {

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT id_produk, nama_Produk, merk, tahun_Produksi, harga from Produk WHERE jenis_Produk='Mobil' ORDER BY id_produk";
            resultSet = statement.executeQuery(selectSql);

            // Add result to list
            while (resultSet.next()) {
                dataMobil.add(new Mobil(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        "Rp" + resultSet.getString(5)
                        ));

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void pullDBSK() {
        ResultSet resultSet;

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            Statement statement = connection.createStatement()) {

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT id_produk, nama_Produk, merk, tahun_Produksi, harga from Produk WHERE jenis_Produk = 'Suku Cadang' ORDER BY id_produk";
            resultSet = statement.executeQuery(selectSql);

            // Add result to list
            while (resultSet.next()) {
                dataSK.add(new SukuCadang(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        "Rp" + resultSet.getString(5)
                ));

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDataToTableMobil() {
        mobilIdClm.setCellValueFactory(new PropertyValueFactory<>("ID"));
        mobilNamaClm.setCellValueFactory(new PropertyValueFactory<>("nama"));
        mobilMerkClm.setCellValueFactory(new PropertyValueFactory<>("merk"));
        mobilYearClm.setCellValueFactory(new PropertyValueFactory<>("tahun"));
        mobilStokClm.setCellValueFactory(new PropertyValueFactory<>(""));
        mobilHargaClm.setCellValueFactory(new PropertyValueFactory<>("harga"));

        mobilTblView.setItems(dataMobil);
    }

    private void addDataToTableSK() {
        SKIdClm.setCellValueFactory(new PropertyValueFactory<>("ID"));
        SKNamaClm.setCellValueFactory(new PropertyValueFactory<>("nama"));
        SKMerkClm.setCellValueFactory(new PropertyValueFactory<>("merk"));
        SKYearClm.setCellValueFactory(new PropertyValueFactory<>("tahun"));
        SKStokClm.setCellValueFactory(new PropertyValueFactory<>(""));
        SKHargaClm.setCellValueFactory(new PropertyValueFactory<>("harga"));

        SKTblView.setItems(dataSK);
    }



}


