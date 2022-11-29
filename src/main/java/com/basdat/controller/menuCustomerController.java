package com.basdat.controller;

import com.basdat.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuCustomerController {

    @FXML
    private Button logoutBtn;


    @FXML
    private void logoutBtnAction() throws IOException {
        App.setRoot("welcome");
    }


}
