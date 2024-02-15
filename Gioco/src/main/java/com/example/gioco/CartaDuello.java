package com.example.gioco;

import javafx.scene.image.Image;

public class CartaDuello implements Carta {
    private String desc;
    public CartaDuello(String desc) {
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

    public String toString() {
        return "-" + desc + ";\n";
    }
}
