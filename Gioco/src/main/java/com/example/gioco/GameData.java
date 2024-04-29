package com.example.gioco;

import java.io.*;
import java.util.*;

public class GameData {
    private int turnoCorrente;
    private Stato stato;
    private String tipo;
    private static GameData instance = null;
    private LinkedList<GameData> partiteTorneo;
    private final ArrayList<Giocatore> giocatoriPartita;
    private final DataSet dataSet;
    private Mazzo mazzo;
    private int numeroGiocatori;
    private int code;
    private String vincitore;

    public GameData() throws IOException {
        dataSet = new DataSet();
        giocatoriPartita = new ArrayList<>();
        mazzo = new Mazzo();
    }
    public GameData(int n, Stato stato, int turnoCorrente, Mazzo mazzo, ArrayList<Giocatore>giocatoriPartita) {
        dataSet = new DataSet();
        partiteTorneo = new LinkedList<>();
        /*numeroPartita = n;
        this.stato = stato;
        this.turnoCorrente = turnoCorrente;*/
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

    //Fare un metodo

    //Legge il File selezionato tramite il corrispettivo codice
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
                    numeroGiocatori = Integer.parseInt(line.split(":")[1].trim());
                }
                else if (line.startsWith("Turno:")) {
                    turnoCorrente = Integer.parseInt(line.split(":")[1].trim());
                }
                else if (line.startsWith("Mazzo:")) {
                    String[] carteMazzo = line.split(": ");
                    String cma = carteMazzo[carteMazzo.length-1];
                    String[] carteM = cma.trim().split(" ");
                    for (String nomeCarta: carteM) {
                        if (stringaCarta(nomeCarta) != null)
                            mazzo.setMazzo(stringaCarta(nomeCarta));
                    }
                }
                else if (line.startsWith("Scarti:")) {
                    String[] carteScarti = line.split(":");
                    String cs = carteScarti[carteScarti.length-1];
                    String[] carteS = cs.trim().split(" ");
                    for (String nomeCarta: carteS) {
                        if (stringaCarta(nomeCarta) != null)
                            mazzo.setScarti(stringaCarta(nomeCarta));
                    }
                }
                else if (line.startsWith("Giocatore:")) {
                    c = (Integer.parseInt(line.split(" ")[1].trim()));
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
                    String[] carteMano = line.split(":");
                    String cm = carteMano[carteMano.length-1];
                    String[] carteMa = cm.trim().split(" ");
                    for (String s : carteMa) {
                        if (stringaCarta(s) != null)
                            giocatoriPartita.get(c).setMano(stringaCarta(s));
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
            numeroGiocatori = 16;
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
                                mazzo.setMazzo(stringaCarta(nomeCarta));
                            }
                        }
                        else if (line.startsWith("Scarti:")) {
                            String carteScarti = line.split(":")[1];
                            String[] carteS = carteScarti.split(" ");
                            for (String nomeCarta: carteS) {
                                mazzo.setScarti(stringaCarta(nomeCarta));
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
                                giocatoriPartita.get(c).setMano(stringaCarta(s));
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
            FileWriter file = new FileWriter((dataSet.getProjectFolderPath() + File.separator + "/" + code + ".txt"), false);
            PrintWriter writer = new PrintWriter(file);
            writer.println("Dati Generali " + tipo);
            writer.println("NumGiocatori: " + numeroGiocatori);
            writer.println("Turno: " + turnoCorrente);
            writer.println("Mazzo: " + mazzo.toString());
            writer.println("Scarti: " + mazzo.toStringScarti());
            writer.println("******************************");
            for (int i = 0; i < numeroGiocatori; i++) {
                writer.println("Giocatore: " + i);
                writer.println("Tipo: " + ((giocatoriPartita.get(i) instanceof GiocatorePersona) ? "Persona" : "Bot"));
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
    public void setMazzo(Mazzo mazzo) {
        this.mazzo = mazzo;
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
    public int getNumero() {
        return numeroGiocatori;
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
