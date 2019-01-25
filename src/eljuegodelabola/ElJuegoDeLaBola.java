/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eljuegodelabola;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;


/**
 *
 * @author PC06
 */
// BUG IMPORTANTE, GOLPE DERECHO+ GOLPE IZQUIERDO NO HACE ARCO ¿arreglado?
public class ElJuegoDeLaBola extends Application {
    
    double dimensionX = 512; // dimension eje X
    double dimensionY = 512; // dimension eje Y
    double bolaX = dimensionX/2;
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
    double escalaX = 1;
    double escalaY = 1;
    
    @Override
    public void start(Stage primaryStage) {       
        Pane root = new Pane();
        Scene scene = new Scene(root, dimensionX, dimensionY);
        primaryStage.setResizable(false);
        primaryStage.setFullScreenExitHint("");
        scene.setFill(Color.web("#000000"));
        
        
        
        // menu
        
        Button button = new Button();
        button.setText("Empezar");
        button.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(scene);
            }
        });
        Label label = new Label("Tamaño bola");
        Slider slider = new Slider(1, 10, 1);
        slider.setShowTickMarks(true);
        Label label2 = new Label("NumBola");
        Slider slider2 = new Slider(1, 10, 1);
        slider2.setShowTickMarks(true);
        Label label3 = new Label("Color de la bola");
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().add("Verde");
        choiceBox.getItems().add("Amarillo");
        choiceBox.getItems().add("Azul");
        choiceBox.getItems().add("Rojo");
        VBox vbox= new VBox(label,slider,label2,slider2,label3,choiceBox,button);
    
        Scene menu = new Scene(vbox, dimensionX, dimensionY);
        menu.setFill(Color.web("#000000"));
        
        
        // menu
        
        primaryStage.setTitle("El juego de la bola");
        primaryStage.setScene(menu);
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

        Scale scale = new Scale();
                scale.setX(escalaX);
                scale.setY(escalaY);
                scale.setPivotX(bola.getRadius()*2);
                scale.setPivotY(bola.getRadius()*2);
                
        AnimationTimer animationBall = new AnimationTimer(){
            @Override
            public void handle(long now){
                bolaCompleta.setLayoutX(bolaX);
                bolaCompleta.setLayoutY(bolaY);
                
                
                bolaCompleta.getTransforms().add(scale);
                if (golpe == true){
                    radianes = anguloinicial*Math.PI/180;
                    
                    
                    if (positivo == true){
                        incBolaX++;
                    }
                    
                    else if (positivo == false){
                        incBolaX--;
                    }
                    movimiento();
                    
                }
                
                //rebote
                if ((bolaX+bola.getRadius()) >= dimensionX){   //rebote pared derecha
                    rebotederecha();
                }
                    
                if  (((bolaX+bola.getRadius()) >= dimensionX)&&(rebAlto == true)){                       // rebote hacia arriba
                    rebotederechahaciaarriba();
                }
                
                
                if ((bolaX-bola.getRadius()) <= 0){           // rebote pared izquierda
                    reboteizquierda();
                }    
                if (((bolaX-bola.getRadius()) <= 0)&&(rebAlto == true)){
                    reboteizquierdahaciaarriba();
                }
                
                
                if (bolaY >= dimensionY){
                    root.getChildren().remove(bolaCompleta);
                    System.out.println("GAME OVER");
                    reinicio();
                }
                
                if ((bolaY-bola.getRadius()) <= 0 && positivo == true){
                    rebotearribaizquierda();
                }
            
                if ((bolaY-bola.getRadius()) <= 0 && positivo == false){
                    rebotearribaderecha();
                }
//                System.out.println("bola X: "+bolaX);
//                System.out.println("bola Y: "+incBolaY);

            };
            
            public void movimiento(){
                incBolaY =-(incBolaX * Math.tan(radianes)-(gravedad*Math.pow(incBolaX,2))/(2*Math.pow(velocidad,2)*Math.pow(Math.cos(radianes),2)));
                    incBolaY = (incBolaY/10);
                    
                    bolaY = bolaInicioY + incBolaY;
                    bolaX = bolaInicioX + incBolaX;
            }
            public void reinicio(){
                golpe = false;
                bolaInicioX= dimensionX/2;
                bolaInicioY= dimensionY/2;
                incBolaX = 0;
                bolaX = dimensionX/2;
                bolaY = dimensionY/2;
                root.getChildren().add(bolaCompleta);
            }
            public void reboteizquierda(){
                positivo = true;
                incBolaX++;
                bolaInicioY = bolaY;
                bolaInicioX = bolaX;
                anguloinicial= Math.abs(anguloinicial);
                anguloinicial=-anguloinicial;
                incBolaX = 0;
            }
            public void rebotederecha(){
                positivo = false;
                incBolaX--;
                bolaInicioY = bolaY;
                bolaInicioX = bolaX;
                anguloinicial= Math.abs(anguloinicial);
                incBolaX = 0;
            }
            public void rebotearribaizquierda(){
                incBolaX--;
                bolaInicioY = bolaY;
                bolaInicioX = bolaX;
                anguloinicial= Math.abs(anguloinicial);
                anguloinicial=-anguloinicial;
                incBolaX = 0;
            }
            public void rebotearribaderecha(){
                incBolaX++;
                bolaInicioY = bolaY;
                bolaInicioX = bolaX;
                anguloinicial= Math.abs(anguloinicial);
                incBolaX = 0;
            }
            public void rebotederechahaciaarriba(){
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
            public void reboteizquierdahaciaarriba(){
                System.out.println("LISTO");
                positivo = true;
                incBolaX++;
                bolaInicioY = bolaY;
                bolaInicioX = bolaX;
                anguloinicial= Math.abs(anguloinicial);
                incBolaX = 0;
                rebAlto = false;
            }

        };
                
        
        scene.setOnKeyPressed((KeyEvent event) ->{
                   switch(event.getCode()) {
                       case F10:
                           dimensionX = 716.8;
                           dimensionY = 716.8;
                           primaryStage.setFullScreen(false);
                           primaryStage.setWidth(dimensionX);
                           primaryStage.setHeight(dimensionY);
                           
                           break;
                           
                       case F11:
                           dimensionX = 1366;
                           dimensionY = 768;
                           primaryStage.setWidth(dimensionX);
                           primaryStage.setHeight(dimensionY);
                           primaryStage.setFullScreen(true);
                           
                           break;
                           
                       case ESCAPE:
                           dimensionX = 512;
                           dimensionY = 512;
                           primaryStage.setFullScreen(false);
                           primaryStage.setWidth(dimensionX);
                           primaryStage.setHeight(dimensionY);
                           
                           break;
                   } 
                });
        bolaCompleta.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Insertar aquí el código a ejecutar cuando se haga clic en el ratón
                
//              
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
                    clickDerecho();
                }
                
                if (clickX <0 && clickY >0){
                    clickIzquierdo();
                }
                // También se puede comprobar sobre qué botón se ha actuado,
                //  válido para cualquier acción (pressed, released, clicked, etc) 
                if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                    golpe = true;
                }
                
                if(mouseEvent.getButton() == MouseButton.SECONDARY) {
                }
            }
            
            public void clickIzquierdo(){
                System.out.println("golpe-izquierdo");
                positivo = true;
                bolaInicioY = bolaY;
                bolaInicioX = bolaX;
                anguloinicial= Math.abs(anguloinicial);
                incBolaX = 0;
            }
            
            public void clickDerecho(){
                System.out.println("golpe-derecho");
                positivo = false;
                bolaInicioY = bolaY;
                bolaInicioX = bolaX;
                anguloinicial= Math.abs(anguloinicial);
                anguloinicial = -anguloinicial;
                incBolaX = 0;
            }        
        });
          animationBall.start();      
    }

}

