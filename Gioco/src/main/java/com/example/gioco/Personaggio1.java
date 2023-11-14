package com.example.gioco;

public class Personaggio1 implements Effetto {
    private String nome;
    private int hp;
    private String effetto;

    public Personaggio1(String nome, int hp,String effetto) {
        this.nome = nome;
        this.hp = hp;
        this.effetto = effetto;
    }

    @Override
    public void useEffetto() {

    }
}
