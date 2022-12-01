package com.basdat.controller.customer_controller;

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
    private Button backSignUpBtn;
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
                App.setRoot("fxml/welcome");
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

    @FXML
    private void backSignUpBtnAction() throws  IOException {
        App.setRoot("fxml/customer_menu/loginCustomer");
    }

    @FXML
    private void backSignUpBtnPressed() throws  IOException {
        backSignUpBtn.setPrefHeight(backSignUpBtn.getPrefHeight()*1.1);
    }

    @FXML
    private void backSignUpBtnReleased() throws IOException {
        backSignUpBtn.setPrefHeight(backSignUpBtn.getPrefHeight()/1.1);
    }
}
