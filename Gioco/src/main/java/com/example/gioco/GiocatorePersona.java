package com.example.gioco;

import java.util.ArrayList;

public class GiocatorePersona extends Giocatore{
    private Ruoli ruolo;
    //private Personaggi personaggio;
    private int hp;
    private ArrayList<Carta> mano;
    public GiocatorePersona(Ruoli ruolo){
        this.ruolo = ruolo;
        this.mano = new ArrayList<Carta>();
        hp = 100;
    }
    public GiocatorePersona() {
        this.mano = new ArrayList<Carta>();
    }

    public void aggiungiCarta(Carta carta){
        mano.add(carta);
    }
    public void subisciDanno(int danno){
        hp=hp-danno;
    }
    public void cura(int cura){
        hp= Math.min(hp + cura, 100);
    }
    public void setRuolo(Ruoli ruolo) {
        this.ruolo = ruolo;
    }
    public Ruoli getRuolo() {
        return ruolo;
    }
    public int getHp() {
        return hp;
    }
    public ArrayList<Carta> getMano() {
        return mano;
    }
}