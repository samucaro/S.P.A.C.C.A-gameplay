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

    public void setMano(Carta carta){
        mano.add(carta);
    }

    public void setMano(int i, Carta carta){
        mano.add(i, carta);
    }

    public void scarta(Carta carta) {
        mano.remove(carta);
    }

    public void subisciDanno(int danno, TabelloneGiocoController tg){
        hpRimanente -= danno;
        if(hpRimanente <= 0) {
            hpRimanente = 0;
            System.out.println("Il giocatore è già eliminato");
            tg.setMortiEVincitore(this);
        } else {
            OvalPaneController.setVita(this);
        }
    }

    public void cura(){
        if(hpRimanente == HP) {
            System.out.println("Il personaggio ha già la vita massima, non verrà aggiunto nessun punto vita");
        }
        else {
            hpRimanente += 1;
            OvalPaneController.setVita(this);
        }
    }

    public void setHpRimanente(int hp) {
        this.hpRimanente = hp;
    }

    public int getHpRimanente() {
        return hpRimanente;
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
            if (c != null)
                str.append(c.toStringNome()).append(" ");
        }
        return str.toString();
    }
}