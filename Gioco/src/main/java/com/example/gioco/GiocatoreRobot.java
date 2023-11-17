package com.example.gioco;

import java.util.ArrayList;

public class GiocatoreRobot extends Giocatore {
    private Ruoli ruolo;
    private int hp;
    private ArrayList<Carta> mano;
    private Personaggi personaggio;
    public GiocatoreRobot(Ruoli ruolo, Personaggi personaggio) {
        this.ruolo = ruolo;
        this.mano = new ArrayList<Carta>();
        this.personaggio = personaggio;
    }
    public GiocatoreRobot() {
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

    public void setPersonaggio(Personaggi personaggio) {
        this.personaggio = personaggio;
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
