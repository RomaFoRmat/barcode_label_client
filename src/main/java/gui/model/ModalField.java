package gui.model;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModalField {
    private CheckBox checkBox;
    private TextField textField;
    private Label label;
}
