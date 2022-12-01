package com.basdat.controller;

import com.basdat.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuAdminController {

    @FXML
    private Button stokBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button DescriptionLabel;



    @FXML
    private void stokBtnAction() throws IOException {
        App.setRoot("stokAdmin");
    }

    @FXML
    private void logoutBtnAction() throws IOException {
        App.setRoot("welcome");
    }

}
