package com.example.gioco;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class OvalPaneController {
    @FXML
    private Pane ovalPane;
    private static GameData gameData = GameData.getInstance();
    private static Group[] spheres;
    private Cylinder ring;
    private boolean isPaused = false;
    private static double centroX;
    private static double centroY;
    private static final int n = gameData.getNumero();
    private double anchorX, anchorY;
    private static int turnoDi = 0;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    @FXML
    public void initialize() {
        spheres = new Group[n];
        for (int i = 0; i < n; i++) {
            spheres[i] = new Group();
            spheres[i].getChildren().add(new Sphere(60));
            PhongMaterial material = new PhongMaterial();
            Image texture = textures(i);
            material.setDiffuseMap(texture);
            ((Sphere) spheres[i].getChildren().getFirst()).setMaterial(material);
            spheres[i].getChildren().getFirst().setRotationAxis(Rotate.Y_AXIS);
            provaAnimazione(i);
            if (i==2){
                ring = new Cylinder(80, 3);
                PhongMaterial materialS = new PhongMaterial();
                materialS.setDiffuseColor(Color.BEIGE);
                ring.setMaterial(materialS);
                ring.setRotationAxis(Rotate.Y_AXIS);
                ring.setRotate(75);
                spheres[i].getChildren().add(ring);
            }
            ovalPane.getChildren().add(spheres[i]);
        }
        addSelectionMouse();
    }

    private void provaAnimazione(int i) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!isPaused) {
                    spheres[i].getChildren().getFirst().rotateProperty().set(spheres[i].getChildren().getFirst().getRotate() + 0.2);
                }
            }
        };
        timer.start();
    }

    public void pauseAnimation() {
        isPaused = true;
    }

    public void resumeAnimation() {
        isPaused = false;
    }

    private Image textures(int i){
        return switch (i) {
            case 7 -> new Image(getClass().getResource("mercury.jpg").toString());
            case 6 -> new Image(getClass().getResource("venus.jpg").toString());
            case 5 -> new Image(getClass().getResource("earth.jpg").toString());
            case 4 -> new Image(getClass().getResource("mars.jpg").toString());
            case 3 -> new Image(getClass().getResource("jupiter.jpg").toString());
            default -> new Image(getClass().getResource("saturn.jpg").toString());
            case 1 -> new Image(getClass().getResource("uranus.jpg").toString());
            case 0 -> new Image(getClass().getResource("neptune.jpg").toString());
        };
    }
    public static void setScenaX(double x){
        centroX=x/2;
        posizionaSfere();
    }
    public static void setScenaY(double y){
        centroY=y/2;
        posizionaSfere();
    }
    private static void posizionaSfere() {
        for (int i = 0; i < n; i++) {
            int indice = ((n - turnoDi) + i) % n;
            //System.out.println("i "+i);
            //System.out.println("indice "+indice);
            //System.out.println("Turnodi "+turnoDi);
            if (indice == 2) {
                ((Cylinder) spheres[indice].getChildren().get(1)).setRadius((60 * Math.min(centroX, centroY)) / 300 + 30);
            }
            ((Sphere) spheres[indice].getChildren().get(0)).setRadius((60 * Math.min(centroX, centroY)) / 300);

            System.out.println((centroX - ((Sphere) spheres[indice].getChildren().get(0)).getRadius() * 2 + 50) * Math.cos((2 * Math.PI * (i + 1) / n)+(Math.PI/2)) + centroX);
            System.out.println((centroY - ((Sphere) spheres[indice].getChildren().getFirst()).getRadius() * 2 + 40) * Math.sin(2 * Math.PI * (i + 1) / n) + centroY);
            spheres[indice].setTranslateX((centroX - ((Sphere) spheres[indice].getChildren().getFirst()).getRadius() * 2 + 50) * Math.cos((2 * Math.PI * (i + 1) / n)+(Math.PI/2)) + centroX);
            spheres[indice].setTranslateY((centroY - ((Sphere) spheres[indice].getChildren().getFirst()).getRadius() * 2 + 40) * Math.sin((2 * Math.PI * (i + 1) / n)+(Math.PI/2)) + centroY);
            spheres[indice].setScaleX(i == n-1 ? 0.6 : 1);
            spheres[indice].setScaleY(i == n-1 ? 0.6 : 1);
        }
    }
    public void cambiaTurno() {
        Timeline timeline = new Timeline();
        for (int ind = 0; ind < n-1; ind++) {
            int i = ind % n;
            KeyValue kv1 = new KeyValue(spheres[i].translateXProperty(), spheres[i+1].getTranslateX());
            KeyValue kv2 = new KeyValue(spheres[i].translateYProperty(), spheres[i+1].getTranslateY());
            KeyValue kv3 = new KeyValue(spheres[i].scaleXProperty(), spheres[i+1].getScaleX());
            KeyValue kv4 = new KeyValue(spheres[i].scaleYProperty(), spheres[i+1].getScaleY());
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv1, kv2, kv3, kv4);
            timeline.getKeyFrames().add(kf);
        }
        KeyValue kv1 = new KeyValue(spheres[n-1].translateXProperty(), spheres[0].getTranslateX());
        KeyValue kv2 = new KeyValue(spheres[n-1].translateYProperty(), spheres[0].getTranslateY());
        KeyValue kv3 = new KeyValue(spheres[n-1].scaleXProperty(), spheres[0].getScaleX());
        KeyValue kv4 = new KeyValue(spheres[n-1].scaleYProperty(), spheres[0].getScaleY());
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv1, kv2, kv3, kv4);
        timeline.getKeyFrames().add(kf);
        timeline.playFromStart();
        timeline.setOnFinished(event -> {
            turnoDi=(turnoDi==(n-1))?0:turnoDi+1;
            giocoIo(turnoDi);
            posizionaSfere();
        });
    }

    public void giocoIo(int turnoDi) {
        Giocatore player = gameData.getGiocatoriPartita().get(turnoDi);
        gameData.setTurnoCorrente(turnoDi);
        player.getMano();
    }
    private void addSelectionMouse() {
        for (int i = 0; i < n; i++) {
            final Sphere currentSphere = ((Sphere) spheres[i].getChildren().getFirst());
            final Group currentGroup = spheres[i];
            currentSphere.setOnMousePressed(event -> {
                MainController.setDisableHD(false);
                double x = currentGroup.getTranslateX();
                double y = currentGroup.getTranslateY();
                double s = currentGroup.getScaleX();
                double scala = (centroX%centroY)/100;
                spostaSfere(currentGroup, scala, centroX, centroY);
                Button bottone = new Button("<---");
                bottone.setTranslateX(10);
                bottone.setTranslateY(10);
                Pane overlay = new Pane();
                overlay.setPrefSize(centroX*2,centroY*2);
                overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(mettibottone -> {
                    ovalPane.getChildren().remove(currentGroup);
                    ovalPane.getChildren().addAll(overlay, bottone, currentGroup);
                    addMouseHandlers(currentSphere);
                });
                pause.play();
                bottone.setOnAction(evento -> {
                    MainController.setDisableHD(true);
                    spostaSfere(currentGroup, s, x, y);
                    ovalPane.getChildren().removeAll(bottone,overlay);
                    resetMouseHandlers(currentSphere);
                    posizionaSfere();
                });
            });
        }
    }
    private void spostaSfere(Group currentGroup, double scala, double centroX, double centroY) {
        Timeline timeline = new Timeline();
        KeyValue kv1 = new KeyValue(currentGroup.translateXProperty(), centroX);
        KeyValue kv2 = new KeyValue(currentGroup.translateYProperty(), centroY);
        KeyValue scx = new KeyValue(currentGroup.scaleXProperty(), scala);
        KeyValue scy = new KeyValue(currentGroup.scaleYProperty(), scala);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv1, kv2, scx, scy);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
    private void resetMouseHandlers(Sphere sfera) {
        sfera.setOnMousePressed(null);
        sfera.setOnMouseDragged(null);
        sfera.setOnMouseReleased(null);
        addSelectionMouse();
    }
    private void addMouseHandlers(Sphere sfera) {
        final Sphere currentSphere = sfera;
        currentSphere.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                anchorAngleX = currentSphere.getRotate();
                anchorAngleY = currentSphere.getRotationAxis().getY();
                pauseAnimation();
            }
        });
        currentSphere.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double deltaX = event.getSceneX() - anchorX;
                double deltaY = event.getSceneY() - anchorY;
                currentSphere.setRotate(anchorAngleX - (deltaX * 0.2));
                Rotate RX = new Rotate(deltaY * 0.2, Rotate.X_AXIS);
                currentSphere.getTransforms().add(RX);
                currentSphere.setRotate(anchorAngleY - (deltaY * 0.2));
                Rotate RY = new Rotate(-deltaX * 0.2, Rotate.Y_AXIS);
                currentSphere.getTransforms().add(RY);
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
            }
        });
        currentSphere.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                currentSphere.setRotate(0);
                currentSphere.setRotationAxis(Rotate.Y_AXIS);
                currentSphere.getTransforms().clear();
                resumeAnimation();
            }
        });
    }
}