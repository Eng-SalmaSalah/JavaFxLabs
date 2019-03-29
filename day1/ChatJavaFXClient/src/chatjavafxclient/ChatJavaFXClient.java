/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatjavafxclient;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;         
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author salma
 */
public class ChatJavaFXClient extends Application {
    
    @Override
    public void start(Stage primaryStage) { 
        primaryStage.setTitle("Jets chat room");
        Parent root = null;
        FXMLLoader loader=new FXMLLoader();
        ClientWelcomePageController controller = new ClientWelcomePageController(primaryStage);
        loader.setController(controller);
        
        try {
            root = loader.load(getClass().getResource("ClientWelcomeGui.fxml").openStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        Scene scene = new Scene(root, 750, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
