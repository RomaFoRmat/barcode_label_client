package gui.service;

import gui.application.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class TextFieldService {
    public static void alert(String error) {
        Alert alert = new Alert(Alert.AlertType.WARNING, error, ButtonType.OK);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.out.println(error);
        }
    }

}
