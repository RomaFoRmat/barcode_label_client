package gui.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        setProperties();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/scan_spool.fxml"));
        primaryStage.setTitle("АРМ ЛИНИИ ИНСПЕКЦИИ");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        primaryStage.setResizable(false);
        primaryStage.requestFocus();
        primaryStage.show();

    }

    @Override
    public void stop() {
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }


    private void setProperties() {
        try (InputStream inputStream = new FileInputStream("src/main/resources/application.properties")) {
            Properties property = new Properties();
            // set the properties value
            property.load(inputStream);
            String host = property.getProperty("pack.host");
            if (host.isEmpty()) throw new IllegalArgumentException("Set host address in application.properties");
            AppProperties.setHost(property.getProperty("pack.host", ""));
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
