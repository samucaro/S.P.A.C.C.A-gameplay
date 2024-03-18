package com.example.gioco;

import javafx.fxml.FXML;
import javafx.scene.image.Image;

public class CartaScartaBang implements Carta{
    private String desc = "Tutti i tuoi avversari scartano una carta Bang";
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
        return "ScartaBang";
    }
}
