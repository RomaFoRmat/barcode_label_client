package gui.controller;

import com.jfoenix.controls.JFXButton;
import gui.application.Main;
import gui.model.Constants;
import gui.util.TextFieldUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.ResourceBundle;

import static gui.model.Constants.ID_GROUP;

public class SideMenuController implements Initializable {

    @FXML
    private VBox vbox;
    @FXML
    private JFXButton btnLab2;
    @FXML
    private JFXButton btnChangeUser;
    @FXML
    private JFXButton btnAbout;
    public TemplatesLabelsController templatesLabelsController;
    @FXML
    private JFXButton btnTemplates;
    private Stage stage;
    public AboutDialogController aboutDialogController;
    @FXML
    private JFXButton btnExit;
    //    private final String osArch = System.getProperty("os.arch");
    private final String osArch = System.getProperty("sun.arch.data.model");
    private final String x64 = "C:\\Program Files (x86)\\LaboratoryResearches2\\ProjectStart2.exe";
    private final String x86 = "C:\\Program Files\\LaboratoryResearches2\\ProjectStart2.exe";
    public static final Logger LOGGER = LogManager.getLogger(SideMenuController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        btnTemplates.setDisable(ID_GROUP != 11661L);
        FXMLLoader aboutLoader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
        FXMLLoader templatesLoader = new FXMLLoader(getClass().getResource("/fxml/templatesLabels.fxml"));
        try {
            aboutLoader.load();
            templatesLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        aboutDialogController = aboutLoader.getController();
        templatesLabelsController = templatesLoader.getController();

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
        TextFieldUtil.exitConfirmationAlert("Вы действительно хотите выйти из программы?", btnExit);
    }

    @FXML
    private void openProjectStart2() throws IOException {
//            String osArch2 = System.getProperty("sun.arch.data.model"); //64,32
        Runtime run = Runtime.getRuntime();
//        Defining bit depth JVM:
        if (osArch.equals("64") && (new File(x64)).exists()) {
            run.exec(x64);
            LOGGER.info("Open Lab STPC-2: {}; {}", Constants.FIO_VIEW, InetAddress.getLocalHost());
        } else if (osArch.equals("32") && (new File(x86)).exists()) {
            run.exec(x86);
            LOGGER.info("Open Lab STPC-2: {}; {}", Constants.FIO_VIEW, InetAddress.getLocalHost());
        } else {
            LOGGER.error("{} - {}; {}", "Cannot run program", Constants.FIO_VIEW, InetAddress.getLocalHost());
            alertLink();

        }
    }

    @FXML
    private void aboutAction() throws UnknownHostException {
        aboutDialogController.show();
        LOGGER.info("About the program: {}; {}", Constants.FIO_VIEW, InetAddress.getLocalHost());
    }

    @FXML
    private void templatesAction() throws UnknownHostException {
        templatesLabelsController.show();
        LOGGER.info("Open templates: {}; {}", Constants.FIO_VIEW, InetAddress.getLocalHost());
    }

    private void alertLink() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        alert.setHeaderText("Не удается найти указанный файл! \nЛибо данная программа не установлена на вашем ПК!");
        alert.setTitle("Внимание!");
        alert.getDialogPane().setGraphic(new ImageView("/icon/error2.png"));
        FlowPane fp = new FlowPane();
        Hyperlink link = new Hyperlink("Скачать Лабораторные испытания СтПЦ-2");
        link.setStyle("-fx-font-size: 16; -fx-text-fill: 'RoyalBlue'; -fx-font-family: 'Comic Sans MS';");
        fp.getChildren().add(link);
        link.setOnAction((evt) -> {
            alert.close();
            try {
                Desktop.getDesktop().browse(new URI("http://micora/Lab_isp/"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
        alert.getDialogPane().contentProperty().set(fp);
        alert.showAndWait();
    }
}
