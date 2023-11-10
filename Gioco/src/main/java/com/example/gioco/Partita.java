package com.example.gioco;

import java.util.ArrayList;

// Classe Partita
public class Partita {
    private ArrayList<Giocatore> giocatori;
    private Mazzo mazzo;

    public Partita() {
        this.giocatori = new ArrayList<Giocatore>();
        this.mazzo = new Mazzo();
    }

    // Metodi per iniziare una nuova partita, gestire i turni dei giocatori e determinare il vincitore...
}