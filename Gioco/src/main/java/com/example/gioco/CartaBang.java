package com.example.gioco;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
public class CartaBang extends Carta{
    private final String desc = "Spara a un tuo avversario";
    public ImageView getImage(){
        ImageView imageView = new ImageView(new Image("CartaBang.png"));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    public String getDesc() {
        return desc;
    }
    public void usaAbilita(ArrayList<Giocatore> g, int numGiocatore) {
        boolean var = false;
        for(Carta c: g.get(numGiocatore).getMano()) {
            if(c instanceof CartaMancato) {
                var = true;
                g.get(numGiocatore).scarta(c);
                break;
            }
        }
        if(!var) {
            g.get(numGiocatore).subisciDanno(1);
        }
    }
    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "Bang";
    }
}
