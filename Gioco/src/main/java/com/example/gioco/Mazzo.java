package com.example.gioco;

import java.util.ArrayList;
import java.util.Random;

public class Mazzo {
    private final ArrayList<Carta> mazzo;
    private final ArrayList<Carta> scarti;
    private final Random random;

    public Mazzo() {
        mazzo = new ArrayList<>();
        scarti = new ArrayList<>();
        random = new Random();
    }

    //Realizza il mazzo con un numero preciso di carte
    public void componiMazzo() {
        for(int i = 1; i <= 30; i++) {
            mazzo.add(new CartaBang());
        }
        for(int i = 1; i <= 18; i++) {
            mazzo.add(new CartaMancato());
        }
        for(int i = 1; i <= 4; i++) {
            mazzo.add(new CartaDuello());
        }
        for(int i = 1; i <= 2; i++) {
            mazzo.add(new CartaPerdiCarta());
        }
        for(int i = 1; i <= 2; i++) {
            mazzo.add(new CartaScartaBang());
        }
        for(int i = 1; i <= 4; i++) {
            mazzo.add(new CartaSparaTutti());
        }
        for(int i = 1; i <= 4; i++) {
            mazzo.add(new CartaRecuperaVita());
        }
        mescola();
    }

    //Mescola il mazzo in modo casuale
    public void mescola() {
        for (int i = mazzo.size()-1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Carta temp = mazzo.get(i);
            mazzo.set(i, mazzo.get(j));
            mazzo.set(j, temp);
        }
    }

    //pesca la prima carta dal mazzo e se è finito aggiunge tutti gli scarti rimescolandoli
    public Carta pesca(){
        if (mazzo.isEmpty()) {
            mazzo.addAll(scarti);
            scarti.clear();
            mescola();
        }
        Carta ct = mazzo.getLast();
        mazzo.removeLast();
        return ct;
    }

    public void setMazzo(Carta c) {
        mazzo.add(c);
    }
    public void setScarti(Carta c) {
        scarti.add(c);
    }
    public ArrayList<Carta> getMazzo() {
        return mazzo;
    }
    public ArrayList<Carta> getScarti() {
        return scarti;
    }

    public Carta ultimoScarto(){
        return scarti.getLast();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(Carta c: mazzo) {
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