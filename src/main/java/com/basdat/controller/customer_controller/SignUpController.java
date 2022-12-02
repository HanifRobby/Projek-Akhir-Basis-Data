package com.basdat.controller.customer_controller;

import com.basdat.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.basdat.repository.DBConnect.connectionUrl;

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


    private String email, user, password;



    @FXML
    private void signingUp() throws IOException {
        try {
            email = fieldEmailSU.getText().trim();
            user = fieldUsernameSU.getText().trim();
            password = fieldPasswordSU.getText().trim();
            if (email.isBlank() || user.isBlank() || password.isBlank()){
                descriptionLabelSU.setText("Please complete the fields above");
            }
            else {
//                String cabang = NcTF.getText().trim();
//                String table = "Pegawai";
//                String table1 = "Pengguna";
//
//                String query = "INSERT INTO " + table + " values (?,?,?,?,?,?,?,?)";
//                String query1 = "INSERT INTO " + table1 + " values (?,?,?,?)";
//
//                try(Connection connection = DriverManager.getConnection(connectionUrl);
//                    Connection connection1 = DriverManager.getConnection(connectionUrl);
//                    PreparedStatement ps = connection.prepareStatement(query);
//                    PreparedStatement ps1 = connection1.prepareStatement(query1)) {
//                    ps.setInt(1, Integer.parseInt(idPgn));
//                    ps.setInt(2, Integer.parseInt(idPgw));
//                    ps.setString(3, nama);
//
//
//                    ps1.executeUpdate();
//
//                    ps.executeUpdate();
//
//                    JOptionPane.showMessageDialog(null, "ADD SUCCESS");
//                }
//                catch (SQLException e) {
//                    e.printStackTrace();
//                    JOptionPane.showMessageDialog(null, "ADD FAILED");
//                }
//
//                descriptionLabelSU.setText("SIGN UP BERHASIL");
//                App.setRoot("fxml/welcome");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
