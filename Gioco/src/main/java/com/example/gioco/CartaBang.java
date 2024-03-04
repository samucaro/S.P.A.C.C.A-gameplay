package com.example.gioco;

import javafx.fxml.FXML;
import javafx.scene.image.Image;

public class CartaBang implements Carta{
    private String desc = "Spara a un tuo avversario";
    public CartaBang() {
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
        return "Bang";
    }
}
