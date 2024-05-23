package com.example.gioco;

import javafx.scene.control.Alert;
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
            myObj.createNewFile();
        } catch (IOException e) {
            mostraErrore();
            System.err.println("Si è verificato un errore.");
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
        File cartella = new File(percorsoCartellaProgetto);
        File[] files = cartella.listFiles();
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

    private void mostraErrore() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRORE");
        alert.setHeaderText("Impossibile generare il contenuto");
        alert.setContentText("Si è verificato un errore durante la creazione del file.\nRiprova, il file non è stato creato");
        alert.showAndWait();
    }
}