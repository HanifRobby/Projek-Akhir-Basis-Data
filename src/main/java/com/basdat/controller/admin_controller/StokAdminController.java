package com.basdat.controller.admin_controller;

import com.basdat.App;
import com.basdat.db_models.Mobil;
import com.basdat.db_models.StokMobil;
import com.basdat.db_models.StokSK;
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
import static com.basdat.repository.DBConnect.getConnection;

public class StokAdminController implements Initializable {

    @FXML
    private TextField searchTF;
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
    private TableColumn<StokSK, String> IdSKClm;
    @FXML
    private TableColumn<StokSK, String> namaSKClm;
    @FXML
    private TableColumn<StokSK, String> stokSKClm;


    private ObservableList<StokMobil> dataMobil = FXCollections.observableArrayList();
    private ObservableList<StokMobil> selectMobil;
    private ObservableList<StokSK> dataSK = FXCollections.observableArrayList();
    private ObservableList<StokSK> selectSK;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTblStokMobil();
        updateTblStokSK();

        searchOnEnter();
    }

    @FXML
    private void addBtnAction() throws IOException {
        int idProduk = Integer.parseInt(IdPrdTF.getText().trim());
        int idCabang = Integer.parseInt(IdCbgTF.getText().trim());
        int stok = Integer.parseInt(stokTF.getText().trim());

        Connection con = DBConnect.getConnection();
        String query = "INSERT INTO Stok values (?,?,?)";

        if (selectSK == null) {

            try(PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, idCabang);
                ps.setInt(2, idProduk);
                ps.setInt(3, stok);

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
                ps.setInt(1, idCabang);
                ps.setInt(2, idProduk);
                ps.setInt(3, stok);

                ps.executeUpdate();

                Notification.Information("Information", "ADD SUCCESS");
            }
            catch (SQLException e) {
                e.printStackTrace();
                Notification.Error("ERROR", "ADD FAILED");
            }
        }
        App.setRoot("fxml/admin_menu/stokAdmin");
    }

    @FXML
    private void editBtnAction() throws IOException {
        int idProduk = Integer.parseInt(IdPrdTF.getText().trim());
        int idCabang = Integer.parseInt(IdCbgTF.getText().trim());
        int stok = Integer.parseInt(stokTF.getText().trim());

        Connection con = DBConnect.getConnection();
        String query = "UPDATE Stok SET stok = ? WHERE ID_Cabang = ? AND ID_Produk = ?";

        if (selectSK == null) {
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, stok);
                ps.setInt(2, idCabang);
                ps.setInt(3, idProduk);

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
                ps.setInt(1, stok);
                ps.setInt(2, idCabang);
                ps.setInt(3, idProduk);

                ps.executeUpdate();

                Notification.Information("Information", "EDIT SUCCESS");
            }
            catch (SQLException e) {
                e.printStackTrace();
                Notification.Error("ERROR", "EDIT FAILED");
            }
        }
        App.setRoot("fxml/admin_menu/stokAdmin");
    }

    @FXML
    private void deleteBtnAction() throws IOException {
        int idProduk = Integer.parseInt(IdPrdTF.getText().trim());
        int idCabang = Integer.parseInt(IdCbgTF.getText().trim());

        Connection con = DBConnect.getConnection();
        String query = "DELETE FROM Stok WHERE ID_Cabang = ? AND ID_Produk = ?";

        if (selectSK == null) {
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, idCabang);
                ps.setInt(2, idProduk);

                ps.executeUpdate();

                Notification.Information("Information", "DELETE SUCCESS");
            }
            catch (SQLException e) {
                e.printStackTrace();
                Notification.Error("ERROR", "DELETE FAILED");
            }
        }
        else if (selectMobil == null) {
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, idCabang);
                ps.setInt(2, idProduk);

                ps.executeUpdate();

                Notification.Information("Information", "DELETE SUCCESS");
            }
            catch (SQLException e) {
                e.printStackTrace();
                Notification.Error("ERROR", "DELETE FAILED");
            }
        }
        App.setRoot("fxml/admin_menu/stokAdmin");
    }

    @FXML
    private void mobilTblPressed() {
        ObservableList<StokMobil> MobilSelect;
        MobilSelect = mobilTblView.getSelectionModel().getSelectedItems();
        this.selectMobil = MobilSelect;
        this.selectSK = null;

        IdPrdTF.setText(""+selectMobil.get(0).getID_Produk());
        IdCbgTF.setText(""+selectMobil.get(0).getID_Cabang());
        stokTF.setText(""+selectMobil.get(0).getStok());
    }

    @FXML
    private void mobilTblClicked() {
        ObservableList<StokMobil> MobilSelect;
        MobilSelect = mobilTblView.getSelectionModel().getSelectedItems();
        this.selectMobil = MobilSelect;
        this.selectSK = null;

        IdPrdTF.setText(""+selectMobil.get(0).getID_Produk());
        IdCbgTF.setText(""+selectMobil.get(0).getID_Cabang());
        stokTF.setText(""+selectMobil.get(0).getStok());
    }

    @FXML
    private void SKTblPressed() {
        ObservableList<StokSK> SKselect;
        SKselect = SKTblView.getSelectionModel().getSelectedItems();
        this.selectSK = SKselect;
        this.selectMobil = null;

        IdPrdTF.setText(selectSK.get(0).getNama());
        IdCbgTF.setText(""+selectSK.get(0).getID_Cabang());
        stokTF.setText(""+selectSK.get(0).getStok());
    }

    @FXML
    private void SKTblClicked() {
        ObservableList<StokSK> SKselect;
        SKselect = SKTblView.getSelectionModel().getSelectedItems();
        this.selectSK = SKselect;
        this.selectMobil = null;

        IdPrdTF.setText(selectSK.get(0).getNama());
        IdCbgTF.setText(""+selectSK.get(0).getID_Cabang());
        stokTF.setText(""+selectSK.get(0).getStok());
    }

    @FXML
    private void searchOnEnter() {

        // 1. Wrap the ObservableList in a FilteredList
        FilteredList<StokMobil> filteredData = new FilteredList<>(dataMobil, p -> true);
        FilteredList<StokSK> filteredDataSK = new FilteredList<>(dataSK, p -> true);

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
                else if(String.valueOf(mobil.getID_Cabang()).contains(lowerCaseFilter)) {
                    return true;
                }
                else if(String.valueOf(mobil.getID_Produk()).contains(lowerCaseFilter)) {
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
                else if(String.valueOf(SK.getID_Cabang()).contains(lowerCaseFilter)) {
                    return true;
                }
                else if(String.valueOf(SK.getID_Cabang()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;

            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<StokMobil> sortedData = new SortedList<>(filteredData);
        SortedList<StokSK> sortedDataSK = new SortedList<>(filteredDataSK);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(mobilTblView.comparatorProperty());
        sortedDataSK.comparatorProperty().bind(SKTblView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        mobilTblView.setItems(sortedData);
        SKTblView.setItems(sortedDataSK);
    }

    @FXML
    private void backBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/menuAdmin");
    }

    private void updateTblStokMobil() {
        pullDBStokMobil();
        addDataToTableMobil();
        mobilTblView.refresh();
    }

    private void updateTblStokSK() {
        pullDBStokSK();
        addDataToTableSK();
        SKTblView.refresh();
    }

    private void pullDBStokMobil() {
        Connection con = getConnection();
        ResultSet resultSet, resultSet1;
        String query = "SELECT ID_Cabang, ID_Produk, SUM(stok) as stok FROM Stok " +
                "GROUP BY ID_Cabang, ID_Produk " +
                "ORDER BY ID_Produk";
        String query1 = "DELETE FROM Stok";
        String query2 = "INSERT INTO Stok values (?,?,?)";
        String query3 = "SELECT DISTINCT ID_Cabang, p.ID_Produk, nama_Produk, stok FROM Stok s JOIN Produk p ON p.ID_Produk = s.ID_Produk " +
                "WHERE jenis_Produk = ? ORDER BY p.ID_Produk";

        try(PreparedStatement ps = con.prepareStatement(query);
        PreparedStatement ps1 = con.prepareStatement(query1);
        PreparedStatement ps2 = con.prepareStatement(query2);
        PreparedStatement ps3 = con.prepareStatement(query3)) {

            resultSet = ps.executeQuery();

            ps1.executeUpdate();

            while (resultSet.next()) {
                ps2.setString(1, resultSet.getString(1));
                ps2.setString(2, resultSet.getString(2));
                ps2.setString(3, resultSet.getString(3));

                ps2.executeUpdate();
            }

            ps3.setString(1, "Mobil");

            // Create and execute a SELECT SQL statement.
            resultSet1 = ps3.executeQuery();

            // Add result to list
            while (resultSet1.next()) {
                dataMobil.add(new StokMobil(resultSet1.getInt(1),
                        resultSet1.getInt(2),
                        resultSet1.getString(3),
                        resultSet1.getInt(4)
                ));

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void pullDBStokSK() {
        Connection con = getConnection();
        ResultSet resultSet, resultSet1;
        String query = "SELECT ID_Cabang, ID_Produk, SUM(stok) as stok FROM Stok " +
                "GROUP BY ID_Cabang, ID_Produk " +
                "ORDER BY ID_Produk";
        String query1 = "DELETE FROM Stok";
        String query2 = "INSERT INTO Stok values (?,?,?)";
        String query3 = "SELECT DISTINCT ID_Cabang, p.ID_Produk, nama_Produk, stok FROM Stok s JOIN Produk p ON p.ID_Produk = s.ID_Produk " +
                "WHERE jenis_Produk = ? ORDER BY p.ID_Produk";

        try(PreparedStatement ps = con.prepareStatement(query);
            PreparedStatement ps1 = con.prepareStatement(query1);
            PreparedStatement ps2 = con.prepareStatement(query2);
            PreparedStatement ps3 = con.prepareStatement(query3)) {

            resultSet = ps.executeQuery();

            ps1.executeUpdate();

            while (resultSet.next()) {
                ps2.setString(1, resultSet.getString(1));
                ps2.setString(2, resultSet.getString(2));
                ps2.setString(3, resultSet.getString(3));

                ps2.executeUpdate();
            }

            ps3.setString(1, "Suku Cadang");

            // Create and execute a SELECT SQL statement.
            resultSet1 = ps3.executeQuery();

            // Add result to list
            while (resultSet1.next()) {
                dataMobil.add(new StokMobil(resultSet1.getInt(1),
                        resultSet1.getInt(2),
                        resultSet1.getString(3),
                        resultSet1.getInt(4)
                ));

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDataToTableMobil() {
        IdCbgMblClm.setCellValueFactory(new PropertyValueFactory<>("ID_Cabang"));
        IdMblClm.setCellValueFactory(new PropertyValueFactory<>("ID_Produk"));
        namaMblClm.setCellValueFactory(new PropertyValueFactory<>("nama"));
        stokMblClm.setCellValueFactory(new PropertyValueFactory<>("stok"));

        mobilTblView.setItems(dataMobil);
    }

    private void addDataToTableSK() {
        IdCbgSKClm.setCellValueFactory(new PropertyValueFactory<>("ID_Cabang"));
        IdSKClm.setCellValueFactory(new PropertyValueFactory<>("ID_Produk"));
        namaSKClm.setCellValueFactory(new PropertyValueFactory<>("nama"));
        stokSKClm.setCellValueFactory(new PropertyValueFactory<>("stok"));

        SKTblView.setItems(dataSK);
    }


}
