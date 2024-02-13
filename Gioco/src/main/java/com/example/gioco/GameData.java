package com.example.gioco;

import java.util.*;

public class GameData {
    private static GameData instance = null;
    private ArrayList<Giocatore> giocatoriPartita = new ArrayList<>();
    private Mazzo mazzo;
    int[][] matrix = {
            {1,1,1,0}, //sceriffi, rinnegato, fuorilegge, vice
            {1,1,2,0},
            {1,1,2,1},
            {1,1,3,1},
            {1,1,3,2},
            {1,1,4,2}
    };
    private int numeroGG;
    private int numeroR;
    private int numeroP;
    private final Personaggi[] arrayPersonaggi = new Personaggi[8]; //vettore con tutti i personaggi
    //private Ruoli[] ruolo;
    //private String[] pianeta;
    //private Boolean[] bot;
    ArrayList<Ruoli> ruoliPartita = new ArrayList<>();
    public GameData() {
        aggiugniPersonaggi();
        mazzo = new Mazzo();
    }
    public static GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }
    public void setNumero(int numeroGG) {
        this.numeroGG = numeroGG;
    }
    public void setPersone(int numeroP) {
        this.numeroP = numeroP;
    }
    public void setRobot(int numeroR) {
        this.numeroR = numeroR;
    }
    public void setMazzo(Mazzo mazzo) {
        this.mazzo = mazzo;
    }
    public Mazzo getMazzo() {
        return mazzo;
    }

    //Metodo che crea un vettore con tutti i personaggi possibili
    private void aggiugniPersonaggi() {
        arrayPersonaggi[0] = new Personaggio1("Zodiac", 4, "Può pescare la prima carta dalla mano di un giocatore.");
        arrayPersonaggi[1] = new Personaggio2("Ted Bundy", 4, "Può scartare due carte per recuperare un punto vita.");
        arrayPersonaggi[2] = new Personaggio3("Killer Clown", 4, "Ogni volta che viene ferito pesca una carta.");
        arrayPersonaggi[3] = new Personaggio4("Aileen Wuornos", 4, "Può giocare le carte BANG come carte Mancato, e viceversa.");
        arrayPersonaggi[4] = new Personaggio5("Ed Gein", 3, "Può giocare un numero qualsiasi di carte BANG.");
        arrayPersonaggi[5] = new Personaggio6("Jeffrey Dahmer", 4, "Può pescare la prima carta dalla cima degli scarti.");
        arrayPersonaggi[6] = new Personaggio7("The River Man", 3, "Ogni volta che viene ferito da un giocatore, pesca una carta dalla mano di quel giocatore.");
        arrayPersonaggi[7] = new Personaggio8("BTK Killer", 3, "Per evitare i suoi BANG occorrono due carte Mancato.");
    }
    //crea un array di tipo Set con tot valori unici e casuali
    private Set<Integer> setPersonaggi() {
        int min = 0;
        int max = 7;
        int numeriDaStampare = numeroGG;
        return generaNumeriCasualiUnici(min, max, numeriDaStampare);
    }
    //genra un insieme di numeri tutti diversi e non ordinati
    private Set<Integer> generaNumeriCasualiUnici(int min, int max, int count) {
        try {
            if (count > (max - min + 1) || count < 0) {
                throw new IllegalArgumentException("ERRORE! Range non valido.");
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        Set<Integer> numeriCasualiUnici = new LinkedHashSet<>();
        Random rand = new Random();
        while (numeriCasualiUnici.size() < count) {
            int numeroCasuale = rand.nextInt((max - min) + 1) + min;
            numeriCasualiUnici.add(numeroCasuale);
        }
        return numeriCasualiUnici;
    }
    //Da un array di tipo Set estrae i valori e li trasforma in interi i quali vengono poi usati per attribuire il
    //personaggio corrispondente
    private Personaggi[] arrayPersonaggi() {
        Personaggi[] vettP = new Personaggi[numeroGG];
        Integer[] valori = setPersonaggi().toArray(new Integer[0]); //non so se funziona
        for(int i = 0; i < vettP.length; i++) {
            vettP[i] = arrayPersonaggi[valori[i]];
        }
        return vettP;
    }
    //Stabilisce il numero di ogni ruolo in base al numero di giocatori
    private void setRuoliPartita() {
        for(int i=0; i<4; i++) {
            int val = matrix[numeroGG-3][i];
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

    //permette di generare un numero casuale compreso tra il numero di ruoli rimasti e 0
    private int metodino() {
        int min = 0;
        int max = ruoliPartita.size()-1;
        return (int) (Math.random()*(max-min+1))+min;
    }
    //Assegna ruoli e personaggi seguendo i metodi precedenti
    public void getGGRandom() {
        Personaggi[] pPartita = arrayPersonaggi();
        int indexR = 0;
        int indexP = 0;
        setRuoliPartita();
        while(indexR < numeroR) {
            int val = metodino();
            Giocatore g = new GiocatoreRobot(ruoliPartita.get(val), pPartita[indexP]);
            ruoliPartita.remove(val);
            giocatoriPartita.add(g);
            indexR++;
            indexP++;

        }
        indexR=0;
        while(indexR < numeroP ) {
            int val = metodino();
            Giocatore g = new GiocatorePersona(ruoliPartita.get(val), pPartita[indexP]);
            ruoliPartita.remove(val);
            giocatoriPartita.add(g);
            indexR++;
            indexP++;
        }
        System.out.println(giocatoriPartita.toString());
    }

    public int getNumero() {
        return numeroGG;
    }

    public ArrayList<Giocatore> getGiocatoriPartita() {
        return giocatoriPartita;
    }
    /*
    public Ruoli getRuolo(int i) {
        return ruolo[i];
    }
    */

    /*public String getPianeta(int i) {
        return pianeta[i];
    }
    */
    /*
    public void setRuolo(int i, Ruoli r) {
        if (ruolo==null)
            ruolo = new Ruoli[numeroGG];
        ruolo[i]=r;
    }
    */
    /*
    public void setPianeta(int i, String p) {
        if (pianeta==null)
            pianeta = new String[numeroGG];
        pianeta[i]=p;
    }
    */
    /*
    public void setBot(int i, Boolean b) {
        if (bot==null)
            bot = new Boolean[numeroGG];
        bot[i]=b;
    }
    */
    /*
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
    */
}
