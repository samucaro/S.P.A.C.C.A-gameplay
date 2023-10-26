module Gioco {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.Gioco to javafx.fxml;
    exports com.example.Gioco;
}