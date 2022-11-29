package com.basdat.controller;

import com.basdat.App;
//import com.basdat.db_models.Mobil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

//import static com.basdat.repository.DBConnect.connectionUrl;

//public class StokAdminController implements Initializable {
public class StokAdminController {

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
    private TableColumn mobilHargaClm;


//    private ObservableList<Mobil> data = FXCollections.observableArrayList();


//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        pullDB();
//        addDataToTable();
//
//
//
//    }

    @FXML
    private void searchOnEnter(ActionEvent event) throws IOException{
        System.out.println(searchTF.getText().trim());

    }

    @FXML
    private void backBtnAction(ActionEvent event) throws IOException{
        App.setRoot("menuAdmin");
    }


//    private void pullDB() {
//        ResultSet resultSet = null;
//
//        try(Connection connection = DriverManager.getConnection(connectionUrl);
//            Statement statement = connection.createStatement();) {
//
//            // Create and execute a SELECT SQL statement.
//            String selectSql = "SELECT id, name, dept_name, dept_name, salary, salary from instructor ORDER BY name";
//            resultSet = statement.executeQuery(selectSql);
//
//            // Add result to list
//            while (resultSet.next()) {
//                data.add(new Mobil(resultSet.getString(1),
//                        resultSet.getString(2),
//                        resultSet.getString(3),
//                        resultSet.getString(4),
//                        resultSet.getString(5),
//                        "Rp" + resultSet.getString(6)
//                        ));
//
//            }
//
//
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void addDataToTable() {
//        mobilIdClm.setCellValueFactory(new PropertyValueFactory<Mobil, String>("ID"));
//        mobilNamaClm.setCellValueFactory(new PropertyValueFactory<Mobil, String>("nama"));
//        mobilMerkClm.setCellValueFactory(new PropertyValueFactory<Mobil, String>("merk"));
//        mobilYearClm.setCellValueFactory(new PropertyValueFactory<Mobil, String>("tahun"));
//        mobilColorClm.setCellValueFactory(new PropertyValueFactory<Mobil, String>("warna"));
//        mobilHargaClm.setCellValueFactory(new PropertyValueFactory<Mobil, String>("harga"));
//
//        mobilTblView.setItems(data);
//    }



}


