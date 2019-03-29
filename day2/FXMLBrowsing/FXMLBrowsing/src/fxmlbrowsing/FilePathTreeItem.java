/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmlbrowsing;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.stream.FileImageInputStream;


public class FilePathTreeItem extends TreeItem {

    public static Image folderCollapseImage = new Image(ClassLoader.getSystemResourceAsStream("folder.png"));
    public static Image fileImage = new Image(ClassLoader.getSystemResourceAsStream("file.png"));
    private String fullPath;
    public static Image folderExpandImage=new Image(ClassLoader.getSystemResourceAsStream("folder.png"));

    public String getFullPath() {
        return (this.fullPath);
    }
    private boolean isDirectory;

    public boolean isDirectory() {
        return (this.isDirectory);
    }

    public FilePathTreeItem(Path file) {
        super(file.toString());
        this.fullPath = file.toString();
        //test if this is a directory and set the icon
        if (Files.isDirectory(file)) {
            this.isDirectory = true;
            this.setGraphic(new ImageView(folderCollapseImage));
        } else {
            this.isDirectory = false;
            this.setGraphic(new ImageView(fileImage));
            //if you want different icons for different file types this is where you'd do it
        }

    }
}
