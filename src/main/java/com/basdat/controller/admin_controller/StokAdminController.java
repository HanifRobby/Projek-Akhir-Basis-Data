package com.basdat.controller.admin_controller;

import com.basdat.App;
import com.basdat.db_models.Mobil;
import com.basdat.db_models.StokMobil;
import com.basdat.db_models.StokSK;
import com.basdat.db_models.SukuCadang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.basdat.repository.DBConnect.connectionUrl;

public class StokAdminController implements Initializable {

    @FXML
    private TextField IdCbgTF;
    @FXML
    private TextField IdPrdTF;
    @FXML
    private TextField stokTF;

    @FXML
    private TableView<StokMobil> mobilTblView;
    @FXML
    private TableColumn<StokMobil, String> IdCbgMblClm;
    @FXML
    private TableColumn<StokMobil, String> IdMblClm;
    @FXML
    private TableColumn<StokMobil, String> namaMblClm;
    @FXML
    private TableColumn<StokMobil, String> stokMblClm;

    @FXML
    private TableView<StokSK> SKTblView;
    @FXML
    private TableColumn<StokSK, String> IdCbgSKClm;
    @FXML
    private TableColumn<StokSK, String> IdPrdSKClm;
    @FXML
    private TableColumn<StokSK, String> namaSKClm;
    @FXML
    private TableColumn<StokSK, String> stokSKClm;


    private ObservableList<Mobil> dataMobil = FXCollections.observableArrayList();
    private ObservableList<Mobil> selectMobil;
    private ObservableList<SukuCadang> dataSK = FXCollections.observableArrayList();
    private ObservableList<SukuCadang> selectSK;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void addBtnAction() throws IOException {

    }

    @FXML
    private void editBtnAction() throws IOException {

    }

    @FXML
    private void deleteBtnAction() throws IOException {
    }

    @FXML
    private void backBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/menuAdmin");
    }

    @FXML
    private void searchOnEnter() {

    }

    @FXML
    private void mobilTblPressed() {

    }

    @FXML
    private void mobilTblClicked() {

    }

    @FXML
    private void SKTblPressed() {

    }

    @FXML
    private void SKTblClicked() {

    }

    private void updateTblStokMobil() {

    }

    private void updateTblStokSK() {


    }

    private void pullDBStokMobil() {
        ResultSet resultSet;
        String query = "SELECT id_produk, nama_Produk, merk, tahun_Produksi,harga from Produk WHERE jenis_Produk = ? ORDER BY id_produk";

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query)) {
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

    private void pullDBStokSK() {

    }


}
