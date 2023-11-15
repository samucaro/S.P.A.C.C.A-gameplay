package com.example.gioco;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AccessPlayerPageController {
    private GameData gameData = GameData.getInstance();
    ObservableList<String> pianetiItems = FXCollections.observableArrayList("Mercurio", "Venere", "Terra", "Marte", "Giove", "Saturno", "Urano", "Nettuno");
    ObservableList<String> ruoliItems = FXCollections.observableArrayList("Sceriffo", "Rinnegato", "Fuorilegge", "Vice");
    int[] ruoliQ = new int[4];
    int[][] matrix = {
            {1,1,1,1}, //sceriffi, rinnegato, fuorilegge, vice
            {1,1,2,0},
            {1,1,2,1},
            {1,1,3,1},
            {1,1,3,2},
            {1,1,4,2}
    };

    private Parent root;
    private Scene scene;
    private ArrayList<Integer> troppi;
    private int numerogg=0;
    @FXML
    private Button addButton;
    @FXML
    private Label errorMessage;
    @FXML
    private BorderPane borderP;
    private ArrayList<HBox> giocatoriSelezionati = new ArrayList<>();
    @FXML
    private VBox Vboxx;
    @FXML
    private ChoiceBox<String> choicePianeti;
    @FXML
    public void initialize() {
        Image sfondo = new Image(getClass().getResource("_33e14802-fae8-45d6-80aa-5b89524679cb.jpg").toString());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(sfondo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        borderP.setBackground(new Background(backgroundImage));
        if (index<=gameData.getNumero())
            addPlayer();
        /*for(int i=1; i<=gameData.getNumero(); i++) {
            addPlayer(i);
        }*/
    }
    public void addPlayer() {
        try{
            index++;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SelezioneNuovoGiocatore.fxml"));
            loader.setController(this);
            HBox g1 = loader.load();
            ((Label) g1.getChildren().get(0)).setText("G" + index);
            ((ChoiceBox<String>) g1.getChildren().get(2)).setItems(pianetiItems);
            ((ChoiceBox<String>) g1.getChildren().get(3)).setItems(ruoliItems);
            ((ChoiceBox<String>) g1.getChildren().get(2)).getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    sistemaPianetiSelezione(oldValue, newValue, g1);
                }
            });
            ((ChoiceBox<String>) g1.getChildren().get(4)).getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    ruoliSelezione(oldValue, newValue, g1, gameData.getNumero());
                }
            });

            giocatoriSelezionati.add(g1);
            Vboxx.getChildren().add(Vboxx.getChildren().size()-1,giocatoriSelezionati.get(giocatoriSelezionati.size()-1));
            if (Vboxx.getChildren().size()==9)
                addButton.setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void removePlayer(HBox g1){
        numerogg--;
        giocatoriSelezionati.remove(g1);
        Vboxx.getChildren().remove(g1);
    }
    private void sistemaPianetiSelezione(String oldValue, String newValue, HBox g1) {
        int numero = Integer.parseInt(((Label) g1.getChildren().get(0)).getText().substring(1));
        pianetiItems.remove(newValue);
        if (oldValue!=null)
            pianetiItems.add(oldValue);
        for (int i = 0; i<giocatoriSelezionati.size(); i++){
            if (i+1!=numero) {
                ((ChoiceBox<String>) giocatoriSelezionati.get(i).getChildren().get(3)).setItems(pianetiItems);
            }
        }
    }
    private void ruoliSelezione(String oldValue, String newValue, HBox g1, int numGiocatori) {
        int numero = Integer.parseInt(((Label) g1.getChildren().get(0)).getText().substring(1));
        switch (newValue){
            case "Sceriffo" -> {
                System.out.println("bbbbbbbbbbbbbbbbbbbbb");
                ruoliQ[0]++;
                if (ruoliQ[0]==1)
                    ruoliItems.remove(newValue);
            }
            case "Rinnegato" -> {
                ruoliQ[1]++;
                if (ruoliQ[1]==1)
                    ruoliItems.remove(newValue);
            }
            case "Fuorilegge" -> {
                ruoliQ[2]++;
                if (ruoliQ[2]==3)
                    ruoliItems.remove(newValue);
            }
            case "Vice" -> {
                ruoliQ[3]++;
                if (ruoliQ[3]==3)
                    ruoliItems.remove(newValue);
            }
        }
        if (oldValue!=null)
            switch (oldValue){
                case "Sceriffo" -> {
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
                    ruoliQ[0]--;
                    if (ruoliQ[0]==0)
                        ruoliItems.add(oldValue);
                }
                case "Rinnegato" -> {
                    ruoliQ[1]--;
                    if (ruoliQ[1]==0)
                        ruoliItems.add(oldValue);
                }
                case "Fuorilegge" -> {
                    ruoliQ[2]--;
                    if (ruoliQ[2]==2)
                        ruoliItems.add(oldValue);
                }
                case "Vice" -> {
                    ruoliQ[3]--;
                    if (ruoliQ[3]==2)
                        ruoliItems.add(oldValue);
                }
            }
        System.out.println(ruoliQ[0]);
        for (int i = 0; i<giocatoriSelezionati.size(); i++){
            if (i+1!=numero) {
                ((ChoiceBox<String>) giocatoriSelezionati.get(i).getChildren().get(4)).setItems(ruoliItems);
            }
        }
    }
    public void setBot(){
        for (int i = 0; i<giocatoriSelezionati.size(); i++) {
            gameData.setBot(i,((CheckBox) giocatoriSelezionati.get(i).getChildren().get(2)).isSelected());
        }
    }
    public void setPianeti(){
        for (int i = 0; i<giocatoriSelezionati.size(); i++) {
            gameData.setPianeta(i,((ChoiceBox<String>) giocatoriSelezionati.get(i).getChildren().get(3)).getValue());
        }
    }
    public void setRuoli(){
        for (int i = 0; i<giocatoriSelezionati.size(); i++) {
            switch (((ChoiceBox<String>) giocatoriSelezionati.get(i).getChildren().get(4)).getValue()){
                case "Sceriffo" -> gameData.setRuolo(i,Ruoli.SCERIFFO);
                case "Fuorilegge" -> gameData.setRuolo(i,Ruoli.FUORILEGGE);
                case "Rinnegato" -> gameData.setRuolo(i,Ruoli.RINNEGATO);
                case "Vice" -> gameData.setRuolo(i,Ruoli.VICE);
            }
        }
    }

    public void switchOvalPane(ActionEvent event) throws IOException {
        if (ruoliItems.contains("Sceriffo")&&ruoliItems.contains("Fuorilegge")&&ruoliItems.contains("Rinnegato")){
            errorMessage.setText("");
            gameData.setNumero(giocatoriSelezionati.size());
            setBot();
            setPianeti();
            setRuoli();
            root = FXMLLoader.load(getClass().getResource("Partitonza.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setMinWidth(900);
            stage.setMinHeight(600);
            scene = new Scene(root);
            impostaListener(scene);
            ParallelCamera cam = new ParallelCamera();
            cam.setFarClip(2000);
            cam.setNearClip(0.5);
            scene.setCamera(cam);
            stage.setScene(scene);
            stage.show();
        } else {
            errorMessage.setText("Selezionare almeno uno sceriffo, un fuorilegge e un rinnegato.");
        }
    }
    public void impostaListener(Scene scene){
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            OvalPaneController.setScenaX((Double) newVal);
            MainController.setScenaX((Double) newVal);
        });

        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            OvalPaneController.setScenaY((Double) newVal);
            MainController.setScenaY((Double) newVal);
        });
    }
}
