package com.example.gioco;

import java.io.*;
import java.util.*;

public class GameData {
    private int turnoCorrente;
    private int numeroPartita;
    private Stato stato;
    private String tipo;
    private static GameData instance = null;
    private LinkedList<GameData> partiteTorneo;
    private final ArrayList<Giocatore> giocatoriPartita;
    private final DataSet dataSet;
    private Mazzo mazzo;
    private int numeroGG;
    private int code;

    public GameData() throws IOException {
        dataSet = new DataSet();
        giocatoriPartita = new ArrayList<>();
        mazzo = new Mazzo();
    }
    public GameData(int n, Stato stato, int turnoCorrente, Mazzo mazzo, ArrayList<Giocatore>giocatoriPartita) {
        dataSet = new DataSet();
        partiteTorneo = new LinkedList<>();
        numeroPartita = n;
        this.stato = stato;
        this.turnoCorrente = turnoCorrente;
        this.mazzo = mazzo;
        this.giocatoriPartita = giocatoriPartita;
    }
    public static void resetInstance() {
        instance=null;
    }
    public static GameData getInstance() {
        try {
            if (instance == null) {
                instance = new GameData();
            }
        } catch(IOException e) {
            System.out.println("ERR0RE");
            e.printStackTrace();
        }
        return instance;
    }
    public void leggiFilePartita(int code) {
        this.code = code;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataSet.getProjectFolderPath() + File.separator + "/" + code + ".txt"));
            int c = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.startsWith("Dati Generali")) {
                    tipo = line.split(" ")[2];
                }
                else if (line.startsWith("NumGiocatori:")) {
                    numeroGG = Integer.parseInt(line.split(":")[1].trim());
                }
                else if (line.startsWith("Turno:")) {
                    turnoCorrente = Integer.parseInt(line.split(":")[1].trim());
                }
                else if (line.startsWith("Mazzo:")) {
                    String carteMazzo = line.split(": ")[1];
                    String[] carteM = carteMazzo.split(" ");
                    for (String nomeCarta: carteM) {
                        mazzo.addCarta(stringaCarta(nomeCarta));
                    }
                }
                else if (line.startsWith("Scarti:")) {
                    String carteScarti = line.split(":")[1];
                    String[] carteS = carteScarti.split(" ");
                    for (String nomeCarta: carteS) {
                        mazzo.addScarto(stringaCarta(nomeCarta));
                    }
                }
                else if (line.startsWith("Giocatore:")) {
                    c = (Integer.parseInt(line.split(" ")[1].trim()))-1;
                }
                else if (line.startsWith("Tipo:")) {
                    if (line.split(":")[1].trim().equals("Persona")) {
                        giocatoriPartita.add(new GiocatorePersona());
                    } else {
                        giocatoriPartita.add(new GiocatoreRobot());
                    }
                }
                else if (line.startsWith("Nome:")) {
                    giocatoriPartita.get(c).setNome(line.split(":")[1].trim());
                }
                else if (line.startsWith("Mano:")) {
                    String carteMano = line.split(": ")[1];
                    String[] carteMa = carteMano.split(" ");
                    for (String s : carteMa) {
                        giocatoriPartita.get(c).addCarta(stringaCarta(s));
                    }
                }
                else if (line.startsWith("HpRimanente:")) {
                    giocatoriPartita.get(c).setHpRimanente(Integer.parseInt(line.split(":")[1].trim()));
                }
            }
            System.out.println(giocatoriPartita);
            reader.close();
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        }
    }

    public void leggiFileTorneo(int code) {
        this.code = code;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataSet.getProjectFolderPath() + File.separator + "/" + code + ".txt"));
            int c = 0;
            String line;
            numeroGG = 16;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Partita Corrente:")) {
                    turnoCorrente = Integer.parseInt(line.split(": ")[1]);
                }
                else if (line.startsWith("Numero Partita:")) {
                    int n = Integer.parseInt(line.split(": ")[1]);
                    while(!(line = reader.readLine()).equals("******************************")) {
                        if (line.startsWith("Stato:")) {
                            String str = line.split(": ")[1];
                            switch (str) {
                                case "Completata":
                                    stato = Stato.COMPLETATA;
                                    break;
                                case "Pronta":
                                        stato = Stato.PRONTA;
                                        break;
                                case "Attesa":
                                    stato = Stato.ATTESA;
                                    break;
                                case "Sospesa":
                                    stato = Stato.SOSPESA;
                                    break;
                                default:
                                    break;
                            }
                        }
                        else if(line.startsWith("Mazzo:")) {
                            String carteMazzo = line.split(": ")[1];
                            String[] carteM = carteMazzo.split(" ");
                            for (String nomeCarta: carteM) {
                                mazzo.addCarta(stringaCarta(nomeCarta));
                            }
                        }
                        else if (line.startsWith("Scarti:")) {
                            String carteScarti = line.split(":")[1];
                            String[] carteS = carteScarti.split(" ");
                            for (String nomeCarta: carteS) {
                                mazzo.addScarto(stringaCarta(nomeCarta));
                            }
                        }
                        else if (line.startsWith("Turno:")) {
                            turnoCorrente = Integer.parseInt(line.split(":")[1].trim());
                        }
                        else if (line.startsWith("Giocatore:")) {
                            c = Integer.parseInt(line.split(": ")[1]);
                        }
                        else if (line.startsWith("Tipo:")) {
                            if (line.split(": ")[1].equals("Persona")) {
                                giocatoriPartita.add(new GiocatorePersona());
                            } else {
                                giocatoriPartita.add(new GiocatoreRobot());
                            }
                        }
                        else if (line.startsWith("Nome:")) {
                            giocatoriPartita.get(c).setNome(line.split(": ")[1]);
                        }
                        else if (line.startsWith("Mano:")) {
                            String carteMano = line.split(": ")[1];
                            String[] carteMa = carteMano.split(" ");
                            for (String s : carteMa) {
                                giocatoriPartita.get(c).addCarta(stringaCarta(s));
                            }
                        }
                        else if (line.startsWith("HpRimanente:")) {
                            giocatoriPartita.get(c).setHpRimanente(Integer.parseInt(line.split(":")[1].trim()));
                        }
                    }
                    partiteTorneo.add(new GameData(n, stato, turnoCorrente, mazzo, giocatoriPartita));
                }
            }
            System.out.println(partiteTorneo);
            reader.close();
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        }
    }
    public void aggiornaFile(){
        try {
            FileWriter file = new FileWriter((dataSet.getProjectFolderPath() + File.separator + "/" + code + ".txt"), true);
            PrintWriter writer = new PrintWriter(file);
            writer.println("Dati Generali " + tipo + ":");
            writer.println("NumGiocatori: " + numeroGG);
            writer.println("Turno: " + turnoCorrente);
            writer.println("Mazzo: " + mazzo.toString());
            writer.println("Scarti: " + mazzo.toStringScarti());
            writer.println("******************************");
            for (int i = 0; i < numeroGG; i++) {
                writer.println("Giocatore " + (i + 1) + ":");
                writer.println("Tipo: " + (giocatoriPartita.get(i).getClass().getSimpleName().equals("giocatorePersona")?"Persona":"Bot"));
                writer.println("Nome: " + giocatoriPartita.get(i).getNome());
                writer.println("Mano: " + giocatoriPartita.get(i).toStringMano());
                writer.println("HpRimanente: " + giocatoriPartita.get(i).getHpRimanente());
                writer.println("******************************");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Errore durante la creazione del file: " + e.getMessage());
        }
    }
    public Carta stringaCarta(String c) {
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
    //MODIFICA
    public void setNumero(int numeroGG) {
        this.numeroGG = numeroGG;
    }
    //MODIFICA
    public void setMazzo(Mazzo mazzo) {
        this.mazzo = mazzo;
    }
    //MODIFICA
    public void setCode(int code) {
        this.code = code;
    }
    //MODIFICA
    public Mazzo getMazzo() {
        return mazzo;
    }
    //MODIFICA
    public void setTurnoCorrente(int turnoCorrente) {
        this.turnoCorrente = turnoCorrente;
    }
    //MODIFICA
    public int getTurnoCorrente() {
        return turnoCorrente;
    }
    //MODIFICA
    public int getCode() {
        return code;
    }
    //MODIFICA
    public int getNumero() {
        return numeroGG;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getTipo() {
        return tipo;
    }
    //MODIFICA
    public ArrayList<Giocatore> getGiocatoriPartita() {
        return giocatoriPartita;
    }
}
