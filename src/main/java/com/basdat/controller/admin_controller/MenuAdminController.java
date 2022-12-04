package com.basdat.controller.admin_controller;

import com.basdat.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuAdminController implements Initializable {


    @FXML
    private ImageView profileImage;
    @FXML
    private Button logoutBtn;
    @FXML
    private Label adminName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminName.setText(LoginAdminController.getNama());

        if (LoginAdminController.getJenisKelamin().equals("Perempuan")) {
            profileImage.setImage(new Image("file:src/main/resources/images/female.png"));
        }
        else {
            profileImage.setImage(new Image("file:src/main/resources/images/male.png"));
        }
    }


    @FXML
    private void produkBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/produkAdmin");
    }

    @FXML
    private void stokBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/stokAdmin");
    }

    @FXML
    private void pesananBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/pesananAdmin");
    }

    @FXML
    private void akunBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/akunAdmin");
    }

    @FXML
    private void pegawaiBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/pegawaiAdmin");
    }

    @FXML
    private void pembeliBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/pembeliAdmin");
    }

    @FXML
    private void cabangBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/cabangAdmin");
    }

    @FXML
    private void kontakBtnAction() throws IOException {
        App.setRoot("fxml/admin_menu/kontakAdmin");
    }

    @FXML
    private void logoutBtnAction() throws IOException {
        LoginAdminController.ClearLoginInformation();
        App.setRoot("fxml/welcome");
    }


}
