package com.example.gioco;

import java.util.ArrayList;

public class Partita {
    private ArrayList<Giocatore> giocatori;
    private Mazzo mazzo;
    private int codice;
     public Partita(ArrayList<Giocatore> giocatori, Mazzo mazzo) {
         this.giocatori = giocatori;
         this.mazzo = mazzo;
     }

     
}
