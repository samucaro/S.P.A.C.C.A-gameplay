package com.example.gioco;

import javafx.scene.image.Image;

public class CartaPerdiCarta implements Carta{
    private String desc = "Pesca la prima carta da un avversario a tua scelta";
    public CartaPerdiCarta() {}
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
        return "PerdiCarta";
    }
}
