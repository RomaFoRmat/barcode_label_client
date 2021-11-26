package gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import gui.application.AppProperties;
import gui.application.Main;
import gui.model.Constants;
import gui.model.Personals;
import gui.repository.PersonalsRepository;
import gui.service.TextFieldService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ResourceBundle;

public class LoginDialogController implements Initializable {
    public Stage stage;
    @FXML
    public JFXPasswordField loginUserTextField;
    @FXML
    public JFXButton loginButton;

    public ScanController scanController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextFieldService.setTextFieldNumeric(loginUserTextField, 12);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scan_spool.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanController = loader.getController();
    }

    @FXML
    public void login() {
        if (!loginUserTextField.getText().equals("")) {
            String password = loginUserTextField.getText();
            List<Personals> personalsList = PersonalsRepository.findByPassword(password);

            if (personalsList != null && personalsList.isEmpty()) {
                loginUserTextField.clear();
                TextFieldService.alert("Неверный пароль!");
                return;
            }

            Personals personal = personalsList.get(0);
            System.out.println(personal);
            Constants.FIO = personal.getFio() + " (№:" + personal.getPersonnelNumber() + ")";
            AppProperties.personals = personal;
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            show();


        } else if (loginUserTextField.getText().isEmpty()) {
            TextFieldService.alert("Введите пароль!");
        }
    }

    public void show() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/scan_spool.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("АРМ ЛИНИИ ИНСПЕКЦИИ");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        stage.setScene(new Scene(root, 900, 680));
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
