package com.example.gioco;

import java.util.ArrayList;

// Classe Giocatore
public class Giocatore {
    private String nome;
    private ArrayList<Carta> mano;

    public Giocatore(String nome) {
        this.nome = nome;
        this.mano = new ArrayList<Carta>();
    }

    // Metodi per gestire le carte in mano...
}