package com.basdat.controller;

import com.basdat.App;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class LoginAdminController {

    @FXML
    private Button loginBtn;
    @FXML
    private Button signUpBtn;


    @FXML
    private void loginBtnAction() throws IOException {
        App.setRoot("menuAdmin");
    }
    @FXML
    private void signUpBtnAction() throws IOException {
        App.setRoot("signUp");
    }

}
