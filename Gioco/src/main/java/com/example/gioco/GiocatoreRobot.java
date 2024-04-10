package com.example.gioco;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class GiocatoreRobot extends Giocatore {
    private  String nome;
    private final int HP;
    private int hpRimanente;
    private ArrayList<Carta> mano;
    private boolean turno;
    private GameData gameData;

    public GiocatoreRobot(){
        HP = 5;
        hpRimanente = HP;
        mano = new ArrayList<>();
        gameData = GameData.getInstance();
    }

    public void addCarta(Carta carta){
        mano.add(carta);
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
        mainController.pescataInizialeGiocatore();
        System.out.println("mano:" + mano.size());
        int cont = 0;
        Carta cc;
        for (int i = 0; i < mano.size(); i++) {
            cc = mano.get(i);
            if (cont < 1 && cc instanceof CartaBang) {
                System.out.println("SONO QUI 1");
                cc.usaAbilita(ovalPaneController, mainController);
                i--;
                cont++;
            }
            else if (!(cc instanceof CartaBang)){
                cc.usaAbilita(ovalPaneController, mainController);
                if (!(cc instanceof CartaMancato || cc instanceof CartaPerdiCarta || (cc instanceof CartaRecuperaVita && hpRimanente == 5)))
                    i--;
            }
        }
        cliccaBottone(button);
    }

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
            if (c != null)
                str.append(c.toStringNome()).append(" ");
        }
        return str.toString();
    }
}
