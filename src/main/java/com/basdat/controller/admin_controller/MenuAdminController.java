package com.basdat.controller.admin_controller;

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
    private void produkBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/produkAdmin");
    }

    @FXML
    private void stokBtnAction() throws IOException {

    }

    @FXML
    private void pesananBtnAction() throws IOException {

    }

    @FXML
    private void cabangBtnAction() throws IOException {

    }

    @FXML
    private void pegawaiBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/pegawai");
    }

    @FXML
    private void pembeliBtnAction() throws IOException {

    }

    @FXML
    private void kontakBtnAction() throws IOException {

    }

    @FXML
    private void logoutBtnAction() throws IOException {
        App.setRoot("fxml/welcome");
    }


}
