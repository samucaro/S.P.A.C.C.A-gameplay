package com.example.gioco;
public class Carta {
    private String valore;
    private String seme;
    private Abilita abilita;

    public Carta(String valore, String seme, Abilita abilita) {
        this.valore = valore;
        this.seme = seme;
        this.abilita = abilita;
    }

    public void usaAbilita(Giocatore giocatore) {
        abilita.esegui(giocatore);
    }

    // Metodi getter e setter...
}