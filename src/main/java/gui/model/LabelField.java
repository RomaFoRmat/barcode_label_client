package gui.model;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Model для выборки компонентов(CheckBox и TextField) в соответствии с шаблоном этикетки TemplatesLabel
 * */
@Data
@AllArgsConstructor
public class LabelField {
    private CheckBox labelCheckBox;
    private TextField labelTextField;
}
