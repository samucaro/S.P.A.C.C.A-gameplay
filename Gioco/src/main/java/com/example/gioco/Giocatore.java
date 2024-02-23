package com.example.gioco;
import java.util.ArrayList;
public abstract class Giocatore {
    abstract public void aggiungiCarta(Carta carta);
    abstract int getHp();
    abstract void subisciDanno(int danno);
    abstract void cura(int cura);
    abstract int getHpRimanente();
    abstract public void setMano(ArrayList<Carta> mano);
    abstract public ArrayList<Carta> getMano();
    abstract public void setTurno(boolean turno);
    abstract public boolean getTurno();
}