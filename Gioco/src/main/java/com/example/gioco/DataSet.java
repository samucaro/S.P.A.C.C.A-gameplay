package com.example.gioco;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataSet {
    public DataSet() {
    }

    //Da la directory di lavoro corrente dell'utente che lancia il programma
    public String getProjectFolderPath() {
        String currentDirectory = System.getProperty("user.dir");
        String projectFolderPath = currentDirectory + File.separator + "src/main/resources/FileGioco";
        File folder = new File(projectFolderPath);
        if (!folder.exists()) {
            System.err.println("La cartella non esiste!");
        }
        return projectFolderPath;
    }

    public void creaFile(int codice) {
        String percorsoCartellaProgetto = getProjectFolderPath();
        try {
            String nome = percorsoCartellaProgetto + File.separator + codice + ".txt";
            File myObj = new File(nome);
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

    public void eliminaFile(String percorsoFile) {
        File file = new File(percorsoFile);
        if (file.delete()) {
            System.out.println("Il file è stato eliminato con successo.");
        } else {
            System.out.println("File non presente");
        }
    }

    public boolean checkCode(int codice) {
        String percorsoCartellaProgetto = getProjectFolderPath();
        List<Integer> numbers = new ArrayList<>();
        File cart = new File(percorsoCartellaProgetto);
        File[] files = cart.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().matches("\\d{4}\\.txt")) {
                    String fileName = file.getName();
                    int number = Integer.parseInt(fileName.substring(0, 4));
                    numbers.add(number);
                }
            }
        }
        return numbers.contains(codice);
    }
}