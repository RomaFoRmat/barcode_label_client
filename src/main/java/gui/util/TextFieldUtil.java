package gui.util;

import gui.application.Main;
import gui.controller.SideMenuController;
import gui.model.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.awt.*;
import java.awt.Label;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.regex.Pattern;


public class TextFieldUtil {
    public static void alertWarning(String warning) {
        Alert alert = new Alert(Alert.AlertType.WARNING, warning, ButtonType.OK);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        alert.setTitle("Внимание");
        alert.setHeaderText(null);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.out.println(warning);
        }
    }

    public static void alertInformation(String information) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, information, ButtonType.OK);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.out.println(information);
        }
    }

    public static void exitConfirmationAlert(String confirmation, Button button) throws UnknownHostException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmation);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        alert.setTitle("Выход?");
        alert.setHeaderText(null);
        ButtonType ok = new ButtonType("ДА");
        ButtonType cancel = new ButtonType("НЕТ");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ok, cancel);
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ok) {
            stage = (Stage) button.getScene().getWindow();
            stage.close();
            SideMenuController.LOGGER.info("Sign Out: {}; {}", Constants.FIO_VIEW, InetAddress.getLocalHost());
        }
        if (alert.getResult() == ButtonType.YES) {
            System.out.println(confirmation);
        }
    }

    public static void alertError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR, error, ButtonType.OK);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        alert.setTitle("Ошибка");
        alert.getDialogPane().setGraphic(new ImageView("/icon/error2.png"));
        alert.setHeaderText(null);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.out.println(error);
        }

    }

    public static void alertHelp(String information) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, information, ButtonType.OK);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        alert.setTitle("Информация о добавлении/изменении/удаления шаблона");
        alert.getDialogPane().setGraphic(new ImageView("/icon/helpInformation.png"));
        alert.setHeaderText(null);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.out.println(information);
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

    public static void setLimitingFields(TextField textField, int maxNumber) {
        textField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.matches("[0-9]+$")) {
                        int number = Integer.parseInt(newValue);
                        if (number < maxNumber) {
                            return;
                        }
                        textField.setText(oldValue);
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

//    public static void setComboBoxNumeric(ComboBox comboBox) {
//        comboBox.promptTextProperty().addListener((observable, oldValue, newValue) -> {
//            if (comboBox.getValue() != null && !newValue.matches("\\d*")) {
//                comboBox.setValue(newValue.replaceAll("[^\\d]", ""));
//            }
//        });
//    }
}
