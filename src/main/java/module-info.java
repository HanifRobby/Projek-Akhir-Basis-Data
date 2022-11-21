module com.basdat {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.basdat to javafx.fxml;
    exports com.basdat;
}
