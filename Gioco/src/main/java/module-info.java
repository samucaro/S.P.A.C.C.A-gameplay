module com.example.gioco {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gioco to javafx.fxml;
    exports com.example.gioco;
}