package com.example.gioco;
import java.util.ArrayList;
public abstract class Giocatore {


    abstract void aggiungiCarta(Carta carta);
    abstract void subisciDanno(int danno);
    abstract void cura(int cura);
    abstract public String getNome();
    abstract int getHp();
    abstract public ArrayList<Carta> getMano();
}