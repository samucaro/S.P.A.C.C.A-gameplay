package com.example.spacca;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class OvalPaneController {
    @FXML
    private Pane ovalPane;
    private Group[] spheres;
    private double radiusX = 500.0;
    private double radiusY = 300.0;
    private final int n = 8;
    private double anchorX, anchorY;
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
            ((Sphere) spheres[i].getChildren().get(0)).setRotationAxis(Rotate.Y_AXIS);
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
                ((Sphere) spheres[i].getChildren().get(0)).rotateProperty().set(((Sphere) spheres[i].getChildren().get(0)).getRotate() + 0.2);
            }
        };
        timer.start();
    }

    private Image textures(int i){
        Image immaggine;
        switch (i) {
            case 0:
                immaggine = new Image("file:mercury.jpg");
                break;
            case 1:
                immaggine = new Image("file:venus.jpg");
                break;
            case 2:
                immaggine = new Image("file:earth.jpg");
                break;
            case 3:
                immaggine = new Image("file:mars.jpg");
                break;
            case 4:
                immaggine = new Image("file:jupiter.jpg");
                break;
            case 5:
                immaggine = new Image("file:saturn.jpg");
                break;
            case 6:
                immaggine = new Image("file:uranus.jpg");
                break;
            case 7:
                immaggine = new Image("file:neptune.jpg");
                break;
            default:
                immaggine = new Image("file:earth.jpg");
                break;
        }
        return immaggine;
    }


    private void posizionaSfere() {
        for (int i = 0; i < n; i++) {
            spheres[i].setTranslateX(radiusX * Math.cos(2 * Math.PI * (i + 1) / n) + 650);
            spheres[i].setTranslateY(radiusY * Math.sin(2 * Math.PI * (i + 1) / n) + 450);
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
        System.out.println("Fatto!!");
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
                KeyValue kv1 = new KeyValue(currentGroup.translateXProperty(),650);
                KeyValue kv2 = new KeyValue(currentGroup.translateYProperty(), 450);
                KeyValue kv3 = new KeyValue(currentGroup.translateZProperty(), -1300);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv1, kv2, kv3);
                timeline.getKeyFrames().add(kf);
                timeline.play();
                Button bottone = new Button("<---");
                bottone.setTranslateX(x-35);
                bottone.setTranslateY(y);
                Pane overlay = new Pane();
                overlay.setPrefSize(1300,900);
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
