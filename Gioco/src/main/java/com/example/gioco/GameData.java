package com.example.gioco;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class GameData {
    private static GameData instance = null;
    int[][] matrix = {
            {1,1,1,0}, //sceriffi, rinnegato, fuorilegge, vice
            {1,1,2,0},
            {1,1,2,1},
            {1,1,3,1},
            {1,1,3,2},
            {1,1,4,2}
    };
    private int numeroGG;
    private int numeroPersone; //da assegnarli il valore
    private int numeroRobot; //da assegnarli il valore
    private final Personaggi[] arrayPersonaggi = new Personaggi[8]; //vettore con tutti i personaggi
    private Ruoli[] ruolo;
    private String[] pianeta;
    private Boolean[] bot;
    ArrayList<Ruoli> ruoliPartita = new ArrayList<>();
    private GameData() {}
    public static GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }

    private void setRuoliPartita() {
        for(int i=0; i<4; i++) {
            int val = matrix[numeroGG][i];
            if(i==0) {
                while(val>0) {
                    ruoliPartita.add(Ruoli.SCERIFFO);
                    val--;
                }
            }
            if(i==1) {
                while(val>0) {
                    ruoliPartita.add(Ruoli.RINNEGATO);
                    val--;
                }
            }
            if(i==2) {
                while(val>0) {
                    ruoliPartita.add(Ruoli.FUORILEGGE);
                    val--;
                }
            }
            if(i==3) {
                while(val>0) {
                    ruoliPartita.add(Ruoli.VICE);
                    val--;
                }
            }
        }
    }

    public void aggiugniPersonaggi() {
        arrayPersonaggi[0] = new Personaggio1("Samuel", 4, "Pesca la prima carta del mazzo");
        /*
        arrayPersonaggi[1] = new Personaggio2();
        arrayPersonaggi[2] = new Personaggio3();
        arrayPersonaggi[3] = new Personaggio4();
        arrayPersonaggi[4] = new Personaggio5();
        arrayPersonaggi[5] = new Personaggio6();
        arrayPersonaggi[6] = new Personaggio7();
        arrayPersonaggi[7] = new Personaggio8();
        */
    }
    private Set<Integer> setPersonaggi() {
        int min = 0;
        int max = 7;
        int numeriDaStampare = numeroGG;
        return generaNumeriCasualiUnici(min, max, numeriDaStampare);
    }
    private static Set<Integer> generaNumeriCasualiUnici(int min, int max, int count) {
        if (count > (max - min + 1) || count < 0) {
            throw new IllegalArgumentException("Invalid range or count");
        }
        Set<Integer> numeriCasualiUnici = new HashSet<>();
        Random rand = new Random();
        while (numeriCasualiUnici.size() < count) {
            int numeroCasuale = rand.nextInt((max - min) + 1) + min;
            numeriCasualiUnici.add(numeroCasuale);
        }
        return numeriCasualiUnici;
    }
    public int getNumero() {
        return numeroGG;
    }
    public Ruoli getRuolo(int i) {
        return ruolo[i];
    }
    public String getPianeta(int i) {
        return pianeta[i];
    }

    public void setNumero(int numeroGG) {
        this.numeroGG = numeroGG;
    }

    public void setRuolo(int i, Ruoli r) {
        if (ruolo==null)
            ruolo = new Ruoli[numeroGG];
        ruolo[i]=r;
    }
    public void setPianeta(int i, String p) {
        if (pianeta==null)
            pianeta = new String[numeroGG];
        pianeta[i]=p;
    }
    public void setBot(int i, Boolean b) {
        if (bot==null)
            bot = new Boolean[numeroGG];
        bot[i]=b;
    }
    public Giocatore[] getGG() {
        Giocatore[] ggs = new Giocatore[numeroGG];
        for (int i = 0; i<numeroGG; i++){
            if (bot[i]) {
                ggs[i]= new GiocatoreRobot();
            } else {
                ggs[i] = new GiocatorePersona(ruolo[i]);
            }
        }
        return ggs;
    }
    private int metodino() {
        int min = 0;
        int max = ruoliPartita.size()-1;
        return (int) (Math.random()*(max-min+1))+1;
    }
    public Giocatore[] getGGRandom() {
        ArrayList<Giocatore> giocatoriPartita = new ArrayList<>();
        int index = 0;
        setRuoliPartita();
        while(index < numeroRobot ) {
            Giocatore g = new GiocatoreRobot();
            int val = metodino();
            g.setRuolo(ruoliPartita.get(val));
            ruoliPartita.remove(val);
            giocatoriPartita.add(g);
            index++;
        }
        index=0;
        //setPersonaggi() creare un vettore Personaggi
        while(index < numeroPersone ) {
            Giocatore g = new GiocatorePersona();
            g.setPersonaggio(ruoliPartita.get(val)); //creare metodo setPersonaggio in Giocatore e implementarlo in Giocatore Persona
            ruoliPartita.remove(val);
            giocatoriPartita.add(g);
        }
        Giocatore[] ggs = new Giocatore[numeroGG];
        for (int i = 0; i<numeroGG; i++){
            if (bot[i]) {
                ggs[i]= new GiocatoreRobot();
            } else {
                ggs[i] = new GiocatorePersona(ruolo[i]);
            }
        }
        return ggs;
    }
}
