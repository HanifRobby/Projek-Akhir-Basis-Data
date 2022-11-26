package com.basdat.controller;

import java.io.IOException;

import com.basdat.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class signupController {
    @FXML
    private TextField fieldEmailSU;
    @FXML
    private TextField fieldUsernameSU;
    @FXML
    private PasswordField fieldPasswordSU;
    @FXML
    private Label descriptionLabelSU;

    private String email;
    private String username;
    private String password;

    @FXML
    private void signingUp(ActionEvent event) throws IOException{

            password = fieldPasswordSU.getText().trim();
            if(password.equals("")){
                descriptionLabelSU.setText("pass nya kosong mas");
            } else {
                descriptionLabelSU.setText(password);
            }


    }

}
