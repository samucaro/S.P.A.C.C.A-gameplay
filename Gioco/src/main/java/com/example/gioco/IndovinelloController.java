package com.example.gioco;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class IndovinelloController {
    @FXML
    private TextField risposta;
    @FXML
    private Label datiAccesso;

    @FXML
    public void initialize() {
        risposta.textProperty().addListener((observable, oldValue, newValue) -> {
            verificaRisposta();
        });
    }

    private void verificaRisposta() {
        datiAccesso.setVisible(risposta.getText().equalsIgnoreCase("1") ||
                risposta.getText().equalsIgnoreCase("una") ||
                risposta.getText().equalsIgnoreCase("uno"));
    }
}
