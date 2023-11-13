package com.example.gioco;

public class Giocatore {
    private Ruoli ruolo;
    //aggiungere variabile di tipo CarteInMano
    //private Personaggi personaggio;

    //Fare costruttore vuoto
    public Giocatore(Ruoli ruolo) { //aggiungi gli altri attributi
        this.ruolo = ruolo;
    }

    public void setRuolo(Ruoli ruolo) {
        this.ruolo = ruolo;
    }
    public Ruoli getRuolo() {
        return ruolo;
    }
}
