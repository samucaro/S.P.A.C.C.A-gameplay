package com.example.gioco;

import java.io.File;
import java.io.IOException;

public class DataSet {
    private File myObj;
    public DataSet() {
    }

    String percorso = "C:/Users/samua/Documents/Universita/Primo anno/Programmazione/Progetti/ProgettoFinale/Gioco/src/main/resources/FileGioco";
    public void creaFile(int codice) {
        try {
            String nome = percorso + File.separator + codice + ".txt";
            myObj = new File(nome);
            if (myObj.createNewFile()) {
                System.out.println("File creato: " + myObj.getName());
            } else {
                System.out.println("Il file esiste già.");
            }
        } catch (IOException e) {
            System.out.println("Si è verificato un errore.");
            e.printStackTrace();
        }
    }

}