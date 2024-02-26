package com.example.gioco;

import java.io.File;
import java.io.IOException;

public class DataSet {
    private File myObj;
    public DataSet() {
    }


    public void creaFile(int codice) {
        String percorsoCartellaProgetto = getProjectFolderPath();
        System.out.println("Percorso della cartella del progetto: " + percorsoCartellaProgetto);
        try {
            String nome = percorsoCartellaProgetto + File.separator + codice + ".txt";
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

    public static String getProjectFolderPath() {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        String projectFolderPath = currentDirectory + File.separator + "src/main/resources/FileGioco";
        System.out.println(projectFolderPath);
        File folder = new File(projectFolderPath);
        if (!folder.exists()) {
            System.err.println("La cartella non esiste!");
        }
        return projectFolderPath;
    }

}