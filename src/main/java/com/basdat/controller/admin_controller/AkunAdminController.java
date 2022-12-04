package com.basdat.controller.admin_controller;

import com.basdat.App;
import com.basdat.repository.DBConnect;
import com.basdat.util.Notification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AkunAdminController implements Initializable {


    @FXML
    private ImageView profileImage;
    @FXML
    private Text emailTxt;
    @FXML
    private Text namaTxt;
    @FXML
    private Text jalanTxt;
    @FXML
    private Text kecTxt;
    @FXML
    private Text kotaTxt;
    @FXML
    private Text noCabangTxt;
    @FXML
    private Text usernameTxt;

    @FXML
    private PasswordField newPassTF1;
    @FXML
    private PasswordField newPassTF;
    @FXML
    private Text wrongPassTxt;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateInfo();


    }

    private void updateInfo() {
        String email, nama, jenisKelamin, jalan, kecamatan, kota, noCabang, username;

        email = LoginAdminController.getEmail();
        nama = LoginAdminController.getNama();
        jenisKelamin = LoginAdminController.getJenisKelamin();
        jalan = LoginAdminController.getJalan();
        kecamatan = LoginAdminController.getKecamatan();
        kota = LoginAdminController.getKota();
        noCabang = LoginAdminController.getNoCabang();
        username = LoginAdminController.getUser();

        emailTxt.setText(email);
        namaTxt.setText(nama);
        jalanTxt.setText(jalan);
        kecTxt.setText(kecamatan);
        kotaTxt.setText(kota);
        noCabangTxt.setText(noCabang);
        usernameTxt.setText(username);

        if(jenisKelamin.equals("Perempuan")) {
            profileImage.setImage(new Image("file:src/main/resources/images/female.png"));
        }
    }

    @FXML
    private void newPassBtnAction() {
        String newPass = newPassTF.getText().trim();
        String newPass1 = newPassTF1.getText().trim();

        if(!(newPass.isBlank() && newPass1.isBlank()) && (newPass.equals(newPass1)) && !(newPass.equals(LoginAdminController.getPass()))) {
            try {
                setNewPass(newPass);
                Notification.Information("Information", "SET NEW PASSWORD SUCCESS");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (!newPass.equals(newPass1) && !(newPass.isBlank() && newPass1.isBlank())) {
            wrongPassTxt.setText("The password confirmation does not match.");
        }
        else if (newPass.equals(LoginAdminController.getPass())) {
            wrongPassTxt.setText("Your password cannot be the same as the previous password");
        }
        else {
            wrongPassTxt.setText("Please complete the fields above");
        }

    }

    @FXML
    private void backBtnAct() throws IOException {
        App.setRoot("fxml/admin_menu/menuAdmin");
    }

    private void setNewPass (String newPass) {
        String ID_Pengguna = LoginAdminController.getID_Pengguna();

        Connection con = DBConnect.getConnection();
        String query = "UPDATE Pengguna SET Password = ? WHERE ID_Pengguna = ?";

        try(PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, newPass);
            ps.setString(2, ID_Pengguna);

            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            Notification.Error("ERROR", "EDIT FAILED");
        }
    }


}
