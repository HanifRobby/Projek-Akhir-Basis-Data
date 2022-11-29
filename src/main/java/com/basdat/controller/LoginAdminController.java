package com.basdat.controller;

import com.basdat.App;
import com.basdat.db_models.Mobil;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;

import static com.basdat.repository.DBConnect.connectionUrl;

public class LoginAdminController {

    @FXML
    private TextField usernameTF;
    @FXML
    private TextField passTF;
    @FXML
    private Button loginBtn;
    @FXML
    private Button signUpBtn;

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
        }
    }

    @FXML
    private void signUpBtnAction() {
        try {
            App.setRoot("signUp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void passTFonEnter() throws IOException{
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
