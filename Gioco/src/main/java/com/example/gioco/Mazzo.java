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
        componiMazzo();
    }

    private void componiMazzo() {
        for(int i = 1; i <= 30; i++) {
            carte.add(new CartaBang("Spara a un tuo avversario"));
        }
        for(int i = 1; i <= 18; i++) {
            carte.add(new CartaMancato("Colpo schivato!"));
        }
        for(int i = 1; i <= 2; i++) {
            carte.add(new CartaDuello("A turno tu e il tuo avversario" +
                    "scartate un Bang, il primo che rimane senza perde un punto vita. Parte l'avversario."));
        }
        for(int i = 1; i <= 2; i++) {
            carte.add(new CartaPerdiCarta("Pesca la prima carta da un avversario a tua scelta"));
        }
        for(int i = 1; i <= 2; i++) {
            carte.add(new CartaScartaBang("Tutti i tuoi avversari scartano una carta Bang"));
        }
        for(int i = 1; i <= 2; i++) {
            carte.add(new CartaSparaTutti("Spara a tutti gli avversari contemporaneamente"));
        }
        for(int i = 1; i <= 8; i++) {
            carte.add(new CartaRecuperaVita("Recuperi un punto vita"));
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