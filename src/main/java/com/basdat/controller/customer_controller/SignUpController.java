package com.basdat.controller.customer_controller;

import com.basdat.App;
import com.basdat.repository.DBConnect;
import com.basdat.util.Notification;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;

import static com.basdat.repository.DBConnect.connectionUrl;

public class SignUpController {

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
    @FXML
    private TextField fieldNamaSU;
    @FXML
    private TextField fieldNIKSU;
    @FXML
    private TextField fieldJalanSU;
    @FXML
    private TextField fieldKecSU;
    @FXML
    private TextField fieldKotaSU;
    @FXML
    private Label descriptionLabelSU;

    @FXML
    private ImageView profileImage;

    @FXML
    private RadioButton MaleRB;
    @FXML
    private RadioButton FemaleRB;


    private String email, user, password;
    private String nama, NIK, jenisKelamin, jalan, kecamatan, kota;

    @FXML
    private void signingUp() throws IOException {
        email = fieldEmailSU.getText().trim();
        user = fieldUsernameSU.getText().trim();
        password = fieldPasswordSU.getText().trim();
        nama = fieldNamaSU.getText().trim();
        NIK = fieldNIKSU.getText().trim();
        jenisKelamin = "Laki-laki";
        jalan = fieldJalanSU.getText().trim();
        kecamatan = fieldKecSU.getText().trim();
        kota = fieldKotaSU.getText().trim();

        ResultSet resultSet;
        try {
            if (!checkFieldEmpty()){
                descriptionLabelSU.setText("Please complete the fields above");
            }
            else {
                Connection con = DBConnect.getConnection();

                String query = "INSERT INTO Pengguna values (?,?,?)";
                String query1 = "SELECT ID_Pengguna FROM Pengguna WHERE Username = ?";
                String query2 = "INSERT INTO Pembeli values (?,?,?,?,?,?,?)";

                try(PreparedStatement ps = con.prepareStatement(query);
                    PreparedStatement ps1 = con.prepareStatement(query1);
                    PreparedStatement ps2 = con.prepareStatement(query2)) {

                    con.setAutoCommit(false);

                    ps.setString(1, email);
                    ps.setString(2, user);
                    ps.setString(3, password);

                    ps.executeUpdate();

                    ps1.setString(1,user);

                    resultSet = ps1.executeQuery();

                    if (resultSet.next()) {
                        String ID_Pengguna = resultSet.getString(1);

                        ps2.setString(1, ID_Pengguna);
                        ps2.setString(2, nama);
                        ps2.setString(3, NIK);
                        ps2.setString(4, jenisKelamin);
                        ps2.setString(5, jalan);
                        ps2.setString(6, kecamatan);
                        ps2.setString(7, kota);

                        ps2.executeUpdate();
                    }

                    con.commit();
                    descriptionLabelSU.setText("SIGN UP BERHASIL");
                    Notification.Information("Information", "SIGN UP SUCCESS");
                    App.setRoot("fxml/customer_menu/loginCustomer");
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    if (con != null) {
                        try {
                        con.rollback();
                        }
                        catch (Exception ex) {
                            System.out.println("Rollback Failed");
                        }
                        System.out.println("Rollback Succes");
                    }
                    Notification.Error("ERROR", "SIGNUP FAILED");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void MaleRBAction() {
        FemaleRB.setSelected(false);
        jenisKelamin = "Laki-laki";
        profileImage.setImage(new Image("file:src/main/resources/images/male.png"));
    }

    @FXML
    private void FemaleRBAction() {
        MaleRB.setSelected(false);
        jenisKelamin = "Perempuan";
        profileImage.setImage(new Image("file:src/main/resources/images/female.png"));
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

    private boolean checkFieldEmpty() {
        if (!email.isBlank() && !user.isBlank() && !password.isBlank() && !nama.isBlank() && !NIK.isBlank() && !jenisKelamin.isBlank() && !jalan.isBlank() && !kecamatan.isBlank() && !kota.isBlank()) {
            return true;
        }

        return  false;
    }
}
