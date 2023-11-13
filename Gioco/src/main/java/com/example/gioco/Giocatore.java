package com.example.gioco;
import java.util.ArrayList;
public class Giocatore {
    private String nome;
    private int hp;
    private ArrayList<Carta> mano;
    public Giocatore(String nome) {
        this.nome = nome;
        this.mano = new ArrayList<Carta>();
        hp = 100;
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
    public String getNome() {
        return nome;
    }
    public int getHp() {
        return hp;
    }
    public ArrayList<Carta> getMano() {
        return mano;
    }
}