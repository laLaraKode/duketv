package com.sodasmile.imageshow;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TestingTesting extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Text text = new Text(25, 25, "Hi!");
        text.setFill(Color.LIGHTYELLOW);

        Rectangle r = new Rectangle(25,25,250,250);
        r.setFill(Color.BLUE);

        // Group root = new Group(); // Group < Parent < Node < Object
        //Region root = new Region(); // Region < Parent < Node < Object
        Pane root = new Pane(); // Pane < Region < Parent < Node < Object

        root.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        root.getChildren().addAll(text, r);

        Scene scene = new Scene(root, 200, 300, Color.GREEN);

        stage.setTitle(getClass().getSimpleName());
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);


        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
