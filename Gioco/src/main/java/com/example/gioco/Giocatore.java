package com.example.gioco;
import java.util.ArrayList;
public class Giocatore {
    private String nome;
    private int hp;
    private ArrayList<Carta> mano;

    public Giocatore(String nome) {
        this.nome = nome;
        this.mano = new ArrayList<Carta>();
        hp = 100;
    }
    public void aggiungiCarta(Carta carta){
        mano.add(carta);
    }

    // Metodi per gestire le carte in mano...
}