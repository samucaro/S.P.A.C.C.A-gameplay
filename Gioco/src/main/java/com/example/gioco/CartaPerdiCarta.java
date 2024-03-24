package com.example.gioco;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class CartaPerdiCarta extends Carta{
    private String desc = "Pesca la prima carta da un avversario a tua scelta";
    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaPescaCarta.png"));
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

    public void usaAbilita(ArrayList<Giocatore> g, int numGiocatore) {
        int num = (int) (Math.random() * (4 + 1)) + 4;
        g.get(numGiocatore).scarta(g.get(numGiocatore).getMano().get(num));
    }

    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "PerdiCarta";
    }
}
