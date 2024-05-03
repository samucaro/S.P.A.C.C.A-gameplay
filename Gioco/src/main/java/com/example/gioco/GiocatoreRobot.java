package com.example.gioco;

import java.util.ArrayList;

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

    public void setMano(Carta carta){
        mano.add(carta);
    }

    public void clearMano() {
        mano.clear();
    }

    public void setMano(int i, Carta carta){
        mano.add(i, carta);
    }

    public void scarta(Carta carta) {
        mano.remove(carta);
    }

    public void subisciDanno(int danno, TabelloneGiocoController tg) {
        hpRimanente -= danno;
        if(hpRimanente <= 0) {
            hpRimanente = 0;
            System.out.println("Il giocatore " + nome +" è eliminato");
            tg.setMortiEVincitore(this);
        } else {
            OvalPaneController.setVita(this);
        }
    }
    public void cura(){
        if(hpRimanente == HP) {
            System.out.println("Il personaggio " + nome + " ha già la vita massima, non verrà aggiunto nessun punto vita");
        }
        else {
            hpRimanente += 1;
            OvalPaneController.setVita(this);
        }
    }

    public void giocaTurno(TabelloneGiocoController tabelloneGiocoController, OvalPaneController ovalPaneController) {
        int cont = 0;
        Carta cc;
        for (int i = 0; i < mano.size(); i++) {
            if (TabelloneGiocoController.getNomeVincitore() == "" && hpRimanente > 0) {
                cc = mano.get(i);
                if (cc instanceof CartaBang && cont < 2) {
                    cc.usaAbilita(ovalPaneController, tabelloneGiocoController);
                    i--;
                    cont++;
                } else if (!(cc instanceof CartaBang)) {
                    if ((!(cc instanceof CartaMancato) && !(cc instanceof CartaPerdiCarta)) && !(cc instanceof CartaRecuperaVita && hpRimanente == 5))
                        i--;
                    cc.usaAbilita(ovalPaneController, tabelloneGiocoController);
                }
            }
        }
    }

    public void setHpRimanente(int hp) {
        this.hpRimanente=hp;
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
