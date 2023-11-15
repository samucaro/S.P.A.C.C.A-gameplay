module com.example.gioco {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.gioco to javafx.fxml;
    exports com.example.gioco;
    exports com.example.gioco.HomePage;
    opens com.example.gioco.HomePage to javafx.fxml;
    exports com.example.gioco.Giocatore;
    opens com.example.gioco.Giocatore to javafx.fxml;
    exports com.example.gioco.Partita;
    opens com.example.gioco.Partita to javafx.fxml;
}