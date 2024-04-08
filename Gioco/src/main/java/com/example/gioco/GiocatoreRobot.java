package com.example.gioco;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class GiocatoreRobot extends Giocatore {
    private  String nome;
    private final int HP;
    private int hpRimanente;
    private ArrayList<Carta> mano;
    private boolean turno;
    public GiocatoreRobot(){
        HP = 5;
        hpRimanente = HP;
        mano = new ArrayList<>();
    }

    public void addCarta(Carta carta){
        if(mano.size() == 6) {
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

    public void giocaTurno(MainController mainController, OvalPaneController ovalPaneController, Button button) {
        int cont = 0;
        Iterator<Carta> iteratore = mano.iterator();
        //Il fatto di levare carte durante un ciclo for crea problemi...capire come risolvere
        while(iteratore.hasNext()) {
            Carta carta = iteratore.next();
            if(cont != 1 && carta instanceof CartaBang) {
                cont = 1;
                carta.usaAbilita(ovalPaneController, mainController);
            }
            else if(!(carta instanceof CartaMancato)) {
                carta.usaAbilita(ovalPaneController, mainController);
            }
        }
        cliccaBottone(button);
    }

    //Non funziona
    private void cliccaBottone(Button button) {
        ActionEvent event = new ActionEvent(null, null);
        button.fireEvent(event);
    }

    public void setHpRimanente(int hp) {
        this.hpRimanente=hp;
    }
    public int getHpRimanente() {
        return hpRimanente;
    }
    public void setMano(ArrayList<Carta> mano){
        this.mano=mano;
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
        return "\n***GiocatoreRobot***\n" +
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
