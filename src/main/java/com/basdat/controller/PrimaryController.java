package com.basdat.controller;

import java.io.IOException;
import com.basdat.repository.DBConnect;
import javafx.fxml.FXML;
import com.basdat.App;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
//        DBConnect.ConnectDB();
        App.setRoot("secondary");
    }
}
