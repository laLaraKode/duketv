package com.sodasmile.imageshow;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TestingTesting2 extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Text text = new Text(25, 25, "Hi!");
        text.setFill(Color.BLUEVIOLET);

        //Image image = new Image("raspberry.jpg", true);
        Image image = new Image("http://www.grannynet.co.uk/wp-content/uploads/2011/04/raspberry.jpg",
                                0, 500, // The image grows outside the imageViewPane
                                true, // preserveRatio
                                false, // smooth
                                true); // background

        ImageView imageView = new ImageView(image); // ImageView < Node
        imageView.setLayoutX(20); // setter offset inni imageViewPane
        imageView.setLayoutY(30);

        //imageView.setViewport(new Rectangle2D(40, 40, 200, 200)); // klipper ut en del av bildet.
//        imageView.setFitHeight(150); // Squeezer bildet sammen til str
//        imageView.setFitWidth(150);

        Pane imageViewPane = new Pane(imageView);
        imageViewPane.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, CornerRadii.EMPTY,Insets.EMPTY)));
        imageViewPane.setBorder(new Border(new BorderStroke(Color.AQUA, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(14))));
        imageViewPane.setPrefSize(400, 400);
        imageViewPane.setMaxSize(400, 400);
        imageViewPane.setMinSize(400, 400);

        Pane root = new Pane();

        root.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        root.getChildren().addAll(text, imageViewPane);

        Scene scene = new Scene(root, 800, 900, Color.GREEN);

        stage.setTitle(getClass().getSimpleName());
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);

        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
