package gui.controller;

import gui.application.AppProperties;
import gui.application.Main;
import gui.util.TextFieldUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutDialogController implements Initializable {

    @FXML
    private AnchorPane aboutPane;
    @FXML
    private ImageView aboutImageView;
    @FXML
    private ImageView springImageView;
    @FXML
    private ImageView javaImageView;
    @FXML
    private ImageView sceneBuilderImageView;
    @FXML
    private ImageView spoolImageView;
    @FXML
    private Label lblVersion;
    @FXML
    private Hyperlink linkManual;
    private Stage stage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stage = new Stage();
        Image springImage = new Image(Main.class.getResourceAsStream("/icon/springBootLogo.png"));
        Image javaImage = new Image(Main.class.getResourceAsStream("/icon/java8.png"));
        Image aboutImage = new Image(Main.class.getResourceAsStream("/icon/aboutLogo.png"));
        Image sceneBuilderImage = new Image(Main.class.getResourceAsStream("/icon/SceneBuilderLogo.png"));
        Image spoolImage = new Image(Main.class.getResourceAsStream("/icon/spool.png"));
        springImageView.setImage(springImage);
        javaImageView.setImage(javaImage);
        aboutImageView.setImage(aboutImage);
        sceneBuilderImageView.setImage(sceneBuilderImage);
        spoolImageView.setImage(spoolImage);
        stage.setScene(new Scene(aboutPane));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("О программе");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        lblVersion.setText(AppProperties.getVersion());
        usersManual();
    }

    @FXML
    private void usersManual()  {
        if (linkManual.isVisited()) {
            try {
                File pdfFile = new File(getClass().getResource("/template/manual.pdf").toURI());
                if (pdfFile.exists()) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(pdfFile);
                    } else {
                        System.out.println("AWT Desktop is not supported!");
                    }
                } else {
                    TextFieldUtil.alertError("Руководство пользователя не найдено!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public void show() {
        stage.showAndWait();
    }
}
