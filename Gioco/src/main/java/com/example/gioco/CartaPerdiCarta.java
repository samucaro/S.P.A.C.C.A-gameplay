package com.example.gioco;

import javafx.scene.image.Image;

public class CartaPerdiCarta implements Carta{
    private String desc;
    public CartaPerdiCarta(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void usaAbilita() {
        //implementare
    }
}
