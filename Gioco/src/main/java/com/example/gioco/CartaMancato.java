package com.example.gioco;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class CartaMancato extends Carta {
    private String desc = "Colpo schivato!";
    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaMancato.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    public String getDesc() {
        return desc;
    }
    public void usaAbilita(OvalPaneController ovalPaneController, MainController mainController) {
    }
    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "Mancato";
    }
}
