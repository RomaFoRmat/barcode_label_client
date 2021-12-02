package gui.controller;

import com.jfoenix.controls.JFXButton;
import gui.application.Main;
import gui.model.Constants;
import gui.service.TextFieldService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    public AboutDialogController aboutDialogController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader aboutLoader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
            aboutLoader.load();
            aboutDialogController = aboutLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void changeUserAction() throws IOException {
        stage = (Stage) btnExit.getScene().getWindow();
        stage.close();

        System.out.println("Выход из системы: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + Constants.FIO);

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginDialog.fxml"));
        stage.setTitle("Вход в SCAN SPOOLS");
        stage.setScene(new Scene(root, 500, 304));
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void exitAction() {
        TextFieldService.exitConfirmationAlert("Вы действительно хотите выйти из программы?",btnExit);
    }

    @FXML
    private void openProjectStart2() {
        try {
            Runtime run = Runtime.getRuntime();
            run.exec("C:\\Program Files (x86)\\LaboratoryResearches2\\ProjectStart2.exe");
        } catch (IOException e) {
            System.out.println(e.getMessage() + " либо данная программа не установлена на данном ПК");
            TextFieldService.alertError("Не удается найти указанный файл! \nЛибо данная программа не установлена на вашем ПК!");
        }
        System.out.println("Открыть: Лабораторные испытания СтПЦ-2");
    }

    @FXML
    private void aboutAction() {
        aboutDialogController.show();

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
