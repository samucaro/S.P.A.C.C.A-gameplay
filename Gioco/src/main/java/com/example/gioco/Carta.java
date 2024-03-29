package com.example.gioco;

import javafx.scene.image.ImageView;

public abstract class Carta {
    public abstract ImageView getImage();
    public abstract String getDesc();
    public abstract void usaAbilita(OvalPaneController ovalPaneController, MainController mainController);

    public abstract String toString();
    public abstract String toStringNome();
}
