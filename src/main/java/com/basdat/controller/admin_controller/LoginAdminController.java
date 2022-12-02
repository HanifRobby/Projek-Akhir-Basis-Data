package com.basdat.controller.admin_controller;

import com.basdat.App;
import com.basdat.db_models.Mobil;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;

import static com.basdat.repository.DBConnect.connectionUrl;

public class LoginAdminController {

    @FXML
    private Text loginError;
    @FXML
    private TextField usernameTF;
    @FXML
    private TextField passTF;
    @FXML
    private Button loginBtn;

    private static String user;
    private static String pass;

    @FXML
    private void loginBtnAction() throws IOException{
        user = usernameTF.getText().trim();
        pass = passTF.getText().trim();

        if(checkEmpty(user, pass)) {
            if(verifyLogin()) {
                App.setRoot("fxml/admin_menu/menuAdmin");
            }
        }
        else if (!checkEmpty(user, pass)){
            usernameTF.clear();
            passTF.clear();
            loginError.setText("Username and password is blank");
        }
        else if (!checkEmpty(user)){
            usernameTF.clear();
            loginError.setText("Username is blank");
        }
        else if (!checkEmpty(pass)) {
            passTF.clear();
            loginError.setText("Password is blank");
        }
    }

    @FXML
    private void backBtnAction() throws IOException {
        App.setRoot("fxml/welcome");
    }

    @FXML
    private void passwordTFonAction() throws IOException{
        loginBtnAction();
    }

    private Boolean verifyLogin() {
        ResultSet resultSet;
        String query = "SELECT ID_Pengguna, Username, Password FROM Pengguna WHERE username = ? AND password = ?";

        try(Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, user);
            ps.setString(2, pass);

            resultSet = ps.executeQuery();

            if(resultSet.next()) {
                if ( resultSet.getString(1).charAt(0) == '3' ){
                    if(resultSet.getString(2).equals(user) && resultSet.getString(3).equals(pass)) {
                        JOptionPane.showMessageDialog(null, "LOGIN SUCCESS");
                        return true;
                    }
                }

            }
            loginError.setText("Username or password is invalid");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean checkEmpty(String user, String pass) {
        if(!user.isBlank() && !pass.isBlank()){
            return true;
        }

        return false;
    }

    public boolean checkEmpty(String text) {
        if(!text.isBlank()) {
            return true;
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
