package com.example.gioco;

public class Personaggio6 implements Personaggi{
    private final String nome;
    private final int hp;
    private final String effetto;

    public Personaggio6(String nome, int hp, String effetto) {
        this.nome = nome;
        this.hp = hp;
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
    @Override
    public String toString() {
        return "[nome: " + nome + ", hpIniziale: " + hp + ", effetto: " + effetto + "]";
    }
}
