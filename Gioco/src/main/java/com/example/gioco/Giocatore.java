package com.example.gioco;

import java.util.ArrayList;
public abstract class Giocatore {
    abstract public void setMano(Carta carta);
    abstract public void setMano(int i, Carta carta);
    abstract void subisciDanno(int danno, TabelloneGiocoController tg);
    abstract void cura();
    abstract void scarta(Carta carta);
    abstract void setHpRimanente(int hp);
    abstract int getHpRimanente();
    abstract void setNome(String n);
    abstract String getNome();
    abstract public ArrayList<Carta> getMano();
    abstract public String toStringMano();
}