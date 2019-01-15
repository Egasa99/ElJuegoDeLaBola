/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eljuegodelabola;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author PC06
 */
public class ElJuegoDeLaBola extends Application {
    
    double dimensionX = 512;
    double dimensionY = 512;
    double bolaX = dimensionX/2;
    double bolaY = dimensionY/2;
    double anguloinicial = 45;
    boolean golpe = false;
    double gravedad = -9.807;
    double velocidad = 7;
    double radianes = 0;
    double bolaInicioX = bolaX;
    double bolaInicioY = bolaY;
    @Override
    public void start(Stage primaryStage) {       
        Pane root = new Pane();
        Scene scene = new Scene(root, dimensionX, dimensionY);
        scene.setFill(Color.BLACK);
        primaryStage.setTitle("El juego de la bola");
        primaryStage.setScene(scene);
        primaryStage.show();
        // Bola principal
        Circle bola = new Circle();
        bola.setCenterX(0);
        bola.setCenterY(0);
        bola.setRadius(20);
        bola.setFill(Color.DARKGREEN);
        
        // Primer claro
        Circle bolaclaro = new Circle();
        bolaclaro.setCenterX(4);
        bolaclaro.setCenterY(-3);
        bolaclaro.setRadius(15);
        bolaclaro.setFill(Color.GREEN);
        
        // Segundo claro
        Circle bolablanca = new Circle();
        bolablanca.setCenterX(6);
        bolablanca.setCenterY(-8);
        bolablanca.setRadius(5);
        bolablanca.setFill(Color.LAWNGREEN);
        
        // bola completa
        Group bolaCompleta = new Group();
        bolaCompleta.getChildren().add(bola);
        bolaCompleta.getChildren().add(bolaclaro);
        bolaCompleta.getChildren().add(bolablanca);
        root.getChildren().add(bolaCompleta);
        
        AnimationTimer animationBall = new AnimationTimer(){
            @Override
            public void handle(long now){
                bolaCompleta.setLayoutX(bolaX);
                bolaCompleta.setLayoutY(bolaY);

                if (golpe == true){
                    radianes = anguloinicial*Math.PI/180;
                    bolaX++;
                    bolaY =-(bolaX * Math.tan(radianes)-(gravedad*Math.pow(bolaX,2))/(2*Math.pow(velocidad,2)*Math.pow(Math.cos(radianes),2)));
                    bolaY = bolaY/10;
                }
                if (bolaX > dimensionX){
                    bolaX = 0;
                }
                
                if (bolaX < 0){
                    bolaX = dimensionX;
                }
                
//                if (bolaY > dimensionY){
//                    bolaY = 0;
//                }
//                
//                if (bolaY < 0){
//                    bolaY = dimensionY;
//                }
                System.out.println("bola X: "+bolaX);
                System.out.println("bola Y: "+bolaY);
            };
        };
                 // Detectar clic en ratón (pulsado y soltado)
        bolaCompleta.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Insertar aquí el código a ejecutar cuando se haga clic en el ratón
                
                System.out.println("Mouse clicked X : Y - " + 
                        mouseEvent.getX() + " : " + mouseEvent.getY());
                // También se puede comprobar sobre qué botón se ha actuado,
                //  válido para cualquier acción (pressed, released, clicked, etc) 
                if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                    System.out.println("Botón principal");
                    golpe = true;
                }
            }
        });
          animationBall.start();      
    }

}

