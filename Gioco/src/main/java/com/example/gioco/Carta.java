package com.example.gioco;

import com.example.gioco.Abilita;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface Carta {
    public String getFXML();
    public String getDesc();
    public void usaAbilita();

    public String toString();
    public String toStringNome();
}
