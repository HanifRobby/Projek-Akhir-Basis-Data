package com.basdat.controller.admin_controller;

import com.basdat.App;
import com.basdat.db_models.Mobil;
import com.basdat.repository.DBConnect;
import com.basdat.util.Notification;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;


public class LoginAdminController {

    @FXML
    private Text loginError;
    @FXML
    private TextField usernameTF;
    @FXML
    private TextField passTF;
    @FXML
    private Button loginBtn;

    private static String ID_Pengguna, email, user, pass;
    private static String ID_Pegawai, nama, jenisKelamin, jalan, kecamatan, kota, noCabang;

    @FXML
    private void loginBtnAction() throws IOException{
        user = usernameTF.getText().trim();
        pass = passTF.getText().trim();

        if(checkEmpty(user, pass)) {
            if(verifyLogin()) {
                App.setRoot("fxml/admin_menu/menuAdmin");
            }
        }
        else if (!checkEmpty(user)){
            usernameTF.clear();
            loginError.setText("Username is blank");
        }
        else if (!checkEmpty(pass)) {
            passTF.clear();
            loginError.setText("Password is blank");
        }
        else if (!checkEmpty(user, pass)){
            loginError.setText("Username and password is blank");
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
        String query = "SELECT ID_Pengguna, Username, Password, Email FROM Pengguna WHERE username = ? AND password = ?";
        String query1 = "SELECT ID_Pegawai, Nama, jenis_Kelamin, jalan_Pegawai, kecamatan_Pegawai, kota_Pegawai, no_Cabang FROM Pegawai WHERE ID_Pengguna = ?";


        try(PreparedStatement ps = DBConnect.getConnection().prepareStatement(query);
            PreparedStatement ps1 = DBConnect.getConnection().prepareStatement(query1)) {

            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()) {
                ID_Pengguna = resultSet.getString(1);


                if ( resultSet.getString(1).charAt(0) == '3' ){
                    if(resultSet.getString(2).equals(user) && resultSet.getString(3).equals(pass)) {
                        email = resultSet.getString(4);

                        ps1.setString(1, ID_Pengguna);

                        ResultSet resultSet1 = ps1.executeQuery();

                        if (resultSet1.next()) {
                            ID_Pegawai = resultSet1.getString(1);
                            nama = resultSet1.getString(2);
                            jenisKelamin = resultSet1.getString(3);
                            jalan = resultSet1.getString(4);
                            kecamatan = resultSet1.getString(5);
                            kota = resultSet1.getString(6);
                            noCabang = resultSet1.getString(7);
                        }

                        Notification.Information("Information","LOGIN SUCCESS");
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

    public static String getID_Pengguna() {
        return ID_Pengguna;
    }

    public static String getEmail() {
        return email;
    }

    public static String getUser() {
        return user;
    }

    public static String getPass() {
        return pass;
    }

    public static String getID_Pegawai() {
        return ID_Pegawai;
    }

    public static String getNama() {
        return nama;
    }

    public static String getJenisKelamin() {
        return jenisKelamin;
    }

    public static String getJalan() {
        return jalan;
    }

    public static String getKecamatan() {
        return kecamatan;
    }

    public static String getKota() {
        return kota;
    }

    public static String getNoCabang() {
        return noCabang;
    }

    public static void ClearLoginInformation() {
        ID_Pengguna = null;
        email = null;
        user = null;
        pass = null;
        ID_Pegawai = null;
        nama = null;
        jenisKelamin = null;
        jalan = null;
        kecamatan = null;
        kota = null;
        noCabang = null;
    }

}
