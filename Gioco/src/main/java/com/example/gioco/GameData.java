package com.example.gioco;
public class GameData {
    private static GameData instance = null;
    private int numeroGG;
    private String[] nomiGG;

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
    public String getNome(int i) {
        return nomiGG[i];
    }

    public void setNumero(int numeroGG) {
        this.numeroGG = numeroGG;
    }

    public void setNomi(String[] noomiGG) {
        this.nomiGG = noomiGG;
    }
}
