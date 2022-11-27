package com.basdat.controller;

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
    private Button buttonSU;
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
            }
        } catch (Exception e) {

        }
    }

    @FXML
    private void signupBtnPressed() throws IOException {
        buttonSU.setPrefHeight(buttonSU.getPrefHeight()*1.5);
    }

    @FXML
    private void signupBtnReleased() throws IOException {
        buttonSU.setPrefHeight(buttonSU.getPrefHeight()/1.5);
    }
}
