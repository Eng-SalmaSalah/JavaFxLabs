/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myfilechooserfx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;

/**
 *
 * @author salma
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField userSelection;
    @FXML
    private TreeView directoriesTree;
    @FXML
    private ListView filesList;
    @FXML
    private Button goBtn;

    ImageView rootIcon;
    ImageView fileIcon;
    DirectoryChooser directoryChooser;

    public FXMLDocumentController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userSelection.setText(new File("c:\\").getAbsolutePath());
        goBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("select your directory");
                File file = directoryChooser.showDialog(null);
                userSelection.setText(file.getAbsolutePath());

                if (file == null || !file.isDirectory()) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Could not open directory");
                    alert.setContentText("Select valid directory");

                    alert.showAndWait();
                } else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            directoriesTree.setRoot(getDirectoryChildren(file));
                        }
                    });
                }

            }
        });
        userSelection.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                File file = new File(userSelection.getText());

                if (file == null || !file.isDirectory()) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Could not open directory");
                    alert.setContentText("Select valid directory");

                    alert.showAndWait();
                } else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            directoriesTree.setRoot(getDirectoryChildren(file));
                        }
                    });
                }

            }
        });
    }

    public TreeItem<String> getDirectoryChildren(File directory) {
        try {
            rootIcon = new ImageView(new Image(new FileInputStream("C:\\Users\\salma\\Documents\\NetBeansProjects\\MyFileChooserFx\\src\\icons\\folder-icon.png")));
            rootIcon.setFitWidth(20.0);
            rootIcon.setFitHeight(20.0);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        TreeItem<String> root = new TreeItem<String>(directory.getName(), rootIcon);
        for (File child : directory.listFiles()) {
            if (child.isDirectory()) {
                root.getChildren().add(getDirectoryChildren(child));
            } else {
                try {
                    fileIcon = new ImageView(new Image(new FileInputStream("C:\\Users\\salma\\Documents\\NetBeansProjects\\MyFileChooserFx\\build\\classes\\icons\\file.png")));
                    fileIcon.setFitWidth(20.0);
                    fileIcon.setFitHeight(20.0);
                    root.getChildren().add(new TreeItem<String>(child.getName(), fileIcon));
                    root.expandedProperty().addListener((observable) -> {
                        setListView(filesList, child);
                    });

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        return root;
    }

    private void setListView(ListView listView, File selectedFile) {

        ObservableList<File> filesObservableList = FXCollections.observableArrayList(selectedFile.listFiles());
        Platform.runLater(() -> {
            listView.setItems(filesObservableList);
            listView.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
                @Override
                public ListCell<File> call(ListView<File> param) {
                    return new ListCell<File>() {
                        @Override
                        protected void updateItem(File file, boolean empty) {

                            super.updateItem(file, empty);
                            if (!empty) {

                                if (file.isDirectory()) {
                                    HBox hbox = new HBox();
                                    hbox.getChildren().addAll(new ImageView(new Image(getClass().getResourceAsStream("folder.png"))), new Label(file.getName()));
                                    setGraphic(hbox);
                                } else {
                                    HBox hbox = new HBox();
                                    hbox.getChildren().addAll(new ImageView(new Image(getClass().getResourceAsStream("file.png"))), new Label(file.getName()));
                                    setGraphic(hbox);
                                }

                            }

                        }
                    };
                }
            });
        });

    }
}
