package com.example.gioco;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class CartaSparaTutti extends Carta{
    private String desc = "Spara a tutti gli avversari contemporaneamente";
    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaSparaTutti.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void usaAbilita(ArrayList<Giocatore> g, int numGiocatore) {
        int cont = 0;
        boolean var = false;
        for(Giocatore p: g) {
            if(cont != numGiocatore) {
                for (Carta c : p.getMano()) {
                    if (c instanceof CartaMancato) {
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
        return "SparaTutti";
    }
}
