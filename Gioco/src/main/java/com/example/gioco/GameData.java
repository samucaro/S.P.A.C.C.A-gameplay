package com.example.gioco;
public class GameData {
    private static GameData instance = null;
    private int numeroGG;
    private Ruoli[] ruolo;
    private String[] pianeta;
    private Boolean[] bot;
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
    public Ruoli getRuolo(int i) {
        return ruolo[i];
    }
    public String getPianeta(int i) {
        return pianeta[i];
    }

    public void setNumero(int numeroGG) {
        this.numeroGG = numeroGG;
    }

    public void setRuolo(int i, Ruoli r) {
        if (ruolo==null)
            ruolo = new Ruoli[numeroGG];
        ruolo[i]=r;
    }
    public void setPianeta(int i, String p) {
        if (pianeta==null)
            pianeta = new String[numeroGG];
        pianeta[i]=p;
    }
    public void setBot(int i, Boolean b) {
        if (bot==null)
            bot = new Boolean[numeroGG];
        bot[i]=b;
    }
    public Giocatore[] getGG() {
        Giocatore[] ggs = new Giocatore[numeroGG];
        for (int i = 0; i<numeroGG; i++){
            if (bot[i]) {
                ggs[i]= new GiocatoreRobot(ruolo[i]);
            } else {
                ggs[i] = new GiocatorePersona(ruolo[i]);
            }
        }
        return ggs;
    }
}
