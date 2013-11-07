package com.sodasmile.imageshow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ImageShow extends Application {
    private Stage primaryStage;
    private Scene scene;

    private final List<File> photos = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        // create main window and show splash screen
        this.primaryStage = primaryStage;
        Image image = new Image(ImageShow.class.getResource("/raspberry.jpg").toExternalForm(), false);
        ImageView raspberryImageView = new ImageView(image);
        raspberryImageView.setX(1280 / 2 - image.getWidth() / 2);
        raspberryImageView.setY(800 / 2 - image.getHeight() / 2);
        Text loadingText = new Text("Loading...");
        loadingText.setFont(Font.font("System", 24));
        loadingText.setFill(Color.rgb(0, 0, 0, 0.8));
        loadingText.setX(30);
        loadingText.setY(50);
        scene = new Scene(new Group(raspberryImageView, loadingText), 1280, 800);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.initStyle(StageStyle.TRANSPARENT); // bruk denne tilslutt
        //primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

        showPhotosInFullScreen();
    }

    private void showPhotosInFullScreen() {
        File folder = new File("./src/main/resources/images/");
        for (final File file : folder.listFiles()) {
            photos.add(file);
        }
        PhotosFullScreen photosFullScreen = new PhotosFullScreen();
        final int index = photos.size() - 1;
        photosFullScreen.show(photos, index);
    }

    public static void main(String[] args) {
        launch(ImageShow.class, args);
    }
}
