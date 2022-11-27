package com.basdat.controller;

import java.io.IOException;

import com.basdat.App;
import com.basdat.repository.DBConnect;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WelcomeController  {

    @FXML
    private Button adminBtn;
    @FXML
    private Button customerBtn;

    @FXML
    private void adminBtnAction() throws IOException {

        try {
            App.setRoot("loginAdmin");
        } catch (Exception e) {

        }

    }

    @FXML
    private void customerBtnAction() throws IOException {

        try {
            App.setRoot("loginCustomer");
        } catch (Exception e) {

        }

    }

    @FXML
    private void adminBtnPressed() throws IOException {
        adminBtn.setPrefHeight(adminBtn.getPrefHeight()*1.05);
    }

    @FXML
    private void adminBtnReleased() throws IOException {
        adminBtn.setPrefHeight(adminBtn.getPrefHeight()/1.05);
    }

    @FXML
    private void customerBtnPressed() throws IOException {
        customerBtn.setPrefHeight(customerBtn.getPrefHeight()*1.05);
    }

    @FXML
    private void customerBtnReleased() throws IOException {
        customerBtn.setPrefHeight(customerBtn.getPrefHeight()/1.05);
    }


}
