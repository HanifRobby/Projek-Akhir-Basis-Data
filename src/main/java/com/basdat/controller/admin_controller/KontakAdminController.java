package com.basdat.controller.admin_controller;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.basdat.repository.DBConnect.getConnection;

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
        String id = IdTF.getText().trim();
        String nomor = nomorTF.getText().trim();

        Connection con = getConnection();
        String query = "INSERT INTO no_Telp_Cabang values (?,?)";
        String query1 = "INSERT INTO no_Telp_Pegawai values (?,?)";
        String query2 = "INSERT INTO no_Telp_Pembeli values (?,?)";

        if (!(selectCabang == null)) {
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, id);
                ps.setString(2, nomor);

                ps.executeUpdate();

                Notification.Information("Information", "ADD SUCCESS");
            }
            catch (SQLException e) {
                e.printStackTrace();
                Notification.Error("ERROR", "ADD FAILED");
            }
        }
        else if (!(selectPegawai == null)) {
            try(PreparedStatement ps = con.prepareStatement(query1)) {
                ps.setString(1, id);
                ps.setString(2, nomor);

                ps.executeUpdate();

                Notification.Information("Information", "ADD SUCCESS");
            }
            catch (SQLException e) {
                e.printStackTrace();
                Notification.Error("ERROR", "ADD FAILED");
            }
        }
        else if (!(selectPembeli == null)) {
            try(PreparedStatement ps = con.prepareStatement(query2)) {
                ps.setString(1, id);
                ps.setString(2, nomor);

                ps.executeUpdate();

                Notification.Information("Information", "ADD SUCCESS");
            }
            catch (SQLException e) {
                e.printStackTrace();
                Notification.Error("ERROR", "ADD FAILED");
            }
        }
        App.setRoot("fxml/admin_menu/kontakAdmin");
    }

    @FXML
    private void deleteBtnAction() throws IOException {
        String id = IdTF.getText().trim();
        String nomor = nomorTF.getText().trim();

        Connection con = getConnection();
        String query = "DELETE FROM no_Telp_Cabang WHERE no_Cabang = ? AND no_Telp = ?";
        String query1 = "DELETE FROM no_Telp_Pegawai WHERE ID_Pegawai = ? AND no_Telp = ?";
        String query2 = "DELETE FROM no_Telp_Pembeli WHERE ID_Pembeli = ? AND no_Telp = ?";

        if (!(selectCabang == null)) {
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, id);
                ps.setString(2, nomor);

                ps.executeUpdate();

                Notification.Information("Information", "DELETE SUCCESS");
            }
            catch (SQLException e) {
                e.printStackTrace();
                Notification.Error("ERROR", "DELETE FAILED");
            }
        }
        else if (!(selectPegawai == null)) {
            try(PreparedStatement ps = con.prepareStatement(query1)) {
                ps.setString(1, id);
                ps.setString(2, nomor);

                ps.executeUpdate();

                Notification.Information("Information", "DELETE SUCCESS");
            }
            catch (SQLException e) {
                e.printStackTrace();
                Notification.Error("ERROR", "DELETE FAILED");
            }
        }
        else if (!(selectPembeli == null)) {
            try(PreparedStatement ps = con.prepareStatement(query2)) {
                ps.setString(1, id);
                ps.setString(2, nomor);

                ps.executeUpdate();

                Notification.Information("Information", "DELETE SUCCESS");
            }
            catch (SQLException e) {
                e.printStackTrace();
                Notification.Error("ERROR", "DELETE FAILED");
            }
        }
        App.setRoot("fxml/admin_menu/kontakAdmin");
    }

    @FXML
    private void backBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/menuAdmin");
    }

    @FXML
    private void searchOnEnter() {

        // 1. Wrap the ObservableList in a FilteredList
        FilteredList<NomorTelepon> filteredData = new FilteredList<>(dataCabang, p -> true);
        FilteredList<NomorTelepon> filteredDataPegawai = new FilteredList<>(dataPegawai, p -> true);
        FilteredList<NomorTelepon> filteredDataPembeli = new FilteredList<>(dataPembeli, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(nomorTelepon -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(nomorTelepon.getNama().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if(nomorTelepon.getID().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else return nomorTelepon.getNomorTelepon().toLowerCase().contains(lowerCaseFilter);

            });

            filteredDataPegawai.setPredicate(nomorTelepon -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(nomorTelepon.getNama().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if(nomorTelepon.getID().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else return nomorTelepon.getNomorTelepon().toLowerCase().contains(lowerCaseFilter);

            });

            filteredDataPembeli.setPredicate(nomorTelepon -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(nomorTelepon.getNama().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if(nomorTelepon.getID().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else return nomorTelepon.getNomorTelepon().toLowerCase().contains(lowerCaseFilter);

            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<NomorTelepon> sortedData = new SortedList<>(filteredData);
        SortedList<NomorTelepon> sortedDataPegawai = new SortedList<>(filteredDataPegawai);
        SortedList<NomorTelepon> sortedDataPembeli = new SortedList<>(filteredDataPembeli);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(CabangTableView.comparatorProperty());
        sortedDataPegawai.comparatorProperty().bind(PegawaiTableView.comparatorProperty());
        sortedDataPembeli.comparatorProperty().bind(PembeliTableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        CabangTableView.setItems(sortedData);
        PegawaiTableView.setItems(sortedDataPegawai);
        PembeliTableView.setItems(sortedDataPembeli);
    }

    @FXML
    private void CbgTblPressed() {
        ObservableList<NomorTelepon> Cabangselect;
        Cabangselect = CabangTableView.getSelectionModel().getSelectedItems();
        this.selectCabang = Cabangselect;
        this.selectPegawai = null;
        this.selectPembeli = null;

        IdTF.setText(Cabangselect.get(0).getID());
        namaTF.setText(Cabangselect.get(0).getNama());
        nomorTF.setText(Cabangselect.get(0).getNomorTelepon());
    }

    @FXML
    private void CbgTblClicked() {
        ObservableList<NomorTelepon> Cabangselect;
        Cabangselect = CabangTableView.getSelectionModel().getSelectedItems();
        this.selectCabang = Cabangselect;
        this.selectPegawai = null;
        this.selectPembeli = null;

        IdTF.setText(Cabangselect.get(0).getID());
        namaTF.setText(Cabangselect.get(0).getNama());
        nomorTF.setText(Cabangselect.get(0).getNomorTelepon());
    }

    @FXML
    private void PgwTblPressed() {
        ObservableList<NomorTelepon> Pegawaiselect;
        Pegawaiselect = PegawaiTableView.getSelectionModel().getSelectedItems();
        this.selectPegawai = Pegawaiselect;
        this.selectCabang = null;
        this.selectPembeli = null;

        IdTF.setText(Pegawaiselect.get(0).getID());
        namaTF.setText(Pegawaiselect.get(0).getNama());
        nomorTF.setText(Pegawaiselect.get(0).getNomorTelepon());
    }

    @FXML
    private void PgwTblClicked() {
        ObservableList<NomorTelepon> Pegawaiselect;
        Pegawaiselect = PegawaiTableView.getSelectionModel().getSelectedItems();
        this.selectPegawai = Pegawaiselect;
        this.selectCabang = null;
        this.selectPembeli = null;

        IdTF.setText(Pegawaiselect.get(0).getID());
        namaTF.setText(Pegawaiselect.get(0).getNama());
        nomorTF.setText(Pegawaiselect.get(0).getNomorTelepon());
    }

    @FXML
    private void PmbTblPressed() {
        ObservableList<NomorTelepon> Pembeliselect;
        Pembeliselect = PembeliTableView.getSelectionModel().getSelectedItems();
        this.selectPembeli = Pembeliselect;
        this.selectCabang = null;
        this.selectPegawai = null;

        IdTF.setText(Pembeliselect.get(0).getID());
        namaTF.setText(Pembeliselect.get(0).getNama());
        nomorTF.setText(Pembeliselect.get(0).getNomorTelepon());
    }

    @FXML
    private void PmbTblClicked() {
        ObservableList<NomorTelepon> Pembeliselect;
        Pembeliselect = PembeliTableView.getSelectionModel().getSelectedItems();
        this.selectPembeli = Pembeliselect;
        this.selectCabang = null;
        this.selectPegawai = null;

        IdTF.setText(Pembeliselect.get(0).getID());
        namaTF.setText(Pembeliselect.get(0).getNama());
        nomorTF.setText(Pembeliselect.get(0).getNomorTelepon());
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
        Connection con = DBConnect.getConnection();
        ResultSet resultSet;
        String query = "SELECT n.no_Cabang, email_Cabang, no_Telp FROM no_Telp_Cabang n JOIN Cabang c ON n.no_Cabang = c.no_Cabang";

        try(PreparedStatement ps = con.prepareStatement(query)) {

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
        Connection con = DBConnect.getConnection();
        ResultSet resultSet;
        String query = "SELECT n.ID_Pegawai, p.Nama, no_Telp FROM no_Telp_Pegawai n JOIN Pegawai p ON n.ID_Pegawai = p.ID_Pegawai";

        try(PreparedStatement ps = con.prepareStatement(query)) {

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
        Connection con = DBConnect.getConnection();
        ResultSet resultSet;
        String query = "SELECT n.ID_Pembeli, p.Nama, no_Telp FROM no_Telp_Pembeli n JOIN Pembeli p ON n.ID_Pembeli = p.ID_Pembeli";

        try(PreparedStatement ps = con.prepareStatement(query)) {

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
