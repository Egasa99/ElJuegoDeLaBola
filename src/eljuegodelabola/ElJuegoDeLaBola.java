/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eljuegodelabola;


import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.beans.value.ChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;


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
    boolean rotacion = false;
    String color1="#006400";
    String color2="#008000";
    String color3="#7CFC00";
    
    ImageView imageView;
    Circle bola;
    Pane root;
    Pane root2;
    Group bolaCompleta;
    Group groupBola;
    public void movimiento(){
        incBolaY =-(incBolaX * Math.tan(radianes)-(gravedad*Math.pow(incBolaX,2))/(2*Math.pow(velocidad,2)*Math.pow(Math.cos(radianes),2)));
        incBolaY = (incBolaY/10);
                    
        bolaY = bolaInicioY + incBolaY;
        bolaX = bolaInicioX + incBolaX;
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
        positivo = true;
        bolaInicioY = bolaY;
        bolaInicioX = bolaX;
        anguloinicial= Math.abs(anguloinicial);
        anguloinicial=-anguloinicial;
        incBolaX = 0;
    }
    public void rebotearribaderecha(){
        positivo = false;
        bolaInicioY = bolaY;
        bolaInicioX = bolaX;
        anguloinicial= Math.abs(anguloinicial);
        incBolaX = 0;
    }
    public void rebotederechahaciaarriba(){
        System.out.println("LISTO");
        positivo = false;
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
        bolaInicioY = bolaY;
        bolaInicioX = bolaX;
        anguloinicial= Math.abs(anguloinicial);
        incBolaX = 0;
        rebAlto = false;
    }
    public Group bolaCreacion(){         
        bola = new Circle();
        bola.setCenterX(0);
        bola.setCenterY(0);
        bola.setRadius(20);
        bola.setFill(Color.web(color1));
        System.out.println("bola1");
        // Primer claro
        Circle bolaclaro = new Circle();
        bolaclaro.setCenterX(4);
        bolaclaro.setCenterY(-3);
        bolaclaro.setRadius(15);
        bolaclaro.setFill(Color.web(color2));
        System.out.println("bola2");
        // Segundo claro
        Circle bolablanca = new Circle();
        bolablanca.setCenterX(6);
        bolablanca.setCenterY(-8);
        bolablanca.setRadius(5);
        bolablanca.setFill(Color.web(color3));
        System.out.println("bola3");
        // Grupo de la Bola
        groupBola = new Group();
        groupBola.getChildren().add(bola);
        groupBola.getChildren().add(bolaclaro);
        groupBola.getChildren().add(bolablanca);
        System.out.println("grupo Creado");
        
        root.getChildren().add(groupBola);
        System.out.println("bolaAñadida2");
        return groupBola;
    }
    public void reinicio(){
        golpe = false;
        bolaInicioX= dimensionX/2;
        bolaInicioY= dimensionY/2;
        incBolaX = 0;
        bolaX = dimensionX/2;
        bolaY = dimensionY/2;
        root.getChildren().add(bolaCompleta);
        System.out.println("bolaAñadida1");
    }
    public void clickIzquierdo(){
        System.out.println("golpe-izquierdo");
        positivo = true;
        bolaInicioY = bolaY;
        bolaInicioX = bolaX;
        anguloinicial= Math.abs(anguloinicial);
        incBolaX = 0;
    }     
    public void clickIzquierdoSuperior(){
        System.out.println("golpe-superior-izquierdo");
        positivo = true;
        bolaInicioY = bolaY;
        bolaInicioX = bolaX;
        anguloinicial= Math.abs(anguloinicial);
        anguloinicial=-anguloinicial;
        incBolaX = 0;
    }
    public void clickDerechoSuperior(){
        System.out.println("golpe-superior-derecho");
        positivo = false;
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
    @Override
    public void start(Stage primaryStage) {       
        root = new Pane();
        root2 = new Pane();
        bolaCompleta = bolaCreacion();
        Scene sceneBola = new Scene(root, dimensionX, dimensionY,Color.web("#000000"));
        Scene sceneMenu = new Scene(root2, dimensionX, dimensionY,Color.web("#FFFFFF"));
        primaryStage.setResizable(false);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setWidth(dimensionX);
        primaryStage.setHeight(dimensionY);
        root.setStyle("-fx-background-color: transparent;");
        root2.setStyle("-fx-background-color: transparent;");  
        
// menu
        Button button = new Button();
        button.setText("Empezar");
        button.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(sceneBola);
                
            }
        });
        Label label = new Label("Tamaño bola");
        Label label5 = new Label("");
        Slider slider = new Slider(1, 10, 1);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    label5.setText(String.format("%1.0f", new_val));
                    System.out.println(label5.getText());
            }
        });
        
        Label label2 = new Label("NumBola");
        Label label6 = new Label("");
        Slider slider2 = new Slider(1, 10, 1);
        slider2.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    label6.setText(String.format("%1.0f", new_val));
                    System.out.println(label6.getText());
            }
        });
        Label label3 = new Label("Color de la bola");
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.setItems(FXCollections.observableArrayList(
        "Verde", "Rojo", "Amarillo", "Azul"));
        choiceBox.setTooltip(new Tooltip("Selecciona el tipo de bola"));
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { 

			// if the item of the list is changed 
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                            System.out.println(choiceBox.getItems().get((Integer) number2));
                            
                        } 
	}); 
        Button button2 = new Button();
        button2.setText("Cargar Imagen");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Usar imagen bola");
                
                FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
                fileChooser.getExtensionFilters().addAll(extFilterPNG);
                File file = fileChooser.showOpenDialog(null);
                String localUrl = null;
                try {
                    localUrl = file.toURI().toURL().toString();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ElJuegoDeLaBola.class.getName()).log(Level.SEVERE, null, ex);
                }
                Image image = new Image(localUrl);
                imageView = new ImageView(image);
                imageView.setFitHeight(bola.getRadius()*2);
                imageView.setFitWidth(bola.getRadius()*2);
                if (imageView != null){
                groupBola.getChildren().add(imageView);
                System.out.println("imagen :D");
                }
            }
        });
        
        
        VBox vbox= new VBox(label,slider,label5,label2,slider2,label6,label3,choiceBox,button2,button);
        root2.getChildren().add(vbox);
        
        
// menu
        primaryStage.setTitle("El juego de la bola");
        primaryStage.setScene(sceneMenu);
        primaryStage.show();
        
        AnimationTimer animationBall = new AnimationTimer(){
            @Override
            public void handle(long now){
                
                bolaCompleta.setLayoutX(bolaX);
                bolaCompleta.setLayoutY(bolaY);
                
                if (imageView != null){
                        imageView.setLayoutX(bolaX-bola.getRadius());
                        imageView.setLayoutY(bolaY-bola.getRadius());
                        }
                
                if (golpe == true){
                    radianes = anguloinicial*Math.PI/180;
                                        
                    if (positivo == true){
                        incBolaX++;
                        if (imageView != null){
                            imageView.setRotate(20);                            
                        }
                    }
                    
                    else if (positivo == false){
                        incBolaX--;
                        if (imageView != null){
                            imageView.setRotate(-20);
                        }
                    }
                    movimiento();
                    
                }
                
                
                //rebote
                if ((bolaX+bola.getRadius()) >= dimensionX){ //rebote pared derecha
                    rebotederecha();
                }
                    
                if  (((bolaX+bola.getRadius()) >= dimensionX)&&(rebAlto == true)){ // rebote hacia arriba
                    rebotederechahaciaarriba();
                }

                if ((bolaX-bola.getRadius()) <= 0){ // rebote pared izquierda
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

            };
            
            
 
        };
                
        
        sceneBola.setOnKeyPressed((KeyEvent event) ->{
                   switch(event.getCode()) {
                        
                        case F9:
                            dimensionX = 512;
                            dimensionY = 512;
                            primaryStage.setFullScreen(false);
                            primaryStage.setWidth(dimensionX);
                            primaryStage.setHeight(dimensionY);
                            golpe = false;
                            bolaInicioX= dimensionX/2;
                            bolaInicioY= dimensionY/2;
                            incBolaX = 0;
                            bolaX = dimensionX/2;
                            bolaY = dimensionY/2;
                            break;
                       
                        case F10:
                            dimensionX = 716.8;
                            dimensionY = 716.8;
                            primaryStage.setFullScreen(false);
                            primaryStage.setWidth(dimensionX);
                            primaryStage.setHeight(dimensionY);
                            golpe = false;
                            bolaInicioX= dimensionX/2;
                            bolaInicioY= dimensionY/2;
                            incBolaX = 0;
                            bolaX = dimensionX/2;
                            bolaY = dimensionY/2;
                            break;
                           
                        case F11:
                            dimensionX = 1366;
                            dimensionY = 768;
                            primaryStage.setWidth(dimensionX);
                            primaryStage.setHeight(dimensionY);
                            primaryStage.setFullScreen(true);
                            golpe = false;
                            bolaInicioX= dimensionX/2;
                            bolaInicioY= dimensionY/2;
                            incBolaX = 0;
                            bolaX = dimensionX/2;
                            bolaY = dimensionY/2;
                            break;

                        case ESCAPE:
                            primaryStage.setScene(sceneMenu);
                            golpe = false;
                            bolaInicioX= dimensionX/2;
                            bolaInicioY= dimensionY/2;
                            incBolaX = 0;
                            bolaX = dimensionX/2;
                            bolaY = dimensionY/2;
                            if (imageView != null){
                            imageView.setImage(null);
                            }
                            System.out.println("Pausa/salir");
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
                
                    if ((bolaX <= dimensionX/10.66 )||(bolaX >= dimensionX-dimensionX/10.66)){
                        rebAlto = true;
                        System.out.println("REBOTE");
                    }
                    else {
                        rebAlto = false;
                    }
                    
                if (clickX>0 && clickY>0){
                    clickDerecho();
                }
                
                if(clickX>0 && clickY<0){
                    clickDerechoSuperior();
                }
                if (clickX<0 && clickY>0){
                    clickIzquierdo();
                }
                
                if(clickX<0 && clickY<0){
                    clickIzquierdoSuperior();
                }
                // También se puede comprobar sobre qué botón se ha actuado,
                //  válido para cualquier acción (pressed, released, clicked, etc) 
                if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                    golpe = true;
                }
                
                if(mouseEvent.getButton() == MouseButton.SECONDARY) {
                    System.out.println("X: "+clickX);
                    System.out.println("Y: "+clickY);
                }
            }     
        });
          animationBall.start();      
    }
}

