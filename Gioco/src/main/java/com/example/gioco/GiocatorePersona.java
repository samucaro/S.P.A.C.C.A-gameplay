package com.example.gioco;

import java.util.ArrayList;

public class GiocatorePersona extends Giocatore{
    private  String nome;
    private final int HP;
    private int hpRimanente;
    private ArrayList<Carta> mano;
    private boolean turno;
    public GiocatorePersona(){
        HP = 5;
        hpRimanente = HP;
        mano = new ArrayList<>();
    }
    public void addCarta(Carta carta){
        if(mano.size() == 5) {
            System.out.println("Hai già il numero massimo di carte in mano");
        }
        else {
            mano.add(carta);
        }
    }
    public void scarta(Carta carta) {
        mano.remove(carta);
    }
    public void subisciDanno(int danno){
        if(hpRimanente == 0) {
            System.out.println("Il giocatore è già eliminato");
        }
        hpRimanente -= danno;
    }
    public void cura(int vita){
        if(hpRimanente == HP) {
            System.out.println("Il personaggio ha già la vita massima, non verrà aggiunto nessun punto vita");
        }
        else {
            hpRimanente += vita;
        }
    }
    public void setHpRimanente(int hp) {
        this.hpRimanente = hp;
    }
    public int getHpRimanente() {
        return hpRimanente;
    }
    public void setMano(ArrayList<Carta> mano) {
        this.mano = mano;
    }
    public ArrayList<Carta> getMano() {
        return mano;
    }
    public void setNome(String n) {
        this.nome = n;
    }
    public String getNome() {
        return nome;
    }
    public void setTurno(boolean turno) {
        this.turno = turno;
    }
    public boolean getTurno() {
        return turno;
    }
    @Override
    public String toString() {
        return "\n***GiocatorePersona***\n" +
                "\n-hpRimanente: " + hpRimanente +
                ";\n-mano: " + toStringMano() +
                ";\n******************************************************************" +
                "**********************************************************";
    }
    public String toStringMano() {
        StringBuilder str = new StringBuilder();
        for(Carta c: mano) {
            str.append(c.toStringNome()).append(" ");
        }
        return str.toString();
    }
}