package com.example.gioco;

import javafx.scene.image.Image;
public class Carta {
    private Image img;
    private String desc;
    private Abilita abilita;
    public Carta(Image img, String desc, Abilita abilita) {
        this.img = img;
        this.desc = desc;
        this.abilita = abilita;
    }
    public Image getImg() {
        return this.img;
    }
    public void setImg(Image img) {
        this.img = img;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public Abilita getAbilita() {
        return this.abilita;
    }
    public void setAbilita(Abilita abilita) {
        this.abilita = abilita;
    }
}
