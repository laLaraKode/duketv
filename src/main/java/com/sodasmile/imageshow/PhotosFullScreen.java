package com.sodasmile.imageshow;

import java.io.File;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.ObjectBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class PhotosFullScreen extends Group {
    private Stage stage;
    private ImageView imageView1 = new ImageView();
    private ImageView imageView2 = new ImageView();
    private HBox toolBar = new HBox(10);
    private Button back;
    private Button forward;
    private List<File> photos;
    private int index;
    private Timeline slideShowTimeline;
    private Timeline transitionTimeline;
    private Timeline hideToolbarTimeline;

    public PhotosFullScreen() {
        stage = new Stage(StageStyle.UNDECORATED);
        Scene scene = new Scene(this);
        // Load Theme CSS
        scene.getStylesheets().add(PhotosFullScreen.class.getResource("/DukePadTheme.css").toExternalForm());
        scene.setFill(Color.BLACK);
        stage.setScene(scene);

        back = new Button();
        back.setBackground(new Background(new BackgroundImage(
                new Image(PhotosFullScreen.class.getResource("/gfx/fs-left.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
        )));
        back.setPrefSize(55, 54);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gotoPic(index - 1, false);
            }
        });

        final Background playBackground = new Background(new BackgroundImage(
                new Image(PhotosFullScreen.class.getResource("/gfx/fs-play.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
        ));
        final Background pauseBackground = new Background(new BackgroundImage(
                new Image(PhotosFullScreen.class.getResource("/gfx/fs-pause.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
        ));
        final ToggleButton play = new ToggleButton();
        play.setPrefSize(55, 54);
        play.backgroundProperty().bind(new ObjectBinding<Background>() {
            {
                bind(play.selectedProperty());
            }

            @Override
            protected Background computeValue() {
                return play.isSelected() ? pauseBackground : playBackground;
            }
        });
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (slideShowTimeline != null) {
                    slideShowTimeline.stop();
                    slideShowTimeline = null;
                }
                if (play.isSelected()) {
                    slideShowTimeline = new Timeline(
                            new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    int nextIndex = index + 1;
                                    if (nextIndex >= photos.size()) nextIndex = 0;
                                    gotoPic(nextIndex, true);
                                }
                            })
                    );
                    slideShowTimeline.setCycleCount(Timeline.INDEFINITE);
                    slideShowTimeline.play();
                }
            }
        });

        Button close = new Button();
        close.setBackground(new Background(new BackgroundImage(
                new Image(PhotosFullScreen.class.getResource("/gfx/fs-close.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
        )));
        close.setPrefSize(55, 54);
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (slideShowTimeline != null) {
                    slideShowTimeline.stop();
                    slideShowTimeline = null;
                }
                if (transitionTimeline != null) {
                    transitionTimeline.stop();
                    transitionTimeline = null;
                }
                stage.close();
            }
        });

        forward = new Button();
        forward.setBackground(new Background(new BackgroundImage(
                new Image(PhotosFullScreen.class.getResource("/gfx/fs-right.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
        )));
        forward.setPrefSize(55, 54);
        forward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gotoPic(index + 1, false);
            }
        });

        toolBar.getChildren().addAll(back, play, close, forward);
        toolBar.setCache(true);
        getChildren().addAll(imageView1, imageView2, toolBar);

        hideToolbarTimeline = new Timeline(
                new KeyFrame(Duration.seconds(5), new KeyValue(toolBar.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(5.5), new KeyValue(toolBar.opacityProperty(), 0))
        );
        hideToolbarTimeline.playFromStart();
        addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hideToolbarTimeline.stop();
                toolBar.setOpacity(1);
                hideToolbarTimeline.playFromStart();
            }
        });
    }

    @Override
    protected void layoutChildren() {
        final int w = (int) stage.getWidth();
        final int h = (int) stage.getHeight();
        final int tbw = (int) (toolBar.prefWidth(-1) + .5);
        final int tbh = (int) (toolBar.prefHeight(-1) + .5);
        imageView1.setFitHeight(w);
        //imageView1.setFitWidth(h);
        imageView1.setPreserveRatio(true);
        imageView2.setFitHeight(w);
        //imageView2.setFitWidth(h);
        imageView2.setPreserveRatio(true);
        System.out.printf("FitHeight %s FitWidth %s\n", w, h);
        toolBar.resizeRelocate((w - tbw) / 2, h - 20 - tbh, tbw, tbh);
    }

    public void show(List<File> photos, int index) {
        this.photos = photos;
        this.index = index;
        gotoPic(index, true);
        Rectangle2D mainScreen = Screen.getPrimary().getBounds();
        stage.setX(mainScreen.getMinX());
        stage.setY(mainScreen.getMinY());
        stage.setWidth(mainScreen.getWidth());
        stage.setHeight(mainScreen.getHeight());
        stage.show();
    }

    public void gotoPic(int index, boolean animate) {
        this.index = index;
        back.setDisable(index <= 0);
        forward.setDisable(index > photos.size() - 2);
        File photo = photos.get(index);
        if (transitionTimeline != null) {
            transitionTimeline.stop();
            transitionTimeline = null;
        }
        if (!animate) {
            setImage(imageView1, getNullsafeImageFromFile(photo));
            setImage(imageView2, null);
        } else {
            setImage(imageView2, imageView1.getImage());
            imageView2.setOpacity(1);
            setImage(imageView1, getNullsafeImageFromFile(photo));
            transitionTimeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(imageView2.opacityProperty(), 1)),
                    new KeyFrame(Duration.seconds(.5), new KeyValue(imageView2.opacityProperty(), 0))
            );
            transitionTimeline.play();
        }
    }

    private void setImage(ImageView imageView, Image image) {
        if (image != null) {
            double width = image.getRequestedWidth();
            double height = image.getHeight();

            double spacing = 1680 - width;
            double margin = spacing / 2;
            System.out.println("margin = " + margin);
            imageView.setX(3);
            imageView.setY(4);

        }
        imageView.setImage(image);
    }

    private Image getNullsafeImageFromFile(File imageFile) {
        return imageFile == null ? null : new Image(imageFile.toURI().toString(), false);
    }
}
