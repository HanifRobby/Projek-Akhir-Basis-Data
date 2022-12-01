package com.basdat.controller;

import com.basdat.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuAdminController {

    @FXML
    private Button stokBtn;
    @FXML
    private Button logoutBtn;


    @FXML
    private void stokBtnAction() throws IOException {
        App.setRoot("stokAdmin");
    }

    @FXML
    private void logoutBtnAction() throws IOException {
        App.setRoot("welcome");
    }

    @FXML
    private void pegawaiBtnAction() throws IOException {
        App.setRoot("pegawai");
    }

    @FXML
    private void produkBtnAction() throws IOException {

    }

    @FXML
    private void pesananActionBtn() throws IOException {

    }

    @FXML
    private void stokActionBtn() throws IOException {

    }

    @FXML
    private void pembelianActionBtn() throws IOException {

    }

    @FXML
    private void pembeliBtnAction() throws IOException {

    }
}
