package com.example.gioco;

import javafx.fxml.FXML;
import javafx.scene.image.Image;

public class CartaSparaTutti implements Carta{
    private String desc = "Spara a tutti gli avversari contemporaneamente";
    public String getFXML(){
        return "CartaSparaTutti.fxml";
    }
    @FXML
    public void initialize() {
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

    @Override
    public String toString() {
        return "-" + desc + ";\n";
    }
    public String toStringNome() {
        return "SparaTutti";
    }
}
