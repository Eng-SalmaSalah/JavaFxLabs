/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatjavafxclient;

import chatjavafxcommon.Message;
import chatjavafxcommon.ServerInterface;
import chatjavafxcommon.clientInterface;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;

public class ClientChatGuiController extends UnicastRemoteObject implements clientInterface, Initializable {

    @FXML
    private ImageView userImage;
    @FXML
    private VBox vbox;
    @FXML
    private Button send;
    @FXML
    private Label userName;
    @FXML
    private TextField userInput;
    @FXML
    private ScrollPane scrollPane;

    Image userimg;
    String input;
    Circle imageCircle;
    clientInterface senderClient;
    File userImageFile;
    Stage stage;

    public ClientChatGuiController() throws RemoteException {
        senderClient = this;
    }

    public void getStage(Stage primaryStage) {
        stage=primaryStage;
    }

    public void setName(String userName) throws RemoteException {
        this.userName.setText(userName);

    }

    public void setImage(Image image) throws RemoteException {
        userImage.setImage(image);
        userimg = image;

    }

    public void setImageFile(File file) throws RemoteException {
        userImageFile = file;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Registry registry;

        try {
            registry = LocateRegistry.getRegistry("127.0.0.1");
            ServerInterface serverInterfaceRefrence = (ServerInterface) registry.lookup("chattingService");
            serverInterfaceRefrence.registerClient(this);



            send.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String messageBody = userInput.getText();
                    //sending image
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    try {
                        BufferedImage img = ImageIO.read(userImageFile);
                        javax.imageio.ImageIO.write(img, "jpg", baos);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    Message message = new Message(messageBody, baos.toByteArray());
                    //image converted to array of bytes (serializable)
                    try {
                        serverInterfaceRefrence.broadcastMessage(message, senderClient);
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                    userInput.clear();
                }

            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void recieve(Message message, boolean echo) throws RemoteException {

        scrollPane.vvalueProperty().bind(vbox.heightProperty());//to make pane scroll down automatically
        input = message.getMessageBody();
        Label msg = new Label();
        msg.setText(input);
        msg.setWrapText(true);
        msg.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        HBox senderBox = new HBox(5);
        HBox.setMargin(msg, new Insets(2, 5, 2, 2));

        //receiving image
        try {
            BufferedImage image = javax.imageio.ImageIO.read(new ByteArrayInputStream(message.getArray()));
            File outputfile = new File("saved.jpg");
            ImageIO.write(image, "jpg", outputfile);
            userimg = SwingFXUtils.toFXImage(image, null);
            imageCircle = new Circle(25, 25, 13);
            imageCircle.setFill(new ImagePattern(userimg));
            HBox.setMargin(imageCircle, new Insets(0, 0, 5, 0));
            senderBox.setPadding(new Insets(5, 5, 5, 5));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (echo) {
            msg.setStyle("-fx-background-color: PINK;-fx-padding: 2px;-fx-border-insets: 2px;-fx-background-insets: 2px;");
            senderBox.setAlignment(Pos.BOTTOM_RIGHT);
            senderBox.getChildren().addAll(msg, imageCircle);
        } else {
            msg.setStyle("-fx-background-color: LIGHTCYAN;-fx-padding: 2px;-fx-border-insets: 2px;-fx-background-insets: 2px;");
            senderBox.setAlignment(Pos.BOTTOM_LEFT);
            senderBox.getChildren().addAll(imageCircle, msg);
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                vbox.getChildren().add(senderBox);
            }
        });

    }

}
