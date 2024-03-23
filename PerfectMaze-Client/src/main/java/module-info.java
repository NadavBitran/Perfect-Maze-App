module com.example.perfectmazeclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens com.example.perfectmazeclient to javafx.fxml;
    exports com.example.perfectmazeclient;
    exports com.example.perfectmazeclient.dm;
    opens com.example.perfectmazeclient.dm to javafx.fxml;
    exports com.example.perfectmazeclient.util;
    opens com.example.perfectmazeclient.util to javafx.fxml;
    exports com.example.perfectmazeclient.validation;
    opens com.example.perfectmazeclient.validation to javafx.fxml;
    exports com.example.perfectmazeclient.constants;
    opens com.example.perfectmazeclient.constants to javafx.fxml;
    exports com.example.perfectmazeclient.views;
    opens com.example.perfectmazeclient.views to javafx.fxml;
}