package com.example.gioco.Giocatore;

public class Personaggio1 implements Personaggi {
    private String nome;
    private final int hp;
    private String effetto;

    public Personaggio1(String nome, int hp,String effetto) {
        this.nome = nome;
        this.hp = hp;
        this.effetto = effetto;
    }

    public void useEffetto() {

    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setEffetto(String effetto) {
        this.effetto = effetto;
    }

    public String getNome() {
        return nome;
    }
    public String getEffetto() {
        return effetto;
    }
    public int getHp() {
        return hp;
    }
}
