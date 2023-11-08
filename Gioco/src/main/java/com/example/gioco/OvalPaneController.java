package com.example.gioco;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Cylinder;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class OvalPaneController {
    @FXML
    private Pane ovalPane;
    private static Group[] spheres;
    private static double radiusX = 500.0;
    private static double radiusY = 300.0;
    private static double centroX = 650.0;
    private static double centroY = 450.0;
    private static final int n = 8;
    private double anchorX, anchorY;
    private static int turnoDi = 0;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;

    @FXML
    public void initialize() {
        spheres = new Group[n];
        for (int i = 0; i < n; i++) {
            spheres[i] = new Group();
            spheres[i].getChildren().add(new Sphere(50));
            PhongMaterial material = new PhongMaterial();
            Image texture = textures(i);
            material.setDiffuseMap(texture);
            ((Sphere) spheres[i].getChildren().get(0)).setMaterial(material);
            spheres[i].getChildren().get(0).setRotationAxis(Rotate.Y_AXIS);
            provaAnimazione(i);

            /*if (i==5){
                Cylinder ring = new Cylinder(80, 3);
                PhongMaterial materialS = new PhongMaterial();
                materialS.setDiffuseColor(Color.BEIGE);
                ring.setMaterial(materialS);
                ring.setRotationAxis(Rotate.Y_AXIS);
                ring.setRotate(75);
                spheres[i].getChildren().add(ring);
            }*/
            ovalPane.getChildren().add(spheres[i]);
        }
        ovalPane.heightProperty().addListener((obs, oldVal, newVal) -> posizionaSfere());
        addSelectionMouse();
    }

    private void provaAnimazione(int i) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                spheres[i].getChildren().get(0).rotateProperty().set(spheres[i].getChildren().get(0).getRotate() + 0.2);
            }
        };
        timer.start();
    }

    public static void setScenaX(double x){
        radiusX=(x/2-250);
        centroX=x/2;
        posizionaSfere();
    }
    public static void setScenaY(double y){
        radiusY=(y/2-150);
        centroY=y/2;
        posizionaSfere();
    }

    private Image textures(int i){
        return switch (i) {
            case 0 -> new Image(getClass().getResource("mercury.jpg").toString());
            case 1 -> new Image(getClass().getResource("venus.jpg").toString());
            case 3 -> new Image(getClass().getResource("mars.jpg").toString());
            case 4 -> new Image(getClass().getResource("jupiter.jpg").toString());
            case 5 -> new Image(getClass().getResource("saturn.jpg").toString());
            case 6 -> new Image(getClass().getResource("uranus.jpg").toString());
            case 7 -> new Image(getClass().getResource("neptune.jpg").toString());
            default -> new Image(getClass().getResource("earth.jpg").toString());
        };
    }



    private static void posizionaSfere() {
        for (int i = 0; i < n; i++) {
            int indice = ((8-turnoDi) + i) % n;
            spheres[indice].setTranslateX(radiusX * Math.cos(2 * Math.PI * (i + 1) / n) + centroX);
            spheres[indice].setTranslateY(radiusY * Math.sin(2 * Math.PI * (i + 1) / n) + centroY);
        }
    }

    public void cambiaTurno() {
        Timeline timeline = new Timeline();
        for (int i = 0; i < n-1; i++) {
            KeyValue kv1 = new KeyValue(spheres[i].translateXProperty(), spheres[i+1].getTranslateX());
            KeyValue kv2 = new KeyValue(spheres[i].translateYProperty(), spheres[i+1].getTranslateY());
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv1, kv2);
            timeline.getKeyFrames().add(kf);
        }
        KeyValue kv1 = new KeyValue(spheres[n-1].translateXProperty(), spheres[0].getTranslateX());
        KeyValue kv2 = new KeyValue(spheres[n-1].translateYProperty(), spheres[0].getTranslateY());
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.playFromStart();
        timeline.setOnFinished(event -> {
            turnoDi=(turnoDi==7)?0:turnoDi+1;
            posizionaSfere();
        });
    }

    private void addSelectionMouse() {
        for (int i = 0; i < n; i++) {
            final Sphere currentSphere = ((Sphere) spheres[i].getChildren().get(0));
            final Group currentGroup = spheres[i];
            currentSphere.setOnMousePressed(event -> {
                double x = currentGroup.getTranslateX();
                double y = currentGroup.getTranslateY();
                double z = currentGroup.getTranslateZ();
                Timeline timeline = new Timeline();
                KeyValue kv1 = new KeyValue(currentGroup.translateXProperty(), centroX);
                KeyValue kv2 = new KeyValue(currentGroup.translateYProperty(), centroY);
                KeyValue kv3 = new KeyValue(currentGroup.translateZProperty(), -(centroX-centroY>=0?centroY*2.8:centroX*2.8));
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv1, kv2, kv3);
                timeline.getKeyFrames().add(kf);
                timeline.play();
                Button bottone = new Button("<---");
                bottone.setTranslateX(x-35);
                bottone.setTranslateY(y);
                Pane overlay = new Pane();
                overlay.setPrefSize(centroX*2,centroY*2);
                overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(mettibottone -> {
                    ovalPane.getChildren().add(overlay);
                    ovalPane.getChildren().remove(currentGroup);
                    ovalPane.getChildren().addAll(currentGroup, bottone);
                    addMouseHandlers(currentSphere);
                });
                pause.play();
                bottone.setOnAction(evento -> {
                    Timeline timelinez = new Timeline();
                    System.out.println("okokok");
                    KeyValue kva = new KeyValue(currentGroup.translateXProperty(), x);
                    KeyValue kvb = new KeyValue(currentGroup.translateYProperty(), y);
                    KeyValue kvc = new KeyValue(currentGroup.translateZProperty(), z);
                    KeyFrame kff = new KeyFrame(Duration.seconds(1), kva, kvb, kvc);
                    timelinez.getKeyFrames().add(kff);
                    timelinez.play();
                    ovalPane.getChildren().removeAll(bottone,overlay);
                    resetMouseHandlers(currentSphere);
                });
            });

        }
    }
    private void resetMouseHandlers(Sphere sfera) {
        final Sphere currentSphere = sfera;
        currentSphere.setOnMousePressed(null);
        currentSphere.setOnMouseDragged(null);
        currentSphere.setOnMouseReleased(null);
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
            }
        });
        currentSphere.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double deltaX = event.getSceneX() - anchorX;
                double deltaY = event.getSceneY() - anchorY;
                currentSphere.setRotate(anchorAngleX - (deltaX * 0.2));
                currentSphere.getTransforms().add(new Rotate(deltaY * 0.2, Rotate.X_AXIS));
                currentSphere.setRotate(anchorAngleY - (deltaY * 0.2));
                currentSphere.getTransforms().add(new Rotate(-deltaX * 0.2, Rotate.Y_AXIS));
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
            }
        });
        currentSphere.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                currentSphere.setRotate(0);
                currentSphere.setRotationAxis(Rotate.Y_AXIS);
                currentSphere.getTransforms().clear();
            }
        });
    }
}