package com.example.gioco;

import java.util.ArrayList;

public class GiocatorePersona extends Giocatore{
    private  String nome;
    private final int HP = 5;
    private int hpRimanente;
    private ArrayList<Carta> mano;
    private boolean turno;
    public GiocatorePersona(){
        hpRimanente = HP;
        mano = new ArrayList<Carta>();
    }
    /*public GiocatorePersona() {
        this.mano = new ArrayList<Carta>();
    }
    */
    public void aggiungiCarta(Carta carta){
        mano.add(carta);
    }
    public void subisciDanno(int danno){
        if(hpRimanente == 0) {
            System.out.println("Il giocatore è già eliminato");
        }
        hpRimanente-=danno;
    }
    public void cura(int vita){
        if(hpRimanente == HP) { //l'hp del personaggio è final e non può cambiare
            System.out.println("Il personaggio ha già la vita massima, non verrà aggiunto nessun punto vita");
        }
        else {
            hpRimanente+=vita; //se si vuole aggiungere più fi una vita fare un ciclo for, meglio lasciare che si può aggiungere solo una vita per volta
        }
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
    public void setTurno(boolean turno) {
        this.turno = turno;
    }
    public boolean getTurno() {
        return turno;
    }
    public int getHp() {
        return HP;
    }

    @Override
    public String toString() {
        return "\n***GiocatorePersona***\n" +
                "\n-hpRimanente: " + hpRimanente +
                ";\n-mano: " + mano +
                ";\n******************************************************************" +
                "**********************************************************";
    }
}