package com.example.gioco;

import javafx.scene.image.Image;

public class CartaDuello implements Carta {
    private String desc = "A turno tu e il tuo avversario" +
            "scartate un Bang, il primo che rimane senza perde un punto vita. Parte l'avversario.";
    public CartaDuello() {}

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
        return "Duello";
    }
}
