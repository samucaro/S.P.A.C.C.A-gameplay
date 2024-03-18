package com.example.gioco;

import javafx.fxml.FXML;
import javafx.scene.image.Image;

public class CartaRecuperaVita implements Carta{
    private String desc = "Recuperi un punto vita";
    @FXML
    public void initialize() {
    }
    public String getDesc() {
        return desc;
    }

    public void usaAbilita() {
        //implementare
    }
    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "RecuperaVita";
    }
}
