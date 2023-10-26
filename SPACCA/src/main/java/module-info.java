module com.example.spacca {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.spacca to javafx.fxml;
    exports com.example.spacca;
}