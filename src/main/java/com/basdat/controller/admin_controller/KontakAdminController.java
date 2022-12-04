package com.basdat.controller.admin_controller;

import com.basdat.App;
import com.basdat.db_models.NomorTelepon;
import com.basdat.db_models.Pembeli;
import com.basdat.db_models.StokMobil;
import com.basdat.db_models.StokSK;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.basdat.repository.DBConnect.connectionUrl;

public class KontakAdminController implements Initializable {


    @FXML
    private TextField searchTF;
    @FXML
    private TextField nomorTF;
    @FXML
    private TextField namaTF;
    @FXML
    private TextField IdTF;
    @FXML
    private TableView<NomorTelepon> CabangTableView;
    @FXML
    private TableColumn<NomorTelepon, String> IdCbgClm;
    @FXML
    private TableColumn<NomorTelepon, String> emailCbgClm;
    @FXML
    private TableColumn<NomorTelepon, String> numCbgClm;

    @FXML
    private TableView<NomorTelepon> PegawaiTableView;
    @FXML
    private TableColumn<NomorTelepon, String> IdPgwClm;
    @FXML
    private TableColumn<NomorTelepon, String> namaPgwClm;
    @FXML
    private TableColumn<NomorTelepon, String> numPgwClm;

    @FXML
    private TableView<NomorTelepon> PembeliTableView;
    @FXML
    private TableColumn<NomorTelepon, String> IdPmbClm;
    @FXML
    private TableColumn<NomorTelepon, String> namaPmbClm;
    @FXML
    private TableColumn<NomorTelepon, String> numPmbClm;


    private ObservableList<NomorTelepon> dataCabang = FXCollections.observableArrayList();
    private ObservableList<NomorTelepon> selectCabang;
    private ObservableList<NomorTelepon> dataPegawai = FXCollections.observableArrayList();
    private ObservableList<NomorTelepon> selectPegawai;
    private ObservableList<NomorTelepon> dataPembeli = FXCollections.observableArrayList();
    private ObservableList<NomorTelepon> selectPembeli;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTblCabang();
        updateTblPegawai();
        updateTblPembeli();

        searchOnEnter();

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
    private void mobilTblClicked() {

    }

    @FXML
    private void mobilTblPressed() {

    }

    @FXML
    private void CbgTblPressed() {

    }

    @FXML
    private void CbgTblClicked() {

    }

    @FXML
    private void PgwTblPressed() {

    }

    @FXML
    private void PgwTblClicked() {

    }

    @FXML
    private void PmbTblPressed() {

    }

    @FXML
    private void PmbTblClicked() {

    }

    private void updateTblCabang() {
        pullDBCabang();
        addDataToTableCabang();
    }

    private void updateTblPegawai() {
        pullDBPegawai();
        addDataToTablePegawai();
    }

    private void updateTblPembeli() {
        pullDBPembeli();
        addDataToTablePembeli();
    }


    private void pullDBCabang() {
        ResultSet resultSet;
        String query = "SELECT n.no_Cabang, email_Cabang, no_Telp FROM no_Telp_Cabang n JOIN Cabang c ON n.no_Cabang = c.no_Cabang";

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query)) {

            // Create and execute a SELECT SQL statement.
            resultSet = ps.executeQuery();

            // Add result to list
            while (resultSet.next()) {
                dataCabang.add(new NomorTelepon(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void pullDBPegawai() {
        ResultSet resultSet;
        String query = "SELECT n.ID_Pegawai, p.Nama, no_Telp FROM no_Telp_Pegawai n JOIN Pegawai p ON n.ID_Pegawai = p.ID_Pegawai";

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query)) {

            // Create and execute a SELECT SQL statement.
            resultSet = ps.executeQuery();

            // Add result to list
            while (resultSet.next()) {
                dataPegawai.add(new NomorTelepon(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void pullDBPembeli() {
        ResultSet resultSet;
        String query = "SELECT n.ID_Pembeli, p.Nama, no_Telp FROM no_Telp_Pembeli n JOIN Pembeli p ON n.ID_Pembeli = p.ID_Pembeli";

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query)) {

            // Create and execute a SELECT SQL statement.
            resultSet = ps.executeQuery();

            // Add result to list
            while (resultSet.next()) {
                dataPembeli.add(new NomorTelepon(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDataToTableCabang() {
        IdCbgClm.setCellValueFactory(new PropertyValueFactory<>("ID"));
        emailCbgClm.setCellValueFactory(new PropertyValueFactory<>("nama"));
        numCbgClm.setCellValueFactory(new PropertyValueFactory<>("nomorTelepon"));

        CabangTableView.setItems(dataCabang);
    }

    private void addDataToTablePegawai() {
        IdPgwClm.setCellValueFactory(new PropertyValueFactory<>("ID"));
        namaPgwClm.setCellValueFactory(new PropertyValueFactory<>("nama"));
        numPgwClm.setCellValueFactory(new PropertyValueFactory<>("nomorTelepon"));

        PegawaiTableView.setItems(dataPegawai);
    }

    private void addDataToTablePembeli() {
        IdPmbClm.setCellValueFactory(new PropertyValueFactory<>("ID"));
        namaPmbClm.setCellValueFactory(new PropertyValueFactory<>("nama"));
        numPmbClm.setCellValueFactory(new PropertyValueFactory<>("nomorTelepon"));

        PembeliTableView.setItems(dataPembeli);
    }


}
