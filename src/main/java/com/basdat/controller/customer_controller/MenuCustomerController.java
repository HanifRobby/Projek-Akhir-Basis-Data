package com.basdat.controller.customer_controller;

import com.basdat.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuCustomerController {

    @FXML
    private Button logoutBtn;
    @FXML
    private Button pesanBtn;
    @FXML
    private Button daftarPesanBtn;
    @FXML
    private Button pembayaranBtn;


    @FXML
    private void logoutBtnAction() throws IOException {
        App.setRoot("fxml/welcome");
    }

    @FXML
    private void pesanBtnAction() throws IOException {
        App.setRoot("fxml/customer_menu/pesan");
    }

    @FXML
    private void daftarPesanBtnAction() throws IOException {
        App.setRoot("fxml/customer_menu/daftarPesanan");
    }

    @FXML
    private void pembayaranBtnAction() throws IOException {

    }


}
