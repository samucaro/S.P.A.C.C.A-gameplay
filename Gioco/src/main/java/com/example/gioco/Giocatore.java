package com.example.gioco;

import java.util.ArrayList;
public abstract class Giocatore {
    abstract public void addCarta(Carta carta);
    abstract void subisciDanno(int danno);
    abstract void cura(int cura);
    abstract void scarta(Carta carta);
    abstract void setHpRimanente(int hp);
    abstract int getHpRimanente();
    abstract void setNome(String n);
    abstract String getNome();
    abstract public void setMano(ArrayList<Carta> mano);
    abstract public ArrayList<Carta> getMano();
    abstract public void setTurno(boolean turno);
    abstract public boolean getTurno();
    abstract public String toStringMano();
}