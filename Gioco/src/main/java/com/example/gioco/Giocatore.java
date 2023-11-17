package com.example.gioco;
import java.util.ArrayList;
public abstract class Giocatore {
    abstract public void aggiungiCarta(Carta carta);
    abstract void subisciDanno(int danno);
    abstract void cura(int cura);
    abstract void setRuolo(Ruoli ruolo);
    abstract void setPersonaggio(Personaggi personaggio);
    abstract public Ruoli getRuolo();
    abstract int getHp();
    abstract public ArrayList<Carta> getMano();

}