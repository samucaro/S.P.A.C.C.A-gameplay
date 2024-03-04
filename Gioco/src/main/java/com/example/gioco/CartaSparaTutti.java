package com.example.gioco;

import javafx.scene.image.Image;

public class CartaSparaTutti implements Carta{
    private String desc = "Spara a tutti gli avversari contemporaneamente";
    public CartaSparaTutti() {}

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void usaAbilita() {
        //implementare
    }

    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "SparaTutti";
    }
}
