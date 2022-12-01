package com.basdat.controller.admin_controller;

import com.basdat.App;
import com.basdat.db_models.Cabang;
import com.basdat.db_models.Pegawai;
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

import javax.swing.*;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.basdat.repository.DBConnect.connectionUrl;

public class CabangAdminController implements Initializable {

    @FXML
    private TextField searchTF;
    @FXML
    private TextField IdTF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField jalanTF;
    @FXML
    private TextField kecTF;
    @FXML
    private TextField kotaTF;
    @FXML
    private TextField kptsTF;

    @FXML
    private TableView<Cabang> cabangTblView;
    @FXML
    private TableColumn<Cabang,String> IdCbgClm;
    @FXML
    private TableColumn<Cabang,String> emailClm;
    @FXML
    private TableColumn<Cabang,String> jalanClm;
    @FXML
    private TableColumn<Cabang,String> kecClm;
    @FXML
    private TableColumn<Cabang,String> kotaClm;
    @FXML
    private TableColumn<Cabang,String> kptsClm;


    private ObservableList<Cabang> dataCabang = FXCollections.observableArrayList();
    private ObservableList<Cabang> selectCabang;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTblCabang();

        searchOnEnter();

    }

    @FXML
    private void addBtnAction() throws IOException {
        int noCabang = Integer.parseInt(IdTF.getText().trim());
        String email = emailTF.getText().trim();
        String jalan = jalanTF.getText().trim();
        String kec = kecTF.getText().trim();
        String kota = kotaTF.getText().trim();
        int kapasitas = Integer.parseInt(kptsTF.getText().trim());
        String table = "Cabang";

        String query = "INSERT INTO " + table + " values (?,?,?,?,?,?)";

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, noCabang);
            ps.setString(2, email);
            ps.setString(3, jalan);
            ps.setString(4, kec);
            ps.setString(5, kota);
            ps.setInt(6, kapasitas);

            ps.executeUpdate();
            updateTblCabang();

            JOptionPane.showMessageDialog(null, "ADD SUCCESS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ADD FAILED");
        }

        App.setRoot("fxml/admin_menu/cabangAdmin");
    }

    @FXML
    private void editBtnAction() throws IOException {
        int noCabang = Integer.parseInt(IdTF.getText().trim());
        String email = emailTF.getText().trim();
        String jalan = jalanTF.getText().trim();
        String kec = kecTF.getText().trim();
        String kota = kotaTF.getText().trim();
        int kapasitas = Integer.parseInt(kptsTF.getText().trim());
        String table = "Cabang";

        String query = "UPDATE " + table + "SET email_Cabang = ?, jalan_Cabang = ?, kecamatan_Cabang = ?, kota_Cabang = ?, kapasitas = ? WHERE no_Cabang = ?";

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, jalan);
            ps.setString(3, kec);
            ps.setString(4, kota);
            ps.setInt(5, kapasitas);
            ps.setInt(6, noCabang);

            ps.executeUpdate();
            updateTblCabang();

            JOptionPane.showMessageDialog(null, "EDIT SUCCESS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "EDIT FAILED");
        }

        App.setRoot("fxml/admin_menu/cabangAdmin");
    }

    @FXML
    private void deleteBtnAction() throws IOException {
        int noCabang = Integer.parseInt(IdTF.getText().trim());
        String table = "Cabang";
        String table1 = "Stok";

        String query = "DELETE FROM " + table + " WHERE no_Cabang = ?";
        String query1 = "DELETE FROM " + table1 + " WHERE ID_Cabang = ?";

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            Connection connection1 = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query);
            PreparedStatement ps1 = connection1.prepareStatement(query1)) {
            ps.setInt(1, noCabang);

            ps1.setInt(1, noCabang);

            ps1.executeUpdate();

            ps.executeUpdate();
            updateTblCabang();

            JOptionPane.showMessageDialog(null, "DELETE SUCCESS");
        }
        catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "DELETE FAILED");
        }

        App.setRoot("fxml/admin_menu/cabangAdmin");
    }

    @FXML
    private void backBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/menuAdmin");
    }

    @FXML
    private void searchOnEnter() {

        // 1. Wrap the ObservableList in a FilteredList
        FilteredList<Cabang> filteredData = new FilteredList<>(dataCabang, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(cabang -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(String.valueOf(cabang.getNoCabang()).contains(lowerCaseFilter)) {
                    return true;
                }
                else if(cabang.getKota().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if(cabang.getKecamatan().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;

            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Cabang> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(cabangTblView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        cabangTblView.setItems(sortedData);

    }

    @FXML
    private void CabangTblClicked() {
        selectCabang = cabangTblView.getSelectionModel().getSelectedItems();

        IdTF.setText("" + selectCabang.get(0).getNoCabang());
        emailTF.setText(selectCabang.get(0).getEmail());
        jalanTF.setText(selectCabang.get(0).getJalan());
        kecTF.setText(selectCabang.get(0).getKecamatan());
        kotaTF.setText(selectCabang.get(0).getKota());
        kptsTF.setText("" + selectCabang.get(0).getKapasitas());
    }

    @FXML
    private void CabangTblPressed() {
        selectCabang = cabangTblView.getSelectionModel().getSelectedItems();

        IdTF.setText("" + selectCabang.get(0).getNoCabang());
        emailTF.setText(selectCabang.get(0).getEmail());
        jalanTF.setText(selectCabang.get(0).getJalan());
        kecTF.setText(selectCabang.get(0).getKecamatan());
        kotaTF.setText(selectCabang.get(0).getKota());
        kptsTF.setText("" + selectCabang.get(0).getKapasitas());
    }

    private void updateTblCabang() {
        pullDBCabang();
        addToTableCabang();
        cabangTblView.refresh();
    }

    private void pullDBCabang() {
        ResultSet resultSet;
        String query = "SELECT no_Cabang, email_Cabang, jalan_Cabang, kecamatan_Cabang, kota_Cabang, kapasitas FROM Cabang";

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query)) {

            // Create and execute a SELECT SQL statement.
            resultSet = ps.executeQuery();

            // Add result to list
            while (resultSet.next()) {
                dataCabang.add(new Cabang(Integer.parseInt(resultSet.getString(1)),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        Integer.parseInt(resultSet.getString(6))
                ));

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addToTableCabang() {
        IdCbgClm.setCellValueFactory(new PropertyValueFactory<>("noCabang"));
        emailClm.setCellValueFactory(new PropertyValueFactory<>("email"));;
        jalanClm.setCellValueFactory(new PropertyValueFactory<>("jalan"));;
        kecClm.setCellValueFactory(new PropertyValueFactory<>("kecamatan"));;
        kotaClm.setCellValueFactory(new PropertyValueFactory<>("kota"));;
        kptsClm.setCellValueFactory(new PropertyValueFactory<>("kapasitas"));;

        cabangTblView.setItems(dataCabang);
    }

}
