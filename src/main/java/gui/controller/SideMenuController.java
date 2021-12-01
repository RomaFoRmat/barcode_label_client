package gui.controller;

import com.jfoenix.controls.JFXButton;
import gui.application.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.bouncycastle.util.encoders.UrlBase64;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SideMenuController implements Initializable {

    LoginDialogController loginDialogController;
    @FXML
    private VBox vbox;
    @FXML
    private JFXButton btnLab2;
    @FXML
    private JFXButton btnChangeUser;
    @FXML
    private JFXButton btnAbout;
    @FXML
    private JFXButton btnExit;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void changeUserAction() throws IOException {
        stage = (Stage) btnExit.getScene().getWindow();
        stage.close();

        System.out.println("Выход из системы: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginDialog.fxml"));
        stage.setTitle("Вход в SCAN SPOOLS");
        stage.setScene(new Scene(root, 500, 304));
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void exitAction() {
        stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void openProjectStart2() throws IOException {
        Runtime.getRuntime().exec("C:\\Program Files (x86)\\LaboratoryResearches2\\ProjectStart2.exe");
        System.out.println("Открыть: Лабораторные испытания СтПЦ-2");
    }

//    private void switchPane(String pane) {
//        ScanController.temporaryPane.getChildren().clear();
//        try {
//            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(pane));
//            ObservableList<Node> elements = anchorPane.getChildren();
//            ScanController.temporaryPane.getChildren().setAll(elements);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    @FXML
//    void aboutTheProgramAction() {
//
//    }
//
//    @FXML
//    void changeUserAction() {
//
//
//    }

//    @FXML
//    void exitAction() {
//        stage = (Stage) btnExit.getScene().getWindow();
//        stage.close();
//    }
//

}
