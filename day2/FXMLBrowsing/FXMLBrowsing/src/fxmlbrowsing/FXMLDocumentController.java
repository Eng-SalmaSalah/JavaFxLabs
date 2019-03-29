/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlbrowsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import javafx.stage.FileChooser;
import javafx.util.Callback;


public class FXMLDocumentController implements Initializable {

    public FXMLDocumentController() {
    }

    @FXML
    private TextField searchTf;

    @FXML
    private Button searchButton;

    @FXML
    private Label browseLabel;

    @FXML
    private TreeView<String> directoryTreeView;

    @FXML
    private ListView directoryListView;

    Node folderIcon = null;
    Node fileIcon = null;
    File selectedFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            folderIcon = new ImageView(new Image(new FileInputStream("folder.png")));
            searchTf.setOnAction((event) -> {
                selectedFile = new File(searchTf.getText());
                TreeItem<String> rootItem = getTree(selectedFile);
                directoryTreeView.setRoot(rootItem);
            });
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void browseHandler(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        selectedFile = directoryChooser.showDialog(null);
        TreeItem<String> rootItem = getTree(selectedFile);
        directoryTreeView.setRoot(rootItem);
    }

    public TreeItem<String> getTree(File file) {
        TreeItem root = new TreeItem<String>(file.getName(), new ImageView(new Image(getClass().getResourceAsStream("folder.png"))));
        File[] listOfFiles = file.listFiles();
        for (File fileItr : listOfFiles) {

            Platform.runLater(() -> {
                TreeItem<String> item;
                if (fileItr.isDirectory()) {

                    item = getTree(fileItr);
                } else {

                    item = new TreeItem<String>(fileItr.getName(), new ImageView(new Image(getClass().getResourceAsStream("file.png"))));
                }
                root.getChildren().add(item);
                item.expandedProperty().addListener((observable) -> {
                    setListView(directoryListView, fileItr);
                });
            });
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
