package com.example.gioco;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class CartaScartaBang implements Carta{
    private String desc = "Tutti i tuoi avversari scartano una carta Bang";
    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaScartaBang.jpg"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        Rectangle clip = new Rectangle(100, 150);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imageView.setClip(clip);
        return imageView;
    }
    @FXML
    public void initialize() {
    }

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
        return "ScartaBang";
    }
}
