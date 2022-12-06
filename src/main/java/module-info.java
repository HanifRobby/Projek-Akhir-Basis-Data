module com.basdat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.basdat to javafx.fxml;
    exports com.basdat;
    exports com.basdat.controller;
    opens com.basdat.controller to javafx.fxml;
    exports com.basdat.db_models;
    opens com.basdat.db_models to javafx.fxml;
    exports com.basdat.controller.admin_controller;
    opens com.basdat.controller.admin_controller to javafx.fxml;
    exports com.basdat.controller.customer_controller;
    opens com.basdat.controller.customer_controller to javafx.fxml;
}
