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
import static javafx.scene.input.KeyCode.F11;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


/**
 *
 * @author PC06
 */
// BUG IMPORTANTE, GOLPE DERECHO+ GOLPE IZQUIERDO NO HACE ARCO ¿arreglado?
public class ElJuegoDeLaBola extends Application {
    
    double dimensionX = 512; // dimension eje X
    double dimensionY = 512; // dimension eje Y
    double bolaX = dimensionX/2+200;
    double bolaY = dimensionY/2;
    double incBolaY = dimensionY/2; // incremento de bola Y
    double incBolaX = 0; // incremento de bola X inicialmente nulo
    double anguloinicial = 88.80; //angulo 
    boolean golpe = false;
    double gravedad = 9.807;
    double velocidad = 125;  // mas grande mas lento, mayor arco;Mas pequeño mas rapido, menor arco
    double radianes = 0;
    double bolaInicioX = dimensionX/2; //posicion de la bola
    double bolaInicioY = dimensionY/2; //posicion de la bola
    double clickX = 0;
    double clickY = 0;
    boolean positivo = false;
    boolean rebAlto = false;
    
    @Override
    public void start(Stage primaryStage) {       
        Pane root = new Pane();
        Scene scene = new Scene(root, dimensionX, dimensionY);
        scene.setFill(Color.web("#000000"));
        primaryStage.setTitle("El juego de la bola");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        
        // Bola principal
        Circle bola = new Circle();
        bola.setCenterX(0);
        bola.setCenterY(0);
        bola.setRadius(20);
        bola.setFill(Color.web("#006400"));
        
        // Primer claro
        Circle bolaclaro = new Circle();
        bolaclaro.setCenterX(4);
        bolaclaro.setCenterY(-3);
        bolaclaro.setRadius(15);
        bolaclaro.setFill(Color.web("#008000"));
        
        // Segundo claro
        Circle bolablanca = new Circle();
        bolablanca.setCenterX(6);
        bolablanca.setCenterY(-8);
        bolablanca.setRadius(5);
        bolablanca.setFill(Color.web("#7CFC00"));
        
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
                    
                    
                    if (positivo == true){
                        incBolaX++;
                        
                    }
                    
                    else if (positivo == false){
                        incBolaX--;
                        
                    }
                    incBolaY =-(incBolaX * Math.tan(radianes)-(gravedad*Math.pow(incBolaX,2))/(2*Math.pow(velocidad,2)*Math.pow(Math.cos(radianes),2)));
                    incBolaY = (incBolaY/10);
                    
                    bolaY = bolaInicioY + incBolaY;
                    bolaX = bolaInicioX + incBolaX;
                    
                }
                
                //rebote
                if ((bolaX+bola.getRadius()) >= dimensionX){   //rebote pared derecha
                    positivo = false;
                    incBolaX--;
                    bolaInicioY = bolaY;
                    bolaInicioX = bolaX;
                    anguloinicial= Math.abs(anguloinicial);
                    incBolaX = 0;
                }
                    
                if  (((bolaX+bola.getRadius()) >= dimensionX)&&(rebAlto == true)){                       // rebote hacia arriba
                    System.out.println("LISTO");
                    positivo = false;
                    incBolaX--;
                    bolaInicioY = bolaY;
                    bolaInicioX = bolaX;
                    anguloinicial= Math.abs(anguloinicial);
                    anguloinicial=-anguloinicial;
                    incBolaX = 0;
                    rebAlto = false;
                }
                
                
                if ((bolaX-bola.getRadius()) <= 0){           // rebote pared izquierda
                    positivo = true;
                    incBolaX++;
                    bolaInicioY = bolaY;
                    bolaInicioX = bolaX;
                    anguloinicial= Math.abs(anguloinicial);
                    anguloinicial=-anguloinicial;
                    incBolaX = 0;
                    
                if (((bolaX-bola.getRadius()) <= 0)&&(rebAlto == true)){
                    System.out.println("LISTO");
                    positivo = true;
                    incBolaX++;
                    bolaInicioY = bolaY;
                    bolaInicioX = bolaX;
                    anguloinicial= Math.abs(anguloinicial);
                    incBolaX = 0;
                    rebAlto = false;
                    }
                }
                
                if (bolaY >= dimensionY){
                    root.getChildren().remove(bolaCompleta);
                    System.out.println("GAME OVER");
                    primaryStage.close();
                }
                
                if ((bolaY-bola.getRadius()) <= 0 && positivo == true){
                    
                    incBolaX--;
                    bolaInicioY = bolaY;
                    bolaInicioX = bolaX;
                    anguloinicial= Math.abs(anguloinicial);
                    anguloinicial=-anguloinicial;
                    incBolaX = 0;
                }
            
                if ((bolaY-bola.getRadius()) <= 0 && positivo == false){
                    incBolaX++;
                    bolaInicioY = bolaY;
                    bolaInicioX = bolaX;
                    anguloinicial= Math.abs(anguloinicial);
                    incBolaX = 0;
                }
//                System.out.println("bola X: "+bolaX);
//                System.out.println("bola Y: "+incBolaY);
            };
        };
                
        
        scene.setOnKeyPressed((KeyEvent event) ->{
                   switch(event.getCode()) {
                       case F11:
                           primaryStage.setMaximized(true);
                           break;
                           
                       case ESCAPE:
                           
                   } 
                });
        bolaCompleta.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Insertar aquí el código a ejecutar cuando se haga clic en el ratón
                
//                System.out.println("Raton pulsado en: " + 
//                        "X " +mouseEvent.getX() + " Y " + mouseEvent.getY());
                clickX = mouseEvent.getX();
//                System.out.println(clickX);
                clickY = mouseEvent.getY();
//                System.out.println(clickY);
                
                    if ((bolaX <= dimensionX/8 )||(bolaX >= dimensionX-dimensionX/8)){
                        rebAlto = true;
                        System.out.println("REBOTE");
                    }
                    else {
                        rebAlto = false;
                    }
                    
                if (clickX >0 && clickY >0){
                    System.out.println("golpe-derecho");
                    positivo = false;
                    bolaInicioY = bolaY;
                    bolaInicioX = bolaX;
                    anguloinicial= Math.abs(anguloinicial);
                    anguloinicial = -anguloinicial;
                    incBolaX = 0;
                }
                
                if (clickX <0 && clickY >0){
                    System.out.println("golpe-izquierdo");
                    positivo = true;
                    bolaInicioY = bolaY;
                    bolaInicioX = bolaX;
                    anguloinicial= Math.abs(anguloinicial);
                    incBolaX = 0;
                }
                // También se puede comprobar sobre qué botón se ha actuado,
                //  válido para cualquier acción (pressed, released, clicked, etc) 
                if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                    golpe = true;
                }
                
                if(mouseEvent.getButton() == MouseButton.SECONDARY) {
                }
            }
        });
          animationBall.start();      
    }

}

