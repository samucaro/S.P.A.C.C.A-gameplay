package com.example.gioco;

import javafx.scene.image.Image;

public class CartaSparaTutti implements Carta{
    private String desc;
    public CartaSparaTutti(String desc) {
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
