package gui.service;

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
}
