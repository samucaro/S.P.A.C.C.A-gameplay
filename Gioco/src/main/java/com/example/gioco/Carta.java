package com.example.gioco;

import com.example.gioco.Abilita;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public abstract class Carta {
    public abstract ImageView getImage();
    public abstract String getDesc();
    public abstract void usaAbilita(OvalPaneController ovalPaneController, MainController mainController);

    public abstract String toString();
    public abstract String toStringNome();
}
