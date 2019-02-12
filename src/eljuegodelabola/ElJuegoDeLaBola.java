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
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Screen;


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
    int puntuacion = 0;
    double velocidadAceleracion = 0;
    ImageView imageView;
    Circle bola;
    Circle bolaclaro;
    Circle bolablanca;
    Pane root;
    Pane root2;
    Group bolaCompleta;
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
        puntuacion += 1;
        
    }
    public void rebotederecha(){
        positivo = false;
        incBolaX--;
        bolaInicioY = bolaY;
        bolaInicioX = bolaX;
        anguloinicial= Math.abs(anguloinicial);
        incBolaX = 0;
        puntuacion += 1;
        
    }
    public void rebotearribaizquierda(){
        positivo = true;
        bolaInicioY = bolaY;
        bolaInicioX = bolaX;
        anguloinicial= Math.abs(anguloinicial);
        anguloinicial=-anguloinicial;
        incBolaX = 0;
        puntuacion += 1;
        
    }
    public void rebotearribaderecha(){
        positivo = false;
        bolaInicioY = bolaY;
        bolaInicioX = bolaX;
        anguloinicial= Math.abs(anguloinicial);
        incBolaX = 0;
        puntuacion += 1;
        
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
        puntuacion += 1;
        
    }
    public void reboteizquierdahaciaarriba(){
        System.out.println("LISTO");
        positivo = true;
        bolaInicioY = bolaY;
        bolaInicioX = bolaX;
        anguloinicial= Math.abs(anguloinicial);
        incBolaX = 0;
        rebAlto = false;
        puntuacion += 1;
        
    }
    public Group bolaCreacion(){         
        bola = new Circle();
        bola.setCenterX(0);
        bola.setCenterY(0);
        bola.setRadius(20);
        bola.setFill(Color.web("#006400"));
        System.out.println("bola1");
        // Primer claro
        bolaclaro = new Circle();
        bolaclaro.setCenterX(4);
        bolaclaro.setCenterY(-3);
        bolaclaro.setRadius(15);
        bolaclaro.setFill(Color.web("#008000"));
        System.out.println("bola2");
        // Segundo claro
        bolablanca = new Circle();
        bolablanca.setCenterX(6);
        bolablanca.setCenterY(-8);
        bolablanca.setRadius(5);
        bolablanca.setFill(Color.web("#7CFC00"));
        System.out.println("bola3");
        // Grupo de la Bola
        Group groupBola = new Group();
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
        puntuacion = 0;
        if(imageView != null){
        imageView.setRotate(0);
        velocidadAceleracion = 0;
    }
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
    
    public void resolucionnormal(){
        bola.setCenterX(0);
        bola.setCenterY(0);
        bola.setRadius(20);
                            
        bolaclaro.setCenterX(4);
        bolaclaro.setCenterY(-3);
        bolaclaro.setRadius(15);

        bolablanca.setCenterX(6);
        bolablanca.setCenterY(-8);
        bolablanca.setRadius(5);
    }
    
    public void resolucionGrande(){
        bola.setCenterX(0);
        bola.setCenterY(0);
        bola.setRadius(30);
        
        bolaclaro.setCenterX(6);
        bolaclaro.setCenterY(-4.5);
        bolaclaro.setRadius(22.5);
        
        bolablanca.setCenterX(9);
        bolablanca.setCenterY(-12);
        bolablanca.setRadius(7.5);
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
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("bolaIcono.PNG")));
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
        slider.valueProperty().addListener (new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    label5.setText(String.format("%.0f",new_val));
//                    System.out.println(label5.getText());
                    int i = new_val.intValue();
                    
                    switch(i){
                        
                        case 1:
                            System.out.println("Es un "+i);
                            break;

                        case 2:
                            System.out.println("Es un "+i);
                            break;
                            
                        case 3:
                            System.out.println("Es un "+i);
                            break;
                            
                        case 4:
                            System.out.println("Es un "+i);
                            break;
                            
                        case 5:
                            System.out.println("Valor por defecto");
                            break;
                            
                        case 6:
                            System.out.println("Es un "+i);
                            break;
                            
                        case 7:
                            System.out.println("Es un "+i);
                            break;
                            
                        case 8:
                            System.out.println("Es un "+i);
                            break;
                            
                        case 9:
                            System.out.println("Es un "+i);
                            break;
                            
                        case 10:
                            System.out.println("Es un "+i);
                            break;
                    }
            }

        });
        
        Label label2 = new Label("NumBola");
        Label label6 = new Label("");
        Slider slider2 = new Slider(1, 10, 1);
        slider2.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    label6.setText(String.format("%1.0f", new_val));
                    
                    
            }
        });
        Label label3 = new Label("Color de la bola");
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.setItems(FXCollections.observableArrayList(
        "Verde", "Rojo", "Amarillo", "Cian","Blanca","Morada","Bounce","Naranja","Spectrum","nula"));
        choiceBox.setTooltip(new Tooltip("Selecciona el tipo de bola"));
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { 

			// if the item of the list is changed 
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
//                            System.out.println(choiceBox.getItems().get((Integer) number2));
                            
                            switch((int) number2){
                                case 0:
                                    bola.setFill(Color.web("#006400"));
                                    bolaclaro.setFill(Color.web("#008000"));
                                    bolablanca.setFill(Color.web("#7CFC00"));
                                    break;
                                case 1:
                                    bola.setFill(Color.web("#640000"));
                                    bolaclaro.setFill(Color.web("#800000"));
                                    bolablanca.setFill(Color.web("#FC0000"));
                                    break;
                                
                                case 2:
                                    bola.setFill(Color.web("#646400"));
                                    bolaclaro.setFill(Color.web("#808000"));
                                    bolablanca.setFill(Color.web("#FCFC00"));
                                    break;
                                    
                                case 3:
                                    bola.setFill(Color.web("#006464"));
                                    bolaclaro.setFill(Color.web("#008080"));
                                    bolablanca.setFill(Color.web("#00FC80"));
                                    break;
                                case 4:
                                    bola.setFill(Color.web("646464"));
                                    bolaclaro.setFill(Color.web("808080"));
                                    bolablanca.setFill(Color.web("FCFCFC"));
                                    break;
                                case 5:
                                    bola.setFill(Color.web("640064"));
                                    bolaclaro.setFill(Color.web("800080"));
                                    bolablanca.setFill(Color.web("FF00FF"));
                                    break;
                                    
                                case 6:
                                    bola.setFill(Color.web("#8D2A24"));
                                    bolaclaro.setFill(Color.web("#E03020"));
                                    bolablanca.setFill(Color.web("#E03020"));
                                    break;
                                    
                                case 7:
                                    bola.setFill(Color.web("#643200"));
                                    bolaclaro.setFill(Color.web("#804000"));
                                    bolablanca.setFill(Color.web("#FC8000"));
                                    break;
                                case 8:
                                    bola.setFill(Color.web("#FF00FF"));
                                    bolaclaro.setFill(Color.web("#FF00FF"));
                                    bolablanca.setFill(Color.web("#FF00FF"));
                                    break;
                                
                                case 9:
                                    bola.setFill(Color.web("#000000"));
                                    bolaclaro.setFill(Color.web("#000000"));
                                    bolablanca.setFill(Color.web("#000000"));
                                    break;
                            }
                            
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
                FileChooser.ExtensionFilter extFilterGIF = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
                fileChooser.getExtensionFilters().addAll(extFilterPNG);
                fileChooser.getExtensionFilters().addAll(extFilterGIF);
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
                System.out.println(imageView.getFitHeight());
                System.out.println(imageView.getFitWidth());
                imageView.setLayoutX(-bola.getRadius());
                imageView.setLayoutY(-bola.getRadius());
                if (imageView != null){
                bolaCompleta.getChildren().add(imageView);
                System.out.println("imagen :D");
                }
            }
        });
        
        Label labelScore = new Label("Puntuacion: ");
        labelScore.setTextFill(Color.web("#FFFFFF"));
        labelScore.setFont(new Font(20));
        
        

        
        VBox vbox= new VBox(label,slider,label5,label2,slider2,label6,label3,choiceBox,button2,button);
        
        root2.getChildren().add(vbox);
        
        
        Label labelPuntuacion = new Label(String.valueOf(puntuacion));
        labelPuntuacion.setTextFill(Color.web("#FFFFFF"));
        labelPuntuacion.setFont(new Font(20));
        HBox hbox = new HBox(labelScore,labelPuntuacion);
        root.getChildren().add(hbox);
// menu
        primaryStage.setTitle("El juego de la bola");
        primaryStage.setScene(sceneMenu);
        primaryStage.show();
        
        AnimationTimer animationBall = new AnimationTimer(){
            @Override
            public void handle(long now){
                
                
                bolaCompleta.setLayoutX(bolaX);
                bolaCompleta.setLayoutY(bolaY);

                
                if (golpe == true){
                    radianes = anguloinicial*Math.PI/180;
                                 
                    if (positivo == true){
                        incBolaX++;
                        if (imageView != null){
                            velocidadAceleracion+=5;
                            imageView.setRotate(velocidadAceleracion);                            
                        }
                    }
                    
                    else if (positivo == false){
                        incBolaX--;
                        if (imageView != null){
                            velocidadAceleracion-=5;
                            imageView.setRotate(velocidadAceleracion);
                        }
                    }
                    movimiento();
                    if (dimensionX > 512 && dimensionY > 512){
                        velocidad = 187.5;
                        //187.5 
                        gravedad = 22;
                        //14.7105;
                    }
                    else{
                        
                        velocidad = 125;
                        gravedad = 9.807;
                    }
                    
                }
                
                
                //rebote
                if ((bolaX+bola.getRadius()) >= dimensionX){ //rebote pared derecha
                    rebotederecha();
                    labelPuntuacion.setText(String.valueOf(puntuacion+1));
                }
                    
                if  (((bolaX+bola.getRadius()) >= dimensionX)&&(rebAlto == true)){ // rebote hacia arriba
                    rebotederechahaciaarriba();
                    labelPuntuacion.setText(String.valueOf(puntuacion+1));
                }

                if ((bolaX-bola.getRadius()) <= 0){ // rebote pared izquierda
                    reboteizquierda();
                    labelPuntuacion.setText(String.valueOf(puntuacion+1));
                }    
                if (((bolaX-bola.getRadius()) <= 0)&&(rebAlto == true)){
                    reboteizquierdahaciaarriba();
                    labelPuntuacion.setText(String.valueOf(puntuacion+1));
                }
                
                
                if (bolaY >= dimensionY){
                    root.getChildren().remove(bolaCompleta);
                    System.out.println("GAME OVER");
                    reinicio();
                    labelPuntuacion.setText(String.valueOf(0));
                }
                
                if ((bolaY-bola.getRadius()) <= 0 && positivo == true){
                    rebotearribaizquierda();
                    labelPuntuacion.setText(String.valueOf(puntuacion+1));
                }
            
                if ((bolaY-bola.getRadius()) <= 0 && positivo == false){
                    rebotearribaderecha();
                    labelPuntuacion.setText(String.valueOf(puntuacion+1));
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
                            puntuacion = 0;
                            bolaInicioX= dimensionX/2;
                            bolaInicioY= dimensionY/2;
                            incBolaX = 0;
                            bolaX = dimensionX/2;
                            bolaY = dimensionY/2;
                            resolucionnormal();
                            if (imageView != null){
                            imageView.setRotate(0);
                            imageView.setFitHeight(bola.getRadius()*2);
                            imageView.setFitWidth(bola.getRadius()*2);
                            imageView.setLayoutX(-bola.getRadius());
                            imageView.setLayoutY(-bola.getRadius());
                            }
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
                            puntuacion = 0;
                            labelPuntuacion.setText(String.valueOf(puntuacion));
                            resolucionGrande();
                            if (imageView != null){
                            imageView.setRotate(0);
                            imageView.setFitHeight(bola.getRadius()*2);
                            imageView.setFitWidth(bola.getRadius()*2);
                            imageView.setLayoutX(-bola.getRadius());
                            imageView.setLayoutY(-bola.getRadius());
                            }
                            break;
                           
                        case F11:

                            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                            primaryStage.setWidth((primScreenBounds.getWidth()));
                            primaryStage.setHeight((primScreenBounds.getHeight()));
                            
                            System.out.println();
                            dimensionX = primaryStage.getWidth();
                            dimensionY = primaryStage.getHeight();
                            primaryStage.setFullScreen(true);
                            golpe = false;
                            bolaInicioX= dimensionX/2;
                            bolaInicioY= dimensionY/2;
                            incBolaX = 0;
                            puntuacion = 0;
                            labelPuntuacion.setText(String.valueOf(puntuacion));
                            bolaX = dimensionX/2;
                            bolaY = dimensionY/2;
                            resolucionGrande();
                            if (imageView != null){
                            imageView.setRotate(0);
                            imageView.setFitHeight(bola.getRadius()*2);
                            imageView.setFitWidth(bola.getRadius()*2);
                            imageView.setLayoutX(-bola.getRadius());
                            imageView.setLayoutY(-bola.getRadius());
                            }
                            break;

                        case ESCAPE:
                            primaryStage.setScene(sceneMenu);
                            dimensionX = 512;
                            dimensionY = 512;
                            primaryStage.setWidth(dimensionX);
                            primaryStage.setHeight(dimensionY);
                            golpe = false;
                            choiceBox.getSelectionModel().selectFirst();
                            bolaInicioX= dimensionX/2;
                            bolaInicioY= dimensionY/2;
                            incBolaX = 0;
                            bolaX = dimensionX/2;
                            bolaY = dimensionY/2;
                            if (imageView != null){
                            imageView.setImage(null);
                            }
                            System.out.println("Pausa/salir");
                            resolucionnormal();
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