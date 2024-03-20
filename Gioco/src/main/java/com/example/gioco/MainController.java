package com.example.gioco;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
public class MainController {
    @FXML
    private OvalPaneController ovalPaneController;
    private double centroX;
    private double centroY;
    private Stage primaryStage;
    private GameData gameData = GameData.getInstance();
    @FXML
    private Button turnButton;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView mazzo;
    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            anchorPane.widthProperty().addListener((observable, oldValue, newValue) -> {
                turnButton.setLayoutX((newValue.doubleValue() / 2) - (turnButton.getWidth() / 2));
            });
            System.out.println(turnButton.getHeight());
            anchorPane.heightProperty().addListener((observable, oldValue, newValue) -> {
                turnButton.setLayoutY((newValue.doubleValue() / 2) - (turnButton.getHeight() / 2));
            });
        });
        impostaCose();
        /*
        Image sfondo = new Image(getClass().getResource("SfondoGioco.png").toString());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(sfondo, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        borderPane.setBackground(new Background(backgroundImage));*/
    }
    /*
    public static void setDim(double x, double y){
        centroX=x/2;
        anchorPane.getChildren().get(1).setTranslateX(0);
    }
    public static void setY(double y){
        centroY=y/2;
        anchorPane.getChildren().get(1).setTranslateY(y-30);
    }*/
    public void impostaCose(){
    }
    @FXML
    public void handleTurnButton() {
        turnButton.setDisable(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> turnButton.setDisable(false));
        ovalPaneController.cambiaTurno();
        pause.play();
    }
    public void pescataInizialeGiocatore() {
        Giocatore player = gameData.getGiocatoriPartita().get(gameData.getTurnoCorrente());
        for(int i = 1; i <= 2; i++) {
            player.addCarta(gameData.getMazzo().pesca());
            System.out.println("Ho pescato");
        }
    }
}