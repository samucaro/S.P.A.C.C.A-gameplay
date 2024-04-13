package com.example.gioco;

public class Costumer {
    private int rank;
    private String nome;
    private int punteggio;

    public Costumer(int rank, String nome, int punteggio) {
        this.rank = rank;
        this.nome = nome;
        this.punteggio = punteggio;
    }

    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPunteggio() {
        return punteggio;
    }
    public void setPunteggio(int punteggio) {
        this.punteggio += punteggio;
    }
}
