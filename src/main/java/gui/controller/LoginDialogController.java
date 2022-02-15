package gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import gui.application.AppProperties;
import gui.application.Main;
import gui.model.Constants;
import gui.model.Personals;
import gui.repository.PersonalsRepository;
import gui.util.TextFieldUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ResourceBundle;

public class LoginDialogController implements Initializable {

    public static final Logger LOGGER = LogManager.getLogger(LoginDialogController.class);
    public Stage stage;
    @FXML
    public JFXPasswordField loginUserTextField;
    @FXML
    public JFXButton loginButton;
    public ScanController scanController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextFieldUtil.setTextFieldNumeric(loginUserTextField, 12);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scanSpool.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanController = loader.getController();
    }

    @FXML
    public void login() throws UnknownHostException {
        if (!loginUserTextField.getText().equals("")) {
            String password = loginUserTextField.getText();
            List<Personals> personalsList = PersonalsRepository.findByPassword(password);

            if (personalsList != null && personalsList.isEmpty()) {
                loginUserTextField.clear();
                TextFieldUtil.alertWarning("Неверный пароль!");
                LOGGER.error("Login failed: {}", InetAddress.getLocalHost());
                return;
            }

            Personals personal = personalsList.get(0);
            Constants.FIO_VIEW = personal.getFio() + " (№:" + personal.getPersonnelNumber() + ")";
            Constants.FIO = personal.getFio();
            Constants.ID_PERSONALS = personal.getIdPersonal();
            Constants.IP_ADDRESS = InetAddress.getLocalHost().getHostAddress().toString();
            AppProperties.personals = personal;
            LOGGER.info("User logged in: {}/{}({}); {}",
                    Constants.FIO_VIEW, personal.getGroupsOfPersonal().getNameGroup(),
                    personal.getGroupsOfPersonal().getIdGroup(), InetAddress.getLocalHost());
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            show();


        } else if (loginUserTextField.getText().isEmpty()) {
            TextFieldUtil.alertWarning("Введите пароль!");
        }
    }

    public void show() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/scanSpool.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("АРМ ЛИНИИ ИНСПЕКЦИИ");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        stage.setScene(new Scene(root, 900, 678));
        stage.setResizable(false);
        stage.requestFocus();
        stage.show();
    }

    public void loginByKey(KeyEvent keyEvent) throws UnknownHostException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            login();
        }
    }

}
