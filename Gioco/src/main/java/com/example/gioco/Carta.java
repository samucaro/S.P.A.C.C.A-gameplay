package com.example.gioco;

import javafx.scene.image.ImageView;

public abstract class Carta {
    public abstract ImageView getImage();
    public abstract void usaAbilita(OvalPaneController ovalPaneController, TabelloneGiocoController tabelloneGiocoController);
    public abstract String toString();
    public abstract String toStringNome();
}
