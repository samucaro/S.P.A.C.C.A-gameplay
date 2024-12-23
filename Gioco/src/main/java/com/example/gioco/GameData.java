package com.example.gioco;

import java.io.*;
import java.util.*;

public class GameData {
    private int turnoCorrente;
    private int numPartita;
    private String tipo;
    private static GameData instance = null;
    private final ArrayList<Giocatore> giocatoriPartita;
    private final DataSet dataSet;
    private Mazzo mazzo;
    private int numeroGiocatori;
    private int code;

    public GameData() throws IOException {
        dataSet = new DataSet();
        giocatoriPartita = new ArrayList<>();
        mazzo = new Mazzo();
    }

    public static void resetInstance() {
        instance=null;
    }

    public static GameData getInstance() {
            try {
                if (instance == null) {
                    instance = new GameData();
                }
            }
            catch(IOException e) {
                System.err.println("ERR0RE");
            }
            return instance;
    }

    //Legge il file e capisce se è un torneo o una partita normale
    public void leggiFile(int code) {
        try {
            this.code = code;
            BufferedReader reader = new BufferedReader(new FileReader(dataSet.getProjectFolderPath() + File.separator + "/" + code + ".txt"));
            if(code <= 999) {
                String line;
                numPartita = 0;
                while((line = reader.readLine()) != null) {
                    if(line.startsWith("Partita Corrente")) {
                        numPartita = Integer.parseInt(line.split(" ")[2]);
                    }
                    else if (line.startsWith("Numero Partita") && numPartita == Integer.parseInt(line.split(" ")[2])) {
                        leggiFilePartita(reader, numPartita, true);
                        break;
                    }
                }

            }
            else {
                leggiFilePartita(reader, -1, false);
            }
        }
        catch (IOException e) {
            System.err.println("ERR0RE");
            throw new RuntimeException();
        }
    }

    //Legge il reader in ingresso e se è un torneo legge solo una partita altrimenti arriva in fondo al file
    private void leggiFilePartita(BufferedReader reader, int numPartita, boolean check) {
        try {
            int c = 0;
            String line;
            while((line = reader.readLine()) != null) {
                if(numPartita == -1 || check) {
                    if(line.startsWith("Dati Generali")) {
                        tipo = line.split(" ")[2];
                    }
                    else if(line.startsWith("NumGiocatori:")) {
                        numeroGiocatori = Integer.parseInt(line.split(":")[1].trim());
                    }
                    else if(line.startsWith("Turno:")) {
                        turnoCorrente = Integer.parseInt(line.split(":")[1].trim());
                    }
                    else if(line.startsWith("Mazzo:")) {
                        String[] carteMazzo = line.split(": ");
                        String cma = carteMazzo[carteMazzo.length - 1];
                        String[] carteM = cma.trim().split(" ");
                        for(String nomeCarta : carteM) {
                            if(stringaCarta(nomeCarta) != null) {
                                mazzo.setMazzo(stringaCarta(nomeCarta));
                            }
                        }
                    }
                    else if(line.startsWith("Scarti:")) {
                        String[] carteScarti = line.split(":");
                        String cs = carteScarti[carteScarti.length - 1];
                        String[] carteS = cs.trim().split(" ");
                        for(String nomeCarta : carteS) {
                            if(stringaCarta(nomeCarta) != null) {
                                mazzo.setScarti(stringaCarta(nomeCarta));
                            }
                        }
                    }
                    else if(line.startsWith("Giocatore:")) {
                        c = (Integer.parseInt(line.split(" ")[1].trim()));
                    }
                    else if(line.startsWith("Tipo:")) {
                        if(line.split(":")[1].trim().equals("Persona")) {
                            giocatoriPartita.add(new GiocatorePersona());
                        }
                        else {
                            giocatoriPartita.add(new GiocatoreRobot());
                        }
                    }
                    else if(line.startsWith("Nome:")) {
                        giocatoriPartita.get(c).setNome(line.split(":")[1].trim());
                    }
                    else if(line.startsWith("Mano:")) {
                        String[] carteMano = line.split(":");
                        String cm = carteMano[carteMano.length - 1];
                        String[] carteMa = cm.trim().split(" ");
                        for(String s : carteMa) {
                            if(stringaCarta(s) != null) {
                                giocatoriPartita.get(c).setMano(stringaCarta(s));
                            }
                        }
                    }
                    else if(line.startsWith("HpRimanente:")) {
                        giocatoriPartita.get(c).setHpRimanente(Integer.parseInt(line.split(":")[1].trim()));
                    }
                    else if(line.startsWith("******************************")) {
                        check = false;
                    }
                }
            }
            reader.close();
        }
        catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        }
    }

    //Si comporta come il salvataggio, soltanto lo aggiorna con i nuovi dati della partita/torneo corrente
    public void aggiornaFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataSet.getProjectFolderPath() + File.separator + "/" + code + ".txt"));
            StringBuilder cont = new StringBuilder();
            String line;
            if(code <= 999) {
                while((line = reader.readLine()) != null) {
                    if(line.startsWith("Numero Partita") && numPartita == Integer.parseInt(line.split(":")[1].trim())) {
                        cont.append("Numero Partita: ").append(numPartita).append("\n").append(scriviFilePartita());
                        do {
                            line = reader.readLine();
                        } while (!line.startsWith("******************************"));
                    }
                    else {
                        cont.append(line).append("\n");
                    }
                }
            }
            else {
                cont.append(scriviFilePartita());
            }
            reader.close();
            FileWriter file = new FileWriter((dataSet.getProjectFolderPath() + File.separator + "/" + code + ".txt"), false);
            PrintWriter writer = new PrintWriter(file);
            writer.write(cont.toString());
            writer.close();
        }
        catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public void aggiornaPartitaCorrente(String nomeVincitore) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataSet.getProjectFolderPath() + File.separator + "/" + code + ".txt"));
            StringBuilder cont = new StringBuilder();
            String line;
            if (numPartita < 14) {
                boolean checkPari = numPartita % 2 == 0;
                int numVero = checkPari ? numPartita : numPartita - 1;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Partita Corrente")) {
                        cont.append("Partita Corrente: ").append(numPartita + 1).append("\n");
                    } else if (line.startsWith("Numero Partita")) {
                        cont.append(line).append("\n");
                        if (Integer.parseInt(line.split(":")[1].trim()) == (((numVero) / 2) + 8)) {
                            if (checkPari) {
                                aggiornaNomeGiocatore(reader, cont, nomeVincitore);
                            } else {
                                do {
                                    line = reader.readLine();
                                    cont.append(line).append("\n");
                                } while (!line.startsWith("Giocatore: 1"));
                                aggiornaNomeGiocatore(reader, cont, nomeVincitore);
                            }
                        }
                    } else {
                        cont.append(line).append("\n");
                    }
                }
                FileWriter file = new FileWriter((dataSet.getProjectFolderPath() + File.separator + "/" + code + ".txt"), false);
                PrintWriter writer = new PrintWriter(file);
                writer.write(cont.toString());
                writer.close();
            } else {
                aggiornaVincitoreTorneo(reader, cont, nomeVincitore);
                reader.close();
            }
        }
        catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    private void aggiornaNomeGiocatore(BufferedReader reader, StringBuilder cont, String nomeVincitore) throws IOException {
        String line;
        while((line = reader.readLine()) != null) {
            if(line.startsWith("Tipo")) {
                if(nomeVincitore.startsWith("Bot")) {
                    cont.append("Tipo: ").append("Bot").append("\n");
                }
                else {
                    cont.append("Tipo: ").append("Persona").append("\n");
                }
            }
            else if(line.startsWith("Nome")) {
                cont.append("Nome: ").append(nomeVincitore).append("\n");
                break;
            }
            else {
                cont.append(line).append("\n");
            }
        }
    }

    private void aggiornaVincitoreTorneo(BufferedReader reader, StringBuilder cont, String nomeVincitore) throws IOException {
        String line;
        while((line = reader.readLine()) != null) {
            if(line.startsWith("Vincitore")) {
                cont.append("Vincitore:").append(nomeVincitore).append("\n");
            }
            else {
                cont.append(line).append("\n");
            }
        }
        FileWriter file = new FileWriter((dataSet.getProjectFolderPath() + File.separator + "/" + code + ".txt"), false);
        PrintWriter writer = new PrintWriter(file);
        writer.write(cont.toString());
        writer.close();
    }

    private String scriviFilePartita(){
        StringBuilder builder = new StringBuilder();
        builder.append("Dati Generali ").append(tipo).append("\n");
        builder.append("NumGiocatori: ").append(numeroGiocatori).append("\n");
        builder.append("Turno: ").append(turnoCorrente).append("\n");
        builder.append("Mazzo: ").append(mazzo.toString()).append("\n");
        builder.append("Scarti: ").append(mazzo.toStringScarti()).append("\n");
        for(int i = 0; i < numeroGiocatori; i++) {
            builder.append("Giocatore: ").append(i).append("\n");
            builder.append("Tipo: ").append((giocatoriPartita.get(i) instanceof GiocatorePersona) ? "Persona" : "Bot").append("\n");
            builder.append("Nome: ").append(giocatoriPartita.get(i).getNome()).append("\n");
            builder.append("Mano: ").append(giocatoriPartita.get(i).toStringMano()).append("\n");
            builder.append("HpRimanente: ").append(giocatoriPartita.get(i).getHpRimanente()).append("\n");
        }
        builder.append("******************************\n");
        return builder.toString();
    }

    private Carta stringaCarta(String c) {
        return switch (c) {
            case "Bang" -> new CartaBang();
            case "Mancato" -> new CartaMancato();
            case "Duello" -> new CartaDuello();
            case "PerdiCarta" -> new CartaPerdiCarta();
            case "ScartaBang" -> new CartaScartaBang();
            case "SparaTutti" -> new CartaSparaTutti();
            case "RecuperaVita" -> new CartaRecuperaVita();
            default -> null;
        };
    }

    public void setMazzo(Mazzo mazzo) {
        this.mazzo = mazzo;
    }
    public Mazzo getMazzo() {
        return mazzo;
    }
    public void setTurnoCorrente(int turnoCorrente) {
        this.turnoCorrente = turnoCorrente;
    }
    public int getTurnoCorrente() {
        return turnoCorrente;
    }
    public int getNumero() {
        return numeroGiocatori;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public int getCode() {
        return code;
    }
    public ArrayList<Giocatore> getGiocatoriPartita() {
        return giocatoriPartita;
    }
    public int getNumPartitaCorrente() {
        return numPartita;
    }
}
