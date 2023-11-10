package com.example.gioco;

// Classe Partita
public class Partita {
    private Giocatore[] giocatori;
    private GameData gameData = GameData.getInstance();
    private Mazzo mazzo;
    int n = 8;

    public Partita() {
        this.giocatori = new Giocatore[n];
        this.mazzo = new Mazzo();
    }

    public void iniziaPartita(String[] nomi){
        for (int i = 0; i < gameData.getNumero(); i++){
            giocatori[i] = new Giocatore(nomi[i]);
            for (int j = 0; j < 7; j++) {
                giocatori[i].aggiungiCarta(mazzo.pesca());
            }
        }
    }

    // Metodi per iniziare una nuova partita, gestire i turni dei giocatori e determinare il vincitore...
}