package com.basdat.controller.customer_controller;

import com.basdat.App;
import com.basdat.controller.admin_controller.LoginAdminController;
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

public class MenuCustomerController implements Initializable {

    @FXML
    private ImageView profileImage;
    @FXML
    private Button logoutBtn;
    @FXML
    private Label customerName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerName.setText(LoginCustomerController.getNama());

        if (LoginCustomerController.getJenisKelamin().equals("Perempuan")) {
            profileImage.setImage(new Image("file:src/main/resources/images/female.png"));
        }
        else {
            profileImage.setImage(new Image("file:src/main/resources/images/male.png"));
        }
    }

    @FXML
    private void logoutBtnAction() throws IOException {
        LoginAdminController.ClearLoginInformation();
        App.setRoot("fxml/welcome");
    }

    @FXML
    private void akunBtnAction() throws IOException {
        App.setRoot("fxml/customer_menu/akunCustomer");
    }

    @FXML
    private void beliBtnAction() throws IOException {
        App.setRoot("fxml/customer_menu/pesan");
    }

    @FXML
    private void pesananBtnAction() throws IOException {
        App.setRoot("fxml/customer_menu/daftarPesanan");
    }

    @FXML
    private void pembayaranBtnAction() throws IOException {

    }


}
