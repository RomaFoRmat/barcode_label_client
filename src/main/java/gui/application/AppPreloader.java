package gui.application;

import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppPreloader extends Preloader {

    private Stage preloadStage;

    @Override
    public void start(Stage primaryStage) {
            this.preloadStage = primaryStage;
            VBox pane = new VBox();
            ImageView imageView = new ImageView();
            Image bmzImage = new Image("File:src\\main\\resources\\icon\\logoBMZ.png");
            imageView.setImage(bmzImage);
            JFXSpinner preloadSpinner = new JFXSpinner(-1);
            pane.getChildren().add(imageView);
            pane.getChildren().add(preloadSpinner);
            pane.setStyle("-fx-background-color: transparent");
            Scene preloadScene = new Scene(pane, 200, 180, Color.TRANSPARENT);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setScene(preloadScene);
            primaryStage.getIcons().add(new Image(AppPreloader.class.getResourceAsStream("/icon/logoBMZ.png")));
            primaryStage.show();
    }
    @Override
    public void handleApplicationNotification(PreloaderNotification pn) {
        if (pn instanceof StateChangeNotification) {
             preloadStage.hide();
        }
    }
}
