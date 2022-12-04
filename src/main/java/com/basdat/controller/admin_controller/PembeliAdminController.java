package com.basdat.controller.admin_controller;

import com.basdat.App;
import com.basdat.db_models.Pegawai;
import com.basdat.db_models.Pembeli;
import com.basdat.repository.DBConnect;
import com.basdat.util.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Text;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.basdat.repository.DBConnect.connectionUrl;

public class PembeliAdminController implements Initializable {

    @FXML
    private TextField searchTF;
    @FXML
    private TextField IdTF;
    @FXML
    private TextField IdAkunTF;
    @FXML
    private TextField NikTF;
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
    private TableView<Pembeli> PembeliTblView;
    @FXML
    private TableColumn<Pembeli, String> IdPmbClm;
    @FXML
    private TableColumn<Pembeli, String> IdPgnClm;
    @FXML
    private TableColumn<Pembeli, String> NikClm;
    @FXML
    private TableColumn<Pembeli, String> namaClm;
    @FXML
    private TableColumn<Pembeli, String> JkClm;
    @FXML
    private TableColumn<Pembeli, String> jalanClm;
    @FXML
    private TableColumn<Pembeli, String> kecClm;
    @FXML
    private TableColumn<Pembeli, String> kotaClm;


    private ObservableList<Pembeli> dataPembeli = FXCollections.observableArrayList();
    private ObservableList<Pembeli> selectPembeli;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTblPembeli();

        searchOnEnter();
    }

    @FXML
    private void editBtnAction() throws IOException {
        String idPgn = IdAkunTF.getText().trim();
        String idPmb = IdTF.getText().trim();
        String nama = namaTF.getText().trim();
        String NIK = NikTF.getText().trim();
        String kelamin = JkTF.getText().trim();
        String jalan = jalanTF.getText().trim();
        String kec = kecTF.getText().trim();
        String kota = kotaTF.getText().trim();

        Connection con = DBConnect.getConnection();
        String query = "UPDATE Pembeli SET Nama = ?, NIK = ?, jenis_Kelamin = ?, jalan_Pembeli = ?, kecamatan_Pembeli = ?, kota_Pembeli = ? " +
                "WHERE ID_Pembeli = ? AND ID_Pengguna = ?";

        try(PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, nama);
            ps.setString(2, NIK);
            ps.setString(3, kelamin);
            ps.setString(4, jalan);
            ps.setString(5, kec);
            ps.setString(6, kota);
            ps.setInt(7, Integer.parseInt(idPmb));
            ps.setInt(8, (Integer.parseInt(idPgn)));

            ps.executeUpdate();

            Notification.Information("Information", "EDIT SUCCESS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            Notification.Error("ERROR", "EDIT FAILED");
        }

        App.setRoot("fxml/admin_menu/pembeliAdmin");
    }

    @FXML
    private void deleteBtnAction() throws IOException {
        String idPgn = IdAkunTF.getText().trim();
        String idPmb = IdTF.getText().trim();

        Connection con = DBConnect.getConnection();
        String query = "DELETE FROM Pengguna WHERE ID_Pengguna = ?";

        try(PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, idPgn);

            ps.executeUpdate();

            Notification.Information("Information", "DELETE SUCCESS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            Notification.Error("ERROR", "DELETE FAILED");
        }

        App.setRoot("fxml/admin_menu/pembeliAdmin");
    }

    @FXML
    private void backBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/menuAdmin");
    }

    @FXML
    private void searchOnEnter() {

        // 1. Wrap the ObservableList in a FilteredList
        FilteredList<Pembeli> filteredData = new FilteredList<>(dataPembeli, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(pembeli -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(pembeli.getNama().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;

            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Pembeli> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(PembeliTblView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        PembeliTblView.setItems(sortedData);

    }

    @FXML
    private void PembeliTblClicked() {
        selectPembeli = PembeliTblView.getSelectionModel().getSelectedItems();

        IdAkunTF.setText("" + selectPembeli.get(0).getID_Pengguna());
        IdTF.setText("" + selectPembeli.get(0).getID_Pembeli());
        NikTF.setText("" + selectPembeli.get(0).getNIK());
        namaTF.setText(selectPembeli.get(0).getNama());
        JkTF.setText(selectPembeli.get(0).getJenisKelamin());
        jalanTF.setText(selectPembeli.get(0).getJalan());
        kecTF.setText(selectPembeli.get(0).getKecamatan());
        kotaTF.setText(selectPembeli.get(0).getKota());
    }

    @FXML
    private void PembeliTblPressed() {
        selectPembeli = PembeliTblView.getSelectionModel().getSelectedItems();

        IdAkunTF.setText("" + selectPembeli.get(0).getID_Pengguna());
        IdTF.setText("" + selectPembeli.get(0).getID_Pembeli());
        NikTF.setText("" + selectPembeli.get(0).getNIK());
        namaTF.setText(selectPembeli.get(0).getNama());
        JkTF.setText(selectPembeli.get(0).getJenisKelamin());
        jalanTF.setText(selectPembeli.get(0).getJalan());
        kecTF.setText(selectPembeli.get(0).getKecamatan());
        kotaTF.setText(selectPembeli.get(0).getKota());
    }

    private void updateTblPembeli() {
        pullDBPembeli();
        addDataToTablePembeli();
        PembeliTblView.refresh();
    }

    private void pullDBPembeli() {
        Connection con = DBConnect.getConnection();
        ResultSet resultSet;
        String query = "SELECT ID_Pengguna, ID_Pembeli, Nama, NIK,jenis_Kelamin, jalan_Pembeli, kecamatan_Pembeli, kota_Pembeli FROM Pembeli";

        try(PreparedStatement ps = con.prepareStatement(query)) {

            // Create and execute a SELECT SQL statement.
            resultSet = ps.executeQuery();

            // Add result to list
            while (resultSet.next()) {
                dataPembeli.add(new Pembeli(Integer.parseInt(resultSet.getString(1)),
                        Integer.parseInt(resultSet.getString(2)),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                ));

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDataToTablePembeli() {
        IdPmbClm.setCellValueFactory(new PropertyValueFactory<>("ID_Pembeli"));
        IdPgnClm.setCellValueFactory(new PropertyValueFactory<>("ID_Pengguna"));
        NikClm.setCellValueFactory(new PropertyValueFactory<>("NIK"));
        namaClm.setCellValueFactory(new PropertyValueFactory<>("nama"));
        JkClm.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        jalanClm.setCellValueFactory(new PropertyValueFactory<>("jalan"));
        kecClm.setCellValueFactory(new PropertyValueFactory<>("kecamatan"));
        kotaClm.setCellValueFactory(new PropertyValueFactory<>("kota"));

        PembeliTblView.setItems(dataPembeli);
    }

}
