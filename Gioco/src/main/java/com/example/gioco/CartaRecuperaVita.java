package com.example.gioco;

import javafx.scene.image.Image;

public class CartaRecuperaVita implements Carta{
    private Image img;
    private String desc;
    private GameData gameData = GameData.getInstance();
    public CartaRecuperaVita(Image img, String desc) {
        this.img = img;
        this.desc = desc;
    }

    public Image getImg() {
        return img;
    }
    public void setImg(Image img) {
        this.img = img;
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
