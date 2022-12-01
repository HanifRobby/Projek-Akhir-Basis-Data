package com.basdat.controller.admin_controller;

import com.basdat.App;
import com.basdat.db_models.Mobil;
import com.basdat.db_models.Pegawai;
import com.basdat.db_models.SukuCadang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.basdat.repository.DBConnect.connectionUrl;

public class PegawaiController implements Initializable {

    @FXML
    private TextField searchTF;
    @FXML
    private TextField IdAkunTF;
    @FXML
    private TextField IdTF;
    @FXML
    private TextField namaTF;
    @FXML
    private TextField JkTF;
    @FXML
    private TextField jalanTF;
    @FXML
    private TextField kecTF;
    @FXML
    private TextField kotaTF;
    @FXML
    private TextField NcTF;

    @FXML
    private TableView<Pegawai> PegawaiTblView;
    @FXML
    private TableColumn<Pegawai, String> IdPgnClm;
    @FXML
    private TableColumn<Pegawai, String> IdPgwClm;
    @FXML
    private TableColumn<Pegawai, String> namaClm;
    @FXML
    private TableColumn<Pegawai, String> JkClm;
    @FXML
    private TableColumn<Pegawai, String> jalanClm;
    @FXML
    private TableColumn<Pegawai, String> kecClm;
    @FXML
    private TableColumn<Pegawai, String> kotaClm;
    @FXML
    private TableColumn<Pegawai, String> NcClm;

    private ObservableList<Pegawai> dataPegawai = FXCollections.observableArrayList();
    private ObservableList<Pegawai> selectPegawai;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTblPegawai();

        searchOnEnter();

    }

    @FXML
    private void addBtnAction() throws IOException{
        String idPgn = IdAkunTF.getText().trim();
        String idPgw = IdTF.getText().trim();
        String nama = namaTF.getText().trim();
        String kelamin = JkTF.getText().trim();
        String jalan = jalanTF.getText().trim();
        String kec = kecTF.getText().trim();
        String kota = kotaTF.getText().trim();
        String cabang = NcTF.getText().trim();


        try(Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Pegawai values (?,?,?,?,?,?,?,?)")) {
            ps.setInt(1, Integer.parseInt(idPgn));
            ps.setInt(2, Integer.parseInt(idPgw));
            ps.setString(3, nama);
            ps.setString(4, kelamin);
            ps.setString(5, jalan);
            ps.setString(6, kec);
            ps.setString(7, kota);
            ps.setInt(8, Integer.parseInt(cabang));

            ps.executeUpdate();
            updateTblPegawai();

            JOptionPane.showMessageDialog(null, "ADD SUCCESS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ADD FAILED");
        }

        App.setRoot("fxml/admin_menu/pegawai");
    }

    @FXML
    private void editBtnAction() throws IOException{
        String idPgn = IdAkunTF.getText().trim();
        String idPgw = IdTF.getText().trim();
        String nama = namaTF.getText().trim();
        String kelamin = JkTF.getText().trim();
        String jalan = jalanTF.getText().trim();
        String kec = kecTF.getText().trim();
        String kota = kotaTF.getText().trim();
        String cabang = NcTF.getText().trim();
        String table = "Pegawai";


        try(Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement("UPDATE " + table + " SET ID_Pengguna = ?, Nama = ?, jenis_Kelamin = ?, jalan_Pegawai = ?, kecamatan_Pegawai = ?, kota_Pegawai = ?, no_cabang = ? WHERE ID_Pegawai = ?")) {
            ps.setInt(1, Integer.parseInt(idPgn));
            ps.setString(2, nama);
            ps.setString(3, kelamin);
            ps.setString(4, jalan);
            ps.setString(5, kec);
            ps.setString(6, kota);
            ps.setInt(7, Integer.parseInt(cabang));
            ps.setInt(8, Integer.parseInt(idPgw));

            ps.executeUpdate();
            updateTblPegawai();

            JOptionPane.showMessageDialog(null, "EDIT SUCCESS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "EDIT FAILED");
        }

        App.setRoot("fxml/admin_menu/pegawai");
    }

    @FXML
    private void deleteBtnAction() throws IOException {
        String idPgw = IdTF.getText().trim();

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Pegawai WHERE ID_Pegawai = ?")) {
            ps.setInt(1, Integer.parseInt(idPgw));


            ps.executeUpdate();
            updateTblPegawai();

            JOptionPane.showMessageDialog(null, "DELETE SUCCESS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "DELETE FAILED");
        }

        App.setRoot("fxml/admin_menu/pegawai");
    }


    @FXML
    private void backBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/menuAdmin");
    }

    @FXML
    private void searchOnEnter() {

        // 1. Wrap the ObservableList in a FilteredList
        FilteredList<Pegawai> filteredData = new FilteredList<>(dataPegawai, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(pegawai -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(pegawai.getNama().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;

            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Pegawai> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(PegawaiTblView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        PegawaiTblView.setItems(sortedData);

    }

    @FXML
    private void PegawaiTblClicked(MouseEvent mouseEvent) {
        ObservableList<Pegawai> selectPegawai;
        selectPegawai = PegawaiTblView.getSelectionModel().getSelectedItems();
        this.selectPegawai = selectPegawai;

        IdAkunTF.setText("" + selectPegawai.get(0).getID_Pengguna());
        IdTF.setText("" + selectPegawai.get(0).getID_Pegawai());
        namaTF.setText(selectPegawai.get(0).getNama());
        JkTF.setText(selectPegawai.get(0).getJenisKelamin());
        jalanTF.setText(selectPegawai.get(0).getJalan());
        kecTF.setText(selectPegawai.get(0).getKecamatan());
        kotaTF.setText(selectPegawai.get(0).getKota());
        NcTF.setText("" + selectPegawai.get(0).getNo_cabang());
    }

    @FXML
    private void PegawaiTblPressed(KeyEvent keyEvent) {
        ObservableList<Pegawai> selectPegawai;
        selectPegawai = PegawaiTblView.getSelectionModel().getSelectedItems();
        this.selectPegawai = selectPegawai;

        IdAkunTF.setText("" + selectPegawai.get(0).getID_Pengguna());
        IdTF.setText("" + selectPegawai.get(0).getID_Pegawai());
        namaTF.setText(selectPegawai.get(0).getNama());
        JkTF.setText(selectPegawai.get(0).getJenisKelamin());
        jalanTF.setText(selectPegawai.get(0).getJalan());
        kecTF.setText(selectPegawai.get(0).getKecamatan());
        kotaTF.setText(selectPegawai.get(0).getKota());
        NcTF.setText("" + selectPegawai.get(0).getNo_cabang());
    }

    private void updateTblPegawai() {
        pullDBPegawai();
        addDataToTableMobil();
        PegawaiTblView.refresh();
    }

    private void pullDBPegawai() {
        ResultSet resultSet;
        String query = "SELECT ID_Pengguna, ID_Pegawai, Nama, jenis_Kelamin, jalan_Pegawai, kecamatan_Pegawai, kota_Pegawai, no_Cabang FROM Pegawai";

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query)) {

            // Create and execute a SELECT SQL statement.
            resultSet = ps.executeQuery();

            // Add result to list
            while (resultSet.next()) {
                dataPegawai.add(new Pegawai(Integer.parseInt(resultSet.getString(1)),
                        Integer.parseInt(resultSet.getString(2)),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        Integer.parseInt(resultSet.getString(8))
                ));

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDataToTableMobil() {
        IdPgnClm.setCellValueFactory(new PropertyValueFactory<>("ID_Pengguna"));
        IdPgwClm.setCellValueFactory(new PropertyValueFactory<>("ID_Pegawai"));
        namaClm.setCellValueFactory(new PropertyValueFactory<>("nama"));
        JkClm.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        jalanClm.setCellValueFactory(new PropertyValueFactory<>("jalan"));
        kecClm.setCellValueFactory(new PropertyValueFactory<>("kecamatan"));
        kotaClm.setCellValueFactory(new PropertyValueFactory<>("kota"));
        NcClm.setCellValueFactory(new PropertyValueFactory<>("no_cabang"));

        PegawaiTblView.setItems(dataPegawai);
    }


}