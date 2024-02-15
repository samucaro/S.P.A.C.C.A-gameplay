package com.example.gioco;

import javafx.scene.image.Image;

public class CartaBang implements Carta{
    private String desc;
    public CartaBang( String desc) {
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
