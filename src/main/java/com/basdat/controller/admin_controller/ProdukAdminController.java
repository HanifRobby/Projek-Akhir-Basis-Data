package com.basdat.controller.admin_controller;

import com.basdat.App;
import com.basdat.db_models.Mobil;
import com.basdat.db_models.SukuCadang;
import com.basdat.repository.DBConnect;
import com.basdat.util.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.basdat.repository.DBConnect.getConnection;

public class ProdukAdminController implements Initializable {

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


    private ObservableList<Mobil> dataMobil = FXCollections.observableArrayList();
    private ObservableList<Mobil> selectMobil;
    private ObservableList<SukuCadang> dataSK = FXCollections.observableArrayList();
    private ObservableList<SukuCadang> selectSK;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Insert Data Mobil to TableView
        updateTblMobil();

        // Insert Data Suku Cadang to TableView
        updateTblSK();

        // Search Text Field
        searchOnEnter();

    }

    @FXML
    private void addBtnAction() throws IOException{
        String id = idTF.getText().trim();
        String produk = produkTF.getText().trim();
        String merk = merkTF.getText().trim();
        String tahun = tahunTF.getText().trim();
        String harga = hargaTF.getText().trim();

        Connection con = getConnection();
        String query = "INSERT INTO Produk values (?,?,?,?,?,?)";

        if (selectSK == null) {
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, id);
                ps.setString(2, produk);
                ps.setString(3, merk);
                ps.setString(4, "Mobil");
                ps.setString(5, tahun);
                ps.setString(6, harga);

                ps.executeUpdate();

                Notification.Information("Information", "ADD SUCCESS");
            }
            catch (SQLException e) {
                e.printStackTrace();
                Notification.Error("ERROR", "ADD FAILED");
            }
        }
        else if (selectMobil == null) {
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, id);
                ps.setString(2, produk);
                ps.setString(3, merk);
                ps.setString(4, "Suku Cadang");
                ps.setString(5, tahun);
                ps.setString(6, harga);

                ps.executeUpdate();

                Notification.Information("Information", "ADD SUCCESS");
            }
            catch (SQLException e) {
                e.printStackTrace();
                Notification.Error("ERROR", "ADD FAILED");
            }
        }
        App.setRoot("fxml/admin_menu/produkAdmin");
    }

    @FXML
    private void editBtnAction() throws IOException{
        String id = idTF.getText().trim();
        String produk = produkTF.getText().trim();
        String merk = merkTF.getText().trim();
        String tahun = tahunTF.getText().trim();
        String harga = hargaTF.getText().trim();

        Connection con = DBConnect.getConnection();
        String query = "UPDATE Produk SET nama_Produk = ?, merk = ?, tahun_Produksi = ?, harga = ? WHERE ID_Produk = ? AND jenis_Produk = ?";

        if (selectSK == null) {
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, produk);
                ps.setString(2, merk);
                ps.setString(3, tahun);
                ps.setString(4, harga);
                ps.setString(5, id);
                ps.setString(6, "Mobil");

                ps.executeUpdate();

                Notification.Information("Information", "EDIT SUCCESS");
            }
            catch (SQLException e) {
                e.printStackTrace();
                Notification.Error("ERROR", "EDIT FAILED");
            }

        }
        else if (selectMobil == null) {
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, produk);
                ps.setString(2, merk);
                ps.setString(3, tahun);
                ps.setString(4, harga);
                ps.setString(5, id);
                ps.setString(6, "Suku Cadang");

                ps.executeUpdate();

                Notification.Information("Information", "EDIT SUCCESS");
            }
            catch (SQLException e) {
                e.printStackTrace();
                Notification.Error("ERROR", "EDIT FAILED");
            }
        }
        App.setRoot("fxml/admin_menu/produkAdmin");
    }

    @FXML
    private void deleteBtnAction() throws  IOException{
        String id = idTF.getText().trim();
        String table = "Produk";
        String table1 = "Stok";

        Connection con = DBConnect.getConnection();
        String query = "DELETE FROM " + table + " WHERE ID_Produk = ? AND jenis_Produk = ?";
        String query1 = "DELETE FROM " + table1 + " WHERE ID_Produk = ?";

        if (selectSK == null) {
            try(PreparedStatement ps = con.prepareStatement(query);
                PreparedStatement ps1 = con.prepareStatement(query1)) {
                ps.setString(1, id);
                ps.setString(2, "Mobil");

                ps1.setString(1, id);

                ps.executeUpdate();
                ps1.executeUpdate();

                Notification.Information("Information", "DELETE SUCCESS");
            }
            catch (SQLException e) {
                e.printStackTrace();
                Notification.Error("ERROR", "DELETE FAILED");
            }
        }
        else if (selectMobil == null) {
            try(PreparedStatement ps = con.prepareStatement(query);
                PreparedStatement ps1 = con.prepareStatement(query1)) {
                ps.setString(1, id);
                ps.setString(2, "Suku Cadang");

                ps1.setString(1, id);

                ps.executeUpdate();
                ps1.executeUpdate();

                Notification.Information("Information", "DELETE SUCCESS");
            }
            catch (SQLException e) {
                e.printStackTrace();
                Notification.Error("ERROR", "DELETE FAILED");
            }
        }
        App.setRoot("fxml/admin_menu/produkAdmin");
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
                else return mobil.getMerk().toLowerCase().contains(lowerCaseFilter);

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
                else return SK.getMerk().toLowerCase().contains(lowerCaseFilter);

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
        App.setRoot("fxml/admin_menu/menuAdmin");
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



}


