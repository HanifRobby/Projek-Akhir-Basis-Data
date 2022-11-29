module com.basdat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.basdat to javafx.fxml;
    exports com.basdat;
    exports com.basdat.controller;
    opens com.basdat.controller to javafx.fxml;
    exports com.basdat.db_models;
    opens com.basdat.db_models to javafx.fxml;
}
