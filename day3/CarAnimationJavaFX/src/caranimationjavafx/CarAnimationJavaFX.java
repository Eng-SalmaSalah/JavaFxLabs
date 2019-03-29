/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caranimationjavafx;

import java.io.File;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author salma
 */
public class CarAnimationJavaFX extends Application {

    Image image;
    int currentRate = 1;

    @Override
    public void start(Stage stage) throws Exception {
        File resource = new File("C:\\Users\\salma\\Documents\\NetBeansProjects\\CarAnimationJavaFX\\src\\caranimationjavafx\\beep.wav");
        AudioClip beeb=new AudioClip(resource.toURI().toString());
        Group group = new Group();
        ImageView carImageView = new ImageView();
        File imageFile = new File("src/caranimationjavafx/car1.png");
        image = new Image(imageFile.toURI().toString());
        carImageView.setImage(image);
        Path path = new Path();
        path.setVisible(true);
        //path.setFill(Color.PINK);
        path.setStrokeWidth(15);
        path.setStroke(Color.BLACK);
        path.getElements().add(new MoveTo(300, 200));

        path.getElements().add(new LineTo(600, 200));
        path.getElements().add(new LineTo(780, 500));
        path.getElements().add(new LineTo(1080, 500));
        path.getElements().add(new CubicCurveTo(1080, 500, 1350, 350, 1080, 200));
        path.getElements().add(new LineTo(780, 200));
        path.getElements().add(new LineTo(600, 500));
        path.getElements().add(new LineTo(300, 500));
        path.getElements().add(new CubicCurveTo(300, 500, 70, 350, 300, 200));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(20000));
        pathTransition.setNode(carImageView);
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.play();
        group.getChildren().add(path);
        group.getChildren().add(carImageView);
        carImageView.setOnMouseClicked(m -> {

            if (currentRate == -1) {
                pathTransition.setRate(1);
                currentRate = 1;
            }

            else if (currentRate == 1) {
                pathTransition.setRate(-1);
                currentRate = -1;
            }
            beeb.play(1.0);

        });
        Scene scene = new Scene(group, 1400, 800);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
