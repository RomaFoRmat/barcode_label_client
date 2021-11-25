package gui.service;

import gui.application.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.regex.Pattern;


public class TextFieldService {
    public static void alert(String error) {
        Alert alert = new Alert(Alert.AlertType.WARNING, error, ButtonType.OK);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        alert.setTitle("Предупреждение");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.out.println(error);
        }
    }

    public static void setTextFieldNumeric(TextField textField, int maxLength) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (textField.getText() != null && !newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (textField.getText() != null && textField.getText().length() > maxLength) {
                String s = textField.getText().substring(0, maxLength);
                textField.setText(s);
            }
        });
    }

    public static void setFieldForStraight(TextField textField, int maxLength) {
        Pattern pattern = Pattern.compile("-?(\\d+\\.?\\d*)?");
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!pattern.matcher(newValue).matches()) textField.setText(oldValue);

            if (textField.getText() != null && textField.getText().length() > maxLength) {
                String s = textField.getText().substring(0, maxLength);
                textField.setText(s);
            }
        });
    }


}
