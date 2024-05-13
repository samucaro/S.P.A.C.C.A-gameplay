package com.example.gioco;

public class Costumer {
    private int rank, punteggio;
    private String giocatore;

    public Costumer(int rank, String giocatore, int punteggio) {
        this.rank = rank;
        this.giocatore = giocatore;
        this.punteggio = punteggio;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    public int getRank() {
        return rank;
    }
    public String getGiocatore() {
        return giocatore;
    }
    public void setGiocatore(String nome) {
        this.giocatore = nome;
    }
    public int getPunteggio() {
        return punteggio;
    }
    public void setPunteggio(int punteggio) {
        this.punteggio += punteggio;
    }
}
