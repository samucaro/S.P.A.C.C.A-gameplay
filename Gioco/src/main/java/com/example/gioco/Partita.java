package com.example.gioco;

public class Partita {
    private Giocatore[] giocatori;
    private GameData gameData = GameData.getInstance();
    private Mazzo mazzo;

    public Partita() {
        this.giocatori = gameData.getGG();
        this.mazzo = new Mazzo();
    }

    public void iniziaPartita() {
        for (int i = 0; i < gameData.getNumero(); i++) {
            for (int j = 0; j < 7; j++) {
                giocatori[i].aggiungiCarta(mazzo.pesca());
            }
        }
    }
    // Metodi per iniziare una nuova partita, gestire i turni dei giocatori e determinare il vincitore...
}