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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SideMenuController implements Initializable {

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

    public static final Logger LOGGER = LogManager.getLogger(SideMenuController.class);

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
        LOGGER.info("User change: {}; {}", Constants.FIO_VIEW, InetAddress.getLocalHost());

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginDialog.fxml"));
        stage.setTitle("Вход в SCAN SPOOLS");
        stage.setScene(new Scene(root, 500, 304));
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void exitAction() throws UnknownHostException {
        TextFieldService.exitConfirmationAlert("Вы действительно хотите выйти из программы?", btnExit);
    }

    @FXML
    private void openProjectStart2() throws UnknownHostException {
        try {
            Runtime run = Runtime.getRuntime();
            run.exec("C:\\Program Files (x86)\\LaboratoryResearches2\\ProjectStart2.exe");
            LOGGER.info("Open Lab STPC-2: {}; {}", Constants.FIO_VIEW, InetAddress.getLocalHost());
        } catch (IOException e) {
            LOGGER.error("{} - {}; {}", e.getMessage(),Constants.FIO_VIEW,InetAddress.getLocalHost());
            TextFieldService.alertError("Не удается найти указанный файл! \nЛибо данная программа не установлена на вашем ПК!");
        }

    }

    @FXML
    private void aboutAction() throws UnknownHostException {
        aboutDialogController.show();
        LOGGER.info("About the program: {}; {}", Constants.FIO_VIEW, InetAddress.getLocalHost());
    }
}
