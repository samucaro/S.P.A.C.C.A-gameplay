package com.example.gioco;

import java.util.ArrayList;

public class GiocatoreRobot extends Giocatore {
    private final Ruoli ruolo;
    private int hpRimanente;
    private ArrayList<Carta> mano;
    private final Personaggi personaggio;
    public GiocatoreRobot(Ruoli ruolo, Personaggi personaggio){
        this.ruolo = ruolo;
        mano = new ArrayList<Carta>();
        this.personaggio = personaggio;
        hpRimanente = personaggio.getHp();
    }

    public void aggiungiCarta(Carta carta){
        if(mano.size() == 6) { //modificare con il numero massimo di carte in mano
            System.out.println("Hai già il numero massimo di carte in mano");
        }
        else {
            mano.add(carta);
        }
    }
    public void subisciDanno(int danno){
        if(hpRimanente == 0) {
            System.out.println("Il giocatore è già eliminato");
        }
        hpRimanente-=danno;
    }
    public void cura(int vita){
        if(hpRimanente == personaggio.getHp()) { //l'hp del personaggio è final e non può cambiare
            System.out.println("Il personaggio ha già la vita massima, non verrà aggiunto nessun punto vita");
        }
        else {
            hpRimanente+=vita; //se si vuole aggiungere più fi una vita fare un ciclo for, meglio lasciare che si può aggiungere solo una vita per volta
        }
    }
    public Ruoli getRuolo() {
        return ruolo;
    }
    public int getHpRimanente() {
        return hpRimanente;
    }
    public ArrayList<Carta> getMano() {
        return mano;
    }
    @Override
    public String toString() {
        return "GiocatoreRobot{" +
                "ruolo=" + ruolo +
                ", hpRimanente=" + hpRimanente +
                ", mano=" + mano +
                ", personaggio=" + personaggio +
                '}';
    }

}
