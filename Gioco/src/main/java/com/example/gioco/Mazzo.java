package com.example.gioco;

import java.util.ArrayList;
import java.util.Random;

public class Mazzo {
    private ArrayList<Carta> carte;
    private final ArrayList<Carta> scarti;
    private final Random random;

    public Mazzo() {
        this.carte = new ArrayList<>();
        this.scarti = new ArrayList<>();
        this.random = new Random();
    }

    //Realizza il mazzo con un numero preciso di carte
    public void componiMazzo() {
        for(int i = 1; i <= 30; i++) {
            carte.add(new CartaBang());
        }
        for(int i = 1; i <= 18; i++) {
            carte.add(new CartaMancato());
        }
        for(int i = 1; i <= 2; i++) {
            carte.add(new CartaDuello());
        }
        for(int i = 1; i <= 2; i++) {
            carte.add(new CartaPerdiCarta());
        }
        for(int i = 1; i <= 2; i++) {
            carte.add(new CartaScartaBang());
        }
        for(int i = 1; i <= 2; i++) {
            carte.add(new CartaSparaTutti());
        }
        for(int i = 1; i <= 8; i++) {
            carte.add(new CartaRecuperaVita());
        }
        mescola();
    }

    public void mescola() {
        for (int i = carte.size()-1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Carta temp = carte.get(i);
            carte.set(i, carte.get(j));
            carte.set(j, temp);
        }
    }

    public void addCarta(Carta c) {
        carte.add(c);
    }
    public void addScarto(Carta c) {
        scarti.add(c);
    }

    public Carta pesca(){
        if (carte.isEmpty()) {
            carte = scarti;
            mescola();
        }
        Carta ct = carte.getLast();
        carte.removeLast();
        return ct;
    }
    public void scarta(Carta scarto){
        scarti.add(scarto);
    }

    public Carta ultimoScarto(){
        return scarti.getLast();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(Carta c: carte) {
            str.append(c.toStringNome()).append(" ");
        }
        return str.toString();
    }
    public String toStringScarti() {
        StringBuilder str = new StringBuilder();
        for(Carta c: scarti) {
            str.append(c.toStringNome()).append(" ");
        }
        return str.toString();
    }
}