package com.garden.gardensimulation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GardenSimulation extends Application {

    private Pane garden = new Pane();
    private List<Plant> plants = new ArrayList<>();
    private Random random = new Random();
    private Text info = new Text();

    private final Image flowerImg = new Image(getClass().getResource("/rose.jpeg").toExternalForm()); // sample flower
    private final Image vegetableImg = new Image(getClass().getResource("/potato.jpg").toExternalForm()); // sample vegetable
    private final Image fruitImg = new Image(getClass().getResource("/orange.jpg").toExternalForm()); // sample fruit

    @Override
    public void start(Stage primaryStage) {
        garden.setPrefSize(600, 400);
        garden.setStyle("-fx-background-color: lightgreen;");

        // Buttons
        Button addFlower = new Button("Add Flower");
        addFlower.setLayoutX(10);
        addFlower.setLayoutY(10);
        addFlower.setOnAction(e -> addPlant("Flower"));

        Button addVegetable = new Button("Add Vegetable");
        addVegetable.setLayoutX(100);
        addVegetable.setLayoutY(10);
        addVegetable.setOnAction(e -> addPlant("Vegetable"));

        Button addFruit = new Button("Add Fruit");
        addFruit.setLayoutX(220);
        addFruit.setLayoutY(10);
        addFruit.setOnAction(e -> addPlant("Fruit"));

        Button fertilize = new Button("Fertilize");
        fertilize.setLayoutX(340);
        fertilize.setLayoutY(10);
        fertilize.setOnAction(e -> fertilizePlants());

        Button pesticide = new Button("Spray Pesticide");
        pesticide.setLayoutX(420);
        pesticide.setLayoutY(10);
        pesticide.setOnAction(e -> sprayPesticide());

        info.setX(10);
        info.setY(50);

        garden.getChildren().addAll(addFlower, addVegetable, addFruit, fertilize, pesticide, info);

        // Timeline to update growth
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updatePlants()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Scene scene = new Scene(garden);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Garden Simulation with Images");
        primaryStage.show();
    }

    private void addPlant(String type) {
        double x = 50 + random.nextDouble() * 500;
        double y = 100 + random.nextDouble() * 250;
        ImageView imgView;
        switch (type) {
            case "Flower":
                imgView = new ImageView(flowerImg);
                imgView.setFitWidth(30);
                imgView.setFitHeight(30);
                break;
            case "Vegetable":
                imgView = new ImageView(vegetableImg);
                imgView.setFitWidth(30);
                imgView.setFitHeight(30);
                break;
            default:
                imgView = new ImageView(fruitImg);
                imgView.setFitWidth(30);
                imgView.setFitHeight(30);
                break;
        }
        imgView.setX(x);
        imgView.setY(y);
        Plant plant = new Plant(imgView, type);
        plants.add(plant);
        garden.getChildren().add(imgView);
    }

    private void fertilizePlants() {
        for (Plant p : plants) p.fertilize();
    }

    private void sprayPesticide() {
        for (Plant p : plants) p.removePests();
    }

    private void updatePlants() {
        int healthy = 0, dead = 0;
        for (Plant p : plants) {
            p.grow();
            if (random.nextDouble() < 0.05) p.addPest();
            if (p.isDead()) dead++;
            else healthy++;
        }
        info.setText("Plants: " + plants.size() + "  Healthy: " + healthy + "  Dead: " + dead);
    }

    class Plant {
        ImageView img;
        double size;
        double growthRate;
        boolean pest = false;
        String type;

        Plant(ImageView img, String type) {
            this.img = img;
            this.type = type;
            size = 30;
            switch (type) {
                case "Flower": growthRate = 1; break;
                case "Vegetable": growthRate = 0.7; break;
                case "Fruit": growthRate = 0.8; break;
            }
        }

        void grow() {
            if (!isDead()) {
                size += growthRate;
                img.setFitWidth(size);
                img.setFitHeight(size);
                if (pest) img.setOpacity(0.5);
                else img.setOpacity(1);
            }
        }

        void addPest() { pest = true; }

        void removePests() { pest = false; }

        void fertilize() { size += 3; }

        boolean isDead() { return size <= 0; }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
