package com.basdat.controller;

import com.basdat.App;
import com.basdat.db_models.Mobil;
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
    private TextField produkTF;
    @FXML
    private TextField stokTF;
    @FXML
    private TextField hargaTF;

    @FXML
    private TableView mobilTblView;
    @FXML
    private TableColumn mobilIdClm;
    @FXML
    private TableColumn mobilNamaClm;
    @FXML
    private TableColumn mobilMerkClm;
    @FXML
    private TableColumn mobilYearClm;
    @FXML
    private TableColumn mobilColorClm;
    @FXML
    private TableColumn mobilStokClm;
    @FXML
    private TableColumn mobilHargaClm;


    private ObservableList<Mobil> data = FXCollections.observableArrayList();
    private static TableRow<Mobil> row = new TableRow<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pullDB();
        addDataToTable();

        searchOnEnter();
        mobilTblClicked();
//        mobilTblPressed();



    }

    @FXML
    private void firstBtnAction() {
        mobilTblView.requestFocus();
        mobilTblView.getSelectionModel().select(0);

        row.getTableView().getSelectionModel().getSelectedItem();
        System.out.println(row.getItem().getNama());
    }

    @FXML
    private void mobilTblPressed() {

    }

    @FXML
    private void mobilTblClicked() {
        mobilTblView.setRowFactory(tv -> {
            TableRow<Mobil> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if ( !row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    this.row = row;

                    Mobil clickedRow = row.getItem();
                    System.out.println(clickedRow.getNama());

                    produkTF.setText(clickedRow.getID() + " " + clickedRow.getNama());
                    stokTF.setText(clickedRow.getHarga());
                    hargaTF.setText(clickedRow.getHarga());
                }
            });
            return row;
        });

    }


    @FXML
    private void searchOnEnter() {

        // 1. Wrap the ObservableList in a FilteredList
        FilteredList<Mobil> filteredData = new FilteredList<>(data, p -> true);

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
                return false;

            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Mobil> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(mobilTblView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        mobilTblView.setItems(sortedData);
    }

    @FXML
    private void backBtnAction(ActionEvent event) throws IOException{
        App.setRoot("menuAdmin");
    }


    private void pullDB() {
        ResultSet resultSet = null;

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            Statement statement = connection.createStatement();) {

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT id, name, dept_name, dept_name, salary, salary from instructor ORDER BY name";
            resultSet = statement.executeQuery(selectSql);

            // Add result to list
            while (resultSet.next()) {
                data.add(new Mobil(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        "Rp" + resultSet.getString(6)
                        ));

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDataToTable() {
        mobilIdClm.setCellValueFactory(new PropertyValueFactory<Mobil, String>("ID"));
        mobilNamaClm.setCellValueFactory(new PropertyValueFactory<Mobil, String>("nama"));
        mobilMerkClm.setCellValueFactory(new PropertyValueFactory<Mobil, String>("merk"));
        mobilYearClm.setCellValueFactory(new PropertyValueFactory<Mobil, String>("tahun"));
        mobilColorClm.setCellValueFactory(new PropertyValueFactory<Mobil, String>("warna"));
        mobilHargaClm.setCellValueFactory(new PropertyValueFactory<Mobil, String>("harga"));

        mobilTblView.setItems(data);
    }



}


