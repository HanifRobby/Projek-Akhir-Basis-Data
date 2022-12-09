package com.basdat.controller.admin_controller;

import com.basdat.App;
import com.basdat.db_models.Mobil;
import com.basdat.db_models.Pegawai;
import com.basdat.db_models.SukuCadang;
import com.basdat.repository.DBConnect;
import com.basdat.util.Notification;
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

        Connection con = DBConnect.getConnection();
        String query = "SET IDENTITY_INSERT Pengguna ON " +
                "INSERT INTO pengguna(ID_Pengguna, Email, Username, Password) values (?,?,?,?) " +
                "SET IDENTITY_INSERT Pengguna OFF";
        String query1 = "INSERT INTO Pegawai values (?,?,?,?,?,?,?)";

        try(PreparedStatement ps = con.prepareStatement(query);
            PreparedStatement ps1 = con.prepareStatement(query1)) {

            con.setAutoCommit(false);

            ps.setString(1, (""+(Integer.parseInt(idPgn) + 2)));
            ps.setString(2, (nama + "123@gmail.com"));
            ps.setString(3, (nama + "123"));
            ps.setString(4, (nama+  123));

            ps.executeUpdate();

            ps1.setInt(1, Integer.parseInt(idPgn));
            ps1.setString(2, nama);
            ps1.setString(3, kelamin);
            ps1.setString(4, jalan);
            ps1.setString(5, kec);
            ps1.setString(6, kota);
            ps1.setInt(7, Integer.parseInt(cabang));

            ps1.executeUpdate();

            con.commit();

            Notification.Information("Information", "ADD SUCCESS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                }
                catch (Exception ex) {
                    System.out.println("Rollback Failed");
                }
                System.out.println("Rollback Succes");
            }
            Notification.Error("ERROR", "ADD FAILED");
        }
        App.setRoot("fxml/admin_menu/pegawaiAdmin");
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

        Connection con = DBConnect.getConnection();
        String query = "UPDATE Pegawai SET Nama = ?, jenis_Kelamin = ?, jalan_Pegawai = ?, kecamatan_Pegawai = ?, kota_Pegawai = ?, no_cabang = ? " +
                "WHERE ID_Pegawai = ? AND ID_Pengguna = ?";

        try(PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, nama);
            ps.setString(2, kelamin);
            ps.setString(3, jalan);
            ps.setString(4, kec);
            ps.setString(5, kota);
            ps.setInt(6, Integer.parseInt(cabang));
            ps.setInt(7, Integer.parseInt(idPgw));
            ps.setInt(8, Integer.parseInt(idPgn));

            ps.executeUpdate();

            Notification.Information("Information", "EDIT SUCCES");
        }
        catch (SQLException e) {
            e.printStackTrace();
            Notification.Error("ERROR", "EDIT FAILED");
        }

        App.setRoot("fxml/admin_menu/pegawaiAdmin");
    }

    @FXML
    private void deleteBtnAction() throws IOException {
        String idPgn = IdAkunTF.getText().trim();
        String idPgw = IdTF.getText().trim();

        Connection con = DBConnect.getConnection();
        String query = "UPDATE Pesanan SET ID_Pegawai = ? WHERE ID_Pegawai = ?";
        String query1 = "DELETE FROM Pegawai WHERE ID_Pengguna = ?";
        String query2 = "DELETE FROM Pengguna WHERE ID_Pengguna = ?";

        try(PreparedStatement ps = con.prepareStatement(query);
            PreparedStatement ps1 = con.prepareStatement(query1);
            PreparedStatement ps2 = con.prepareStatement(query2)) {
            con.setAutoCommit(false);

            ps.setNull(1, Types.INTEGER);
            ps.setString(2, idPgw);
            ps.executeUpdate();

            ps1.setInt(1, Integer.parseInt(idPgn));
            ps1.executeUpdate();

            ps2.setInt(1, Integer.parseInt(idPgn));
            ps2.executeUpdate();

            con.commit();
            Notification.Information("Information", "DELETE SUCCESS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                }
                catch (Exception ex) {
                    System.out.println("Rollback Failed");
                }
                System.out.println("Rollback Succes");
            }
            Notification.Error("ERROR", "DELETE FAILED");
        }

        App.setRoot("fxml/admin_menu/pegawaiAdmin");
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
    private void PegawaiTblClicked() {
        selectPegawai = PegawaiTblView.getSelectionModel().getSelectedItems();

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
    private void PegawaiTblPressed() {
        selectPegawai = PegawaiTblView.getSelectionModel().getSelectedItems();

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
        addDataToTablePegawai();
        PegawaiTblView.refresh();
    }

    private void pullDBPegawai() {
        Connection con = DBConnect.getConnection();
        ResultSet resultSet;
        String query = "SELECT ID_Pengguna, ID_Pegawai, Nama, jenis_Kelamin, jalan_Pegawai, kecamatan_Pegawai, kota_Pegawai, no_Cabang FROM Pegawai";

        try(PreparedStatement ps = con.prepareStatement(query)) {

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

    private void addDataToTablePegawai() {
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
