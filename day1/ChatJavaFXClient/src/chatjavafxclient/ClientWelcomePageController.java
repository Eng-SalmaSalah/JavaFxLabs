/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatjavafxclient;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author salma
 */
public class ClientWelcomePageController implements Initializable {

    @FXML
    private Pane background;
    @FXML
    private ImageView userImage;
    @FXML
    private Button uploadButton;
    @FXML
    private TextField userName;
    @FXML
    private Button signInButton;
    Stage primaryStage;
    Image image;
    File imgFile;
    

    public ClientWelcomePageController() {

    }

    public ClientWelcomePageController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        File file = new File("C:\\Users\\salma\\Documents\\NetBeansProjects\\ChatJavaFXClient\\build\\classes\\images\\icon.png");
        imgFile=file;
        image = new Image(file.toURI().toString());
        userImage.setImage(image);
        uploadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("select your image");
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
                File selectedImage = fileChooser.showOpenDialog(null);
                imgFile=selectedImage;
                image = new Image(selectedImage.toURI().toString());
                userImage.setImage(image);
                
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("ClientChatGui.fxml"));

                try {
                    
                    Parent root = loader.load();
                    Scene scene = new Scene(root, 550, 750);
                    ClientChatGuiController controller = loader.getController();
                    controller.setName(userName.getText());
                    controller.setImage(image);
                    controller.setImageFile(imgFile);
                   
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

    }

}
