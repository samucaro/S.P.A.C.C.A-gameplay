package com.example.gioco;

public class Personaggio3 implements Personaggi{
    private String nome;
    private final int hp;
    private String effetto;

    public Personaggio3(String nome, int hp, String effetto) {
        this.nome = nome;
        this.hp = hp;
        this.effetto = effetto;
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
    @Override
    public void useEffetto() {
        //implementare l'effetto scritto nella stringa effetto
    }
}
