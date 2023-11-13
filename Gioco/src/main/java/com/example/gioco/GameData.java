package com.example.gioco;
public class GameData {
    private static GameData instance = null;
    private int numeroGG;
    //private Personaggi personaggio;
    private GameData() {}
    public static GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }
    public int getNumero() {
        return numeroGG;
    }

    public void setNumero(int numeroGG) {
        this.numeroGG = numeroGG;

    }
}
