package com.example.gioco;

import java.util.ArrayList;
import java.util.Random;

public class Mazzo {
    private ArrayList<Carta> carte;
    private ArrayList<Carta> scarti;
    private Random random;

    public Mazzo() {
        this.carte = new ArrayList<Carta>();
        this.scarti = new ArrayList<Carta>();
        this.random = new Random();

    }

    public void mescola() {
        for (int i = carte.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Carta temp = carte.get(i);
            carte.set(i, carte.get(j));
            carte.set(j, temp);
        }
    }
    public Carta pesca(){
        Carta ct = carte.get(0);
        carte.remove(0);
        if (carte.size()==0) {
            carte = scarti;
            mescola();
        }
        return ct;
    }
    public void scarta(Carta scarto){
        scarti.add(scarto);
    }

    // Metodi per mescolare il mazzo e distribuire le carte...
}