/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eljuegodelabola;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


/**
 *
 * @author PC06
 */
public class ElJuegoDeLaBola extends Application {
    
    double dimensionX = 512;
    double dimensionY = 512;
    double bolaX = dimensionX/2;
    double bolaY = dimensionY/2;
    
    @Override
    public void start(Stage primaryStage) {       
        Pane root = new Pane();
        Scene scene = new Scene(root, dimensionX, dimensionY);
        scene.setFill(Color.BLACK);
        primaryStage.setTitle("El juego de la bola");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Circle bola = new Circle();
        bola.setCenterX(bolaX);
        bola.setCenterY(bolaY);
        bola.setRadius(20);
        bola.setFill(Color.DARKGREEN);
        root.getChildren().add(bola);
        
        Circle bolaclaro = new Circle();
        bolaclaro.setCenterX(bolaX+4);
        bolaclaro.setCenterY(bolaY-3);
        bolaclaro.setRadius(15);
        bolaclaro.setFill(Color.GREEN);
        root.getChildren().add(bolaclaro);
        
        Circle bolablanca = new Circle();
        bolablanca.setCenterX(bolaX+6);
        bolablanca.setCenterY(bolaY-8);
        bolablanca.setRadius(5);
        bolablanca.setFill(Color.LAWNGREEN);
        root.getChildren().add(bolablanca);
        
        AnimationTimer animationBall = new AnimationTimer(){
            @Override
            public void handle(long now){
                bolaY++;
                System.out.println(bolaY);
            };
        };
          animationBall.start();      
    }

}
