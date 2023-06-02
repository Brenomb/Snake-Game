package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloApplication extends Application {

    Pane areaDiGioco = new Pane();
    Label lScore = new Label("score: 1");
    Label gameOver = new Label("GAME OVER!");
    Button restart = new Button("restart");
    int score = 1;
    int alt = 10;
    int lung = 10;
    int snakeY = 250;
    int snakeX = 250;
    int appley = (int)(Math.random() * 50.0);
    int appleY;
    int applex;
    int appleX;
    int tempo;
    Rectangle[] snakeL;
    int direzione;
    boolean toc;
    Rectangle snake;
    Rectangle apple;
    int snakeLung;

    public HelloApplication() {

        this.appleY = this.appley * 10;
        this.applex = (int)(Math.random() * 50.0);
        this.appleX = this.applex * 10;
        this.tempo = 0;
        this.snakeL = new Rectangle[100];
        this.direzione = 1;
        this.toc = false;
        this.apple = new Rectangle(10.0, 10.0);
        this.snakeLung = 1;
    }

    public void start(Stage primaryStage) throws Exception {
        this.areaDiGioco.setPrefSize(500.0, 500.0);
        this.snakeL[0] = new Rectangle((double)this.alt, (double)this.lung);

        for(int i = 1; i < 100; ++i) {
            this.snakeL[i] = new Rectangle(0.0, 0.0);
        }

        this.areaDiGioco.getChildren().add(this.lScore);
        this.snake = this.snakeL[0];
        this.snake.setFill(Color.WHITE);
        this.snake.setY((double)this.snakeY);
        this.snake.setX((double)this.snakeX);
        this.apple.setFill(Color.RED);
        this.apple.setY((double)this.appleY);
        this.apple.setX((double)this.appleX);
        this.areaDiGioco.getChildren().add(this.snake);
        this.areaDiGioco.getChildren().add(this.apple);
        Scene scene = new Scene(this.areaDiGioco, 500.0, 500.0);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setOnKeyPressed((e) -> {this.pigiato(e);});
        this.restart.setOnAction((e) -> {
            this.eRestart();
        });
        scene.getStylesheets().add("it/edu/iisgubbio/snake/snakeS.css");
        this.areaDiGioco.setId("background");
        this.lScore.setId("score");
        this.gameOver.setId("gameOver");
        Timeline timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.seconds(0.1), (x) -> {
            this.snakeMovement();
        }, new KeyValue[0])});
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void eRestart() {
        this.score = 1;
        this.lScore.setText("score: " + this.score);
        this.snakeY = 250;
        this.snakeX = 250;
        this.snake.setY((double)this.snakeY);
        this.snake.setX((double)this.snakeX);
        this.areaDiGioco.getChildren().remove(this.gameOver);
        this.areaDiGioco.getChildren().remove(this.restart);
        this.toc = false;
        int appley = (int)(Math.random() * 50.0);
        int appleY = appley * 10;
        int applex = (int)(Math.random() * 50.0);
        int appleX = applex * 10;
        this.apple.setY((double)appleY);
        this.apple.setX((double)appleX);

        for(int i = this.snakeLung; i > 0; --i) {
            this.areaDiGioco.getChildren().remove(this.snakeL[i]);
        }

        this.snakeLung = 1;
    }

    private void snakeMovement() {
        int a;
        if (!this.toc) {
            switch (this.direzione) {
                case 1:
                    if (this.snake.getY() != 0.0) {
                        this.snakeY -= 10;
                    } else {
                        this.toc = true;
                        this.areaDiGioco.getChildren().add(this.gameOver);
                        this.areaDiGioco.getChildren().add(this.restart);
                    }
                    break;
                case 2:
                    if (this.snake.getX() != 490.0) {
                        this.snakeX += 10;
                    } else {
                        this.toc = true;
                        this.areaDiGioco.getChildren().add(this.gameOver);
                        this.areaDiGioco.getChildren().add(this.restart);
                    }
                    break;
                case 3:
                    if (this.snake.getY() != 490.0) {
                        this.snakeY += 10;
                    } else {
                        this.toc = true;
                        this.areaDiGioco.getChildren().add(this.gameOver);
                        this.areaDiGioco.getChildren().add(this.restart);
                    }
                    break;
                case 4:
                    if (this.snake.getX() != 0.0) {
                        this.snakeX -= 10;
                    } else {
                        this.toc = true;
                        this.areaDiGioco.getChildren().add(this.gameOver);
                        this.areaDiGioco.getChildren().add(this.restart);
                    }
            }

            for(a = 3; a < this.snakeLung; ++a) {
                if (this.snakeL[0].getX() == this.snakeL[a].getX() && this.snakeL[0].getY() == this.snakeL[a].getY()) {
                    this.toc = true;
                    this.areaDiGioco.getChildren().add(this.gameOver);
                    this.areaDiGioco.getChildren().add(this.restart);
                }
            }
        }

        this.snakeL[0].setX((double)this.snakeX);
        this.snakeL[0].setY((double)this.snakeY);

        for(a = this.snakeLung; a > 0; --a) {
            this.snakeL[a].setX(this.snakeL[a - 1].getX());
            this.snakeL[a].setY(this.snakeL[a - 1].getY());
        }

        if (this.snake.getY() == this.apple.getY() && this.snake.getX() == this.apple.getX()) {
            ++this.snakeLung;
            this.snakeL[this.snakeLung].setWidth(10.0);
            this.snakeL[this.snakeLung].setHeight(10.0);
            this.snakeL[this.snakeLung].setFill(Color.WHITE);
            this.snakeL[this.snakeLung].setX(this.snakeL[0].getX());
            this.snakeL[this.snakeLung].setY(this.snakeL[0].getY());
            this.areaDiGioco.getChildren().add(this.snakeL[this.snakeLung]);
            int appley = (int)(Math.random() * 50.0);
            int appleY = appley * 10;
            int applex = (int)(Math.random() * 50.0);
            int appleX = applex * 10;
            this.apple.setY((double)appleY);
            this.apple.setX((double)appleX);
            ++this.score;
            this.lScore.setText("score: " + this.score);
        }

    }

    private void pigiato(KeyEvent e) {
        if (e.getCode() == KeyCode.DOWN && this.snake.getY() != 500.0 && this.direzione != 1) {
            this.direzione = 3;
        }

        if (e.getCode() == KeyCode.UP && this.snake.getY() != 0.0 && this.direzione != 3) {
            this.direzione = 1;
        }

        if (e.getCode() == KeyCode.RIGHT && this.snake.getX() != 500.0 && this.direzione != 4) {
            this.direzione = 2;
        }

        if (e.getCode() == KeyCode.LEFT && this.snake.getX() != 0.0 && this.direzione != 2) {
            this.direzione = 4;
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
