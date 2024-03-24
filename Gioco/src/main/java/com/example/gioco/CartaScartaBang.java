package com.example.gioco;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class CartaScartaBang extends Carta{
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

    public void usaAbilita(ArrayList<Giocatore> g, int numGiocatore) {
        int cont = 0;
        boolean var = false;
        for(Giocatore p: g) {
            if(cont != numGiocatore) {
                for (Carta c : p.getMano()) {
                    if (c instanceof CartaScartaBang) {
                        var = true;
                        p.scarta(c);
                        break;
                    }
                }
                if(!var) {
                    p.subisciDanno(1);
                }
            }
            cont++;
        }
    }

    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "ScartaBang";
    }
}
