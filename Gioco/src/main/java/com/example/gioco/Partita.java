package com.example.gioco;

import java.util.ArrayList;

public class Partita {
    private int numero;
    private Stato stato;
    private Mazzo mazzo;
    private int turno;
    private ArrayList<Giocatore> giocatori;
    private String codice;
    private Giocatore vincitore;
     public Partita(String codice, int numero, Stato stato, int turno, Mazzo mazzo, ArrayList<Giocatore> giocatori) {
         this.codice = codice;
         this.numero = numero;
         this.stato = stato;
         this.turno = turno;
         this.mazzo = mazzo;
         this.giocatori = giocatori;
     }
    public Partita(int numero, Stato stato, int turno, Mazzo mazzo, ArrayList<Giocatore> giocatori) {
        this.numero = numero;
        this.stato = stato;
        this.turno = turno;
        this.mazzo = mazzo;
        this.giocatori = giocatori;
    }

    public Partita() {

    }

    public void setCodice(String codice) {
         this.codice = codice;
    }
    public void setNumero(int numero) {
         this.numero = numero;
    }
    public void setStato(Stato stato) {
        this.stato = stato;
    }
    public void setTurno(int turno) {
        this.turno = turno;
    }
    public void setMazzo(Mazzo mazzo) {
        this.mazzo = mazzo;
    }
    public void setGiocatori(ArrayList<Giocatore> giocatori) {
        this.giocatori = giocatori;
    }

    public String getCodice() {
        return codice;
    }
    public int getNumero() {
        return numero;
    }
    public Stato getStato() {
        return stato;
    }
    public int getTurno() {
        return turno;
    }
    public Mazzo getMazzo() {
        return mazzo;
    }
    public ArrayList<Giocatore> setGiocatori() {
        return giocatori;
    }

    public String toString() {
         return "Numero Partita: " + numero +
                 "\nStato: " + stato +
                 "\nTurno: " + turno +
                 "\nMazzo: " + mazzo.toString() +
                 "\nGiocatori: " + giocatori.toString() +
                 "\n******************************" +
                 "\nVincitore: " + vincitore;
    }
}
