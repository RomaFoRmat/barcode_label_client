package gui.util;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class NodeUtils {

    public static boolean checkSelectedFields(Pane... panes) {
        for (Pane pane : panes) {
            for (Node node : pane.getChildren()) {
                if (node instanceof CheckBox && ((CheckBox) node).isSelected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkEmptyFields(Pane... panes) {
        for (Pane pane : panes) {
            for (Node node : pane.getChildren()) {
                if (node instanceof TextField && !((TextField) node).getText().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkSelectedAndEmptyFields(Pane checkBoxPane0, Pane checkBoxPane1,
                                                      Pane textFieldPane0, Pane textFieldPane1) {
        if(checkBoxPane0.getChildren().size() != textFieldPane0.getChildren().size() ||
                checkBoxPane1.getChildren().size() != textFieldPane1.getChildren().size()){
            throw new IllegalArgumentException("Panes are not of the same size");
        }
        for(int i = 0; i < checkBoxPane0.getChildren().size(); i++){
            CheckBox checkBox = (CheckBox) checkBoxPane0.getChildren().get(i);
            TextField textField = (TextField) textFieldPane0.getChildren().get(i);
            if (checkBox.isSelected() && !textField.getText().isEmpty()){
                return true;
            }
        }
        for(int i = 0; i < checkBoxPane1.getChildren().size(); i++){
            CheckBox checkBox = (CheckBox) checkBoxPane1.getChildren().get(i);
            TextField textField = (TextField) textFieldPane1.getChildren().get(i);
            if (checkBox.isSelected() && !textField.getText().isEmpty()){
                return true;
            }
        }
        return false;
    }
}
