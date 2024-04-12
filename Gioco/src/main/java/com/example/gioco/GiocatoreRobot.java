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

    public void scarta(Carta carta) {
        mano.remove(carta);

    }
    public void subisciDanno(int danno){
        hpRimanente -= danno;
        if(hpRimanente <= 0) {
            hpRimanente = 0;
            TabelloneGiocoController.setMortiEVincitore(this);
            System.out.println("Il giocatore è già eliminato");
        } else {
            OvalPaneController.setVita(this);
        }
    }
    public void cura(int vita){
        if(hpRimanente == HP) {
            System.out.println("Il personaggio ha già la vita massima, non verrà aggiunto nessun punto vita");
        }
        else {
            hpRimanente += vita;
            OvalPaneController.setVita(this);
        }
    }

    public void giocaTurno(TabelloneGiocoController tabelloneGiocoController, OvalPaneController ovalPaneController) {
        tabelloneGiocoController.pescataInizialeGiocatore();
        int cont = 0;
        Carta cc;
        for (int i = 0; i < mano.size(); i++) {
            cc = mano.get(i);
            if (cont < 1 && cc instanceof CartaBang) {
                System.out.println(cc.getClass().getSimpleName());
                cc.usaAbilita(ovalPaneController, tabelloneGiocoController);
                i--;
                cont++;
            }
            else if (!(cc instanceof CartaBang)){
                System.out.println(cc.getClass().getSimpleName());
                cc.usaAbilita(ovalPaneController, tabelloneGiocoController);
                if (!(cc instanceof CartaMancato || cc instanceof CartaPerdiCarta || (cc instanceof CartaRecuperaVita && hpRimanente == 5)))
                    i--;
            }
        }
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
