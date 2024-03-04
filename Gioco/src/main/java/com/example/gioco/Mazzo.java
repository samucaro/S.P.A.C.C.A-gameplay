package com.example.gioco;

import javafx.scene.image.Image;

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
    public void addCarta(Carta c) {
        carte.add(c);
    }
    public void addScarto(Carta c) {
        scarti.add(c);
    }

    public void mescola() {
        for (int i = carte.size()-1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Carta temp = carte.get(i);
            carte.set(i, carte.get(j));
            carte.set(j, temp);
        }
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
    // Metodi per mescolare il mazzo e distribuire le carte...

    @Override
    public String toString() {
        String str = "";
        for(Carta c: carte) {
            str += c.toStringNome() + " ";
        }
        return str;
    }
    public String toStringScarti() {
        String str = "";
        for(Carta c: scarti) {
            str += c.toStringNome() + " ";
        }
        return str;
    }
}