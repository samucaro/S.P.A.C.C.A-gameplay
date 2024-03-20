package com.example.gioco;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CartaBang implements Carta{
    private String desc = "Spara a un tuo avversario";
    public String getFXML(){
        return "CartaBang.fxml";
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
        return "Bang";
    }
}
