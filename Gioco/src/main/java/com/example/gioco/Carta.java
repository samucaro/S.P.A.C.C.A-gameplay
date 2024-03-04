package com.example.gioco;

import com.example.gioco.Abilita;
import javafx.scene.image.Image;
public interface Carta {
    public String getDesc();
    public void usaAbilita();

    public String toString();
    public String toStringNome();
}
