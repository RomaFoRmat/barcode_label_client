package gui.model;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * Model для формирования бирки
 * */
@Data
@AllArgsConstructor
public class FieldModel {
    private TextField textField;
    private CheckBox checkBox;
    private String type;
    private CellStyleOption cellStyleOption;
}
