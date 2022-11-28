package com.basdat.controller;

import com.basdat.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignUpController {
    @FXML
    private Label descriptionLabelSU;
    @FXML
    private Button signUpBtn;
    @FXML
    private TextField fieldEmailSU;
    @FXML
    private TextField fieldUsernameSU;
    @FXML
    private PasswordField fieldPasswordSU;


    private String password;


    @FXML
    private void signingUp() throws IOException {
        try {
            password = fieldPasswordSU.getText().trim();
            if (password.equals("")){
                descriptionLabelSU.setText("Pass masih Kosong mas!!");
            } else {
                descriptionLabelSU.setText(password);
                App.setRoot("welcome");
            }
        } catch (Exception e) {

        }
    }

    @FXML
    private void signUpBtnPressed() throws IOException {
        signUpBtn.setPrefHeight(signUpBtn.getPrefHeight()*1.5);
    }

    @FXML
    private void signUpBtnReleased() throws IOException {
        signUpBtn.setPrefHeight(signUpBtn.getPrefHeight()/1.5);
    }
}
