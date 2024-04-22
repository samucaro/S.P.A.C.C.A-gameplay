package com.example.gioco;

import java.util.ArrayList;
public abstract class Giocatore {
    abstract public void setMano(Carta carta);
    abstract void subisciDanno(int danno, TabelloneGiocoController tg);
    abstract void cura();
    abstract void scarta(Carta carta);
    abstract void setHpRimanente(int hp);
    abstract int getHpRimanente();
    abstract void setNome(String n);
    abstract String getNome();
    abstract public void setMano(ArrayList<Carta> mano);
    abstract public ArrayList<Carta> getMano(); //NON usata
    abstract public void setTurno(boolean turno); //NON usata
    abstract public boolean getTurno(); //NON usata
    abstract public String toStringMano();
}