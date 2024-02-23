package com.example.gioco;

import java.util.*;

public class GameData {
    private int turnoCorrente;
    private static GameData instance = null;
    private final ArrayList<Giocatore> giocatoriPartita = new ArrayList<>();
    private Mazzo mazzo;
    private int numeroGG;
    private int numeroR;
    private int numeroP;
    //private String[] pianeta;
    //private Boolean[] bot;
    public GameData() {
        mazzo = new Mazzo();
    }
    public static GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }
    public void setNumero(int numeroGG) {
        this.numeroGG = numeroGG;
    }
    public void setPersone(int numeroP) {
        this.numeroP = numeroP;
    }
    public void setRobot(int numeroR) {
        this.numeroR = numeroR;
    }
    public void setMazzo(Mazzo mazzo) {
        this.mazzo = mazzo;
    }
    public Mazzo getMazzo() {
        return mazzo;
    }
    public void setTurnoCorrente(int turnoCorrente) {
        this.turnoCorrente = turnoCorrente;
    }
    public int getTurnoCorrente() {
        return turnoCorrente;
    }


    //Assegna ruoli e personaggi seguendo i metodi precedenti
    public void getGGRandom() {
        int indexR = 0;
        int indexP = 0;
        while(indexR < numeroR) {
            Giocatore g = new GiocatoreRobot();
            giocatoriPartita.add(g);
            indexR++;
        }
        while(indexP < numeroP ) {
            Giocatore g = new GiocatorePersona();
            giocatoriPartita.add(g);
            indexP++;
        }
    }

    public int getNumero() {
        return numeroGG;
    }

    public ArrayList<Giocatore> getGiocatoriPartita() {
        return giocatoriPartita;
    }
    /*
    public Ruoli getRuolo(int i) {
        return ruolo[i];
    }
    */

    /*public String getPianeta(int i) {
        return pianeta[i];
    }
    */
    /*
    public void setRuolo(int i, Ruoli r) {
        if (ruolo==null)
            ruolo = new Ruoli[numeroGG];
        ruolo[i]=r;
    }
    */
    /*
    public void setPianeta(int i, String p) {
        if (pianeta==null)
            pianeta = new String[numeroGG];
        pianeta[i]=p;
    }
    */
    /*
    public void setBot(int i, Boolean b) {
        if (bot==null)
            bot = new Boolean[numeroGG];
        bot[i]=b;
    }
    */
    /*
    public Giocatore[] getGG() {
        Giocatore[] ggs = new Giocatore[numeroGG];
        for (int i = 0; i<numeroGG; i++){
            if (bot[i]) {
                ggs[i]= new GiocatoreRobot();
            } else {
                ggs[i] = new GiocatorePersona(ruolo[i]);
            }
        }
        return ggs;
    }
    */
}
