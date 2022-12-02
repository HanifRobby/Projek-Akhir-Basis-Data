package com.basdat.controller.customer_controller;

import com.basdat.App;
import com.basdat.db_models.Mobil;
import com.basdat.db_models.StokMobil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class PesanController {

    @FXML
    private Button addBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button bayarBtn;
    @FXML
    private Button backBtn;

    @FXML
    private TextField searchTF;

    @FXML
    private TableView<Mobil> mobilTblView;
    @FXML
    private TableColumn<Mobil, String> mobilNamaClm;
    @FXML
    private TableColumn<Mobil, String> mobilMerkClm;
    @FXML
    private TableColumn<Mobil, String> mobilYearClm;
    @FXML
    private TableColumn<Mobil, String> mobilHargaClm;

    @FXML
    private void backBtnAction() throws IOException{
        App.setRoot("fxml/customer_menu/menuCustomer");
    }

    @FXML
    private void deleteBtnAction() throws IOException{

    }

    @FXML
    private void addBtnAction() throws IOException{

    }

    @FXML
    private void removeBtnAction() throws IOException{

    }

    @FXML
    private void bayarBtnAction() throws IOException{

    }

    @FXML
    private void searchOnEnter() throws IOException{

    }

    @FXML
    private void MobilTblPressed() throws IOException{

    }

    @FXML
    private void MobilTblClicked() throws IOException{

    }



}
