package com.example.gioco;

import javafx.scene.image.Image;

public class CartaMancato implements Carta {
    private String desc = "Colpo schivato!";
    public CartaMancato() {
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
        return "Mancato";
    }
}
