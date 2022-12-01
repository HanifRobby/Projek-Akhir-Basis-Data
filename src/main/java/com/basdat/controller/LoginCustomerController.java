package com.basdat.controller;

import com.basdat.App;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;

import static com.basdat.repository.DBConnect.connectionUrl;

public class LoginCustomerController {

    @FXML
    private TextField usernameTF;
    @FXML
    private TextField passTF;
    @FXML
    private Button loginBtn;
    @FXML
    private Button signUpBtn;
    @FXML
    private Label DescriptionLabel;

    private static String user;
    private static String pass;

    @FXML
    private void loginBtnAction() throws IOException{
        user = usernameTF.getText().trim();
        pass = passTF.getText().trim();

        if(!user.isEmpty() && !user.isBlank() && !pass.isEmpty() && !pass.isBlank()) {
            if(verifyLogin()) {
                App.setRoot("menuAdmin");
            }
        }
        else{
            usernameTF.clear();
            passTF.clear();
            DescriptionLabel.setText("Terdapat Kesalahan Dalam Mengisi Username/Password!");
        }
    }

    @FXML
    private void signUpBtnAction() throws IOException{
        App.setRoot("signUp");
    }

    @FXML
    private void backBtnAction() throws IOException {
        App.setRoot("welcome");
    }

    @FXML
    private void passwordTFonAction() throws IOException{
        loginBtnAction();
    }

    private Boolean verifyLogin() {
        ResultSet resultSet;

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement("SELECT Username, Password FROM Pengguna WHERE username = ? AND password = ?")) {
            ps.setString(1, user);
            ps.setString(2, pass);

            resultSet = ps.executeQuery();

            if(resultSet.next()) {
                JOptionPane.showMessageDialog(null, "LOGIN SUCCESS");
                return true;

            }
            else {
                JOptionPane.showMessageDialog(null, "LOGIN FAILED");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getUser() {
        return user;
    }

    public static String getPass() {
        return pass;
    }

}