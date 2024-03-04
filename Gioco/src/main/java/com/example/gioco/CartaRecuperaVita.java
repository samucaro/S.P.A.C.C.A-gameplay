package com.example.gioco;

import javafx.scene.image.Image;

public class CartaRecuperaVita implements Carta{
    private String desc = "Recuperi un punto vita";
    public CartaRecuperaVita() {}
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
