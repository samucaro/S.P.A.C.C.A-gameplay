package com.example.gioco.Giocatore;

public interface Personaggi {
    void useEffetto();

    void setNome(String nome);
    void setEffetto(String effetto);

    String getNome();
    String getEffetto();
    int getHp();
}
