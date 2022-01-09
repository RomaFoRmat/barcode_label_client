package gui.service;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.List;

public class Utils {
    public static boolean checkSelectedFields(List<Pane> pane) {
        for (Node node : pane) {
            if (node instanceof CheckBox && ((CheckBox) node).isSelected()) {
                return true;
            } else if (node instanceof TextField && !((TextField) node).getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
