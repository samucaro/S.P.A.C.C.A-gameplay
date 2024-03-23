package com.example.gioco;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class CartaDuello implements Carta {
    private String desc = "A turno tu e il tuo avversario" +
            "scartate un Bang, il primo che rimane senza perde un punto vita. Parte l'avversario.";
    public ImageView getImage(){
        Image cartaImage = new Image(getClass().getResource("CartaBang.png").toString());
        ImageView imageView = new ImageView(cartaImage);
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
        return "Duello";
    }
}
