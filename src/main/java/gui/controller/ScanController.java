package gui.controller;



import gui.model.TestLabel;
import gui.repository.TestLabelRepository;
import gui.service.DateUtil;
import gui.service.TextFieldService;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.util.List;


public class ScanController {

    @FXML
    private AnchorPane anchorPaneMain;

    @FXML
    private Label lbl_pr400;

    @FXML
    private Label lbl_pr1;

    @FXML
    private Label lbl_pr2;

    @FXML
    private Label lbl_pr3;

    @FXML
    private Label lbl_pr4;

    @FXML
    private Label lbl_pr5;

    @FXML
    private Label lbl_pr_avg;

    @FXML
    private Label lbl_torsion;

    @FXML
    private Label lbl_defect;

    @FXML
    private Label lbl_torsRope;

    @FXML
    private Label lbl_prRope;

    @FXML
    private TextField straightforwardness400;

    @FXML
    private TextField straightforwardness1;

    @FXML
    private TextField straightforwardness2;

    @FXML
    private TextField straightforwardness3;

    @FXML
    private TextField straightforwardness4;

    @FXML
    private TextField straightforwardness5;

    @FXML
    private TextField straightforwardnessAvg;

    @FXML
    private TextField torsion;

    @FXML
    private TextField codeDefect;

    @FXML
    private TextField torsRope;

    @FXML
    private TextField straightforwardnessRope;

    @FXML
    private Label lbl_type;

    @FXML
    private Label lbl_code;

    @FXML
    private Label lbl_construct;

    @FXML
    private Label lbl_date;

    @FXML
    private Label lbl_LR;

    @FXML
    private Label lbl_machine;

    @FXML
    private Label lbl_part;

    @FXML
    private Label lbl_lot;

    @FXML
    private Label lbl_length;

    @FXML
    private Label lbl_welds;

    @FXML
    private Label lbl_rope;

    @FXML
    private Button btn_labelForm;

    @FXML
    private Label lbl_spool;

    @FXML
    private TextField numberSpool;

    @FXML
    private Button btn_getInfo;

    @FXML
    private TextField typeSpool;

    @FXML
    private TextField code;

    @FXML
    private TextField construct;

    @FXML
    private TextField date_create;

    @FXML
    private TextField rl;

    @FXML
    private TextField textField_machine;

    @FXML
    private TextField part;

    @FXML
    private TextField lot;

    @FXML
    private TextField length;

    @FXML
    private TextField welds;

    @FXML
    private TextField personal_rope;

    @FXML
    public void initialize() {


    }


    public void getInfoAction() {

        numberSpool.setStyle("-fx-border-color: #000000");
        if (!numberSpool.getText().isEmpty()) {
            List<TestLabel> testLabelList = TestLabelRepository.getTestLabel("http://localhost:8097/api/label/spool/" + numberSpool.getText());
            TestLabel label = testLabelList.get(0);
            System.out.println(label);

            typeSpool.setText(String.valueOf(label.getTypeSpool()));
            code.setText(label.getCode());
//            date_create.setText(String.valueOf(label.getDate_create()));
            date_create.setText(DateUtil.format(label.getDate_create()));
            rl.setText(label.getRl());
            part.setText(label.getPart());
            lot.setText(String.valueOf(label.getLot()));
            length.setText(String.valueOf(label.getLength()));
            welds.setText(String.valueOf(label.getWelds()));
            personal_rope.setText(label.getPersonal_rope());
            straightforwardness400.setText(String.valueOf(label.getStraightforwardness400()));
            straightforwardness1.setText(String.valueOf(label.getStraightforwardness400()));
            straightforwardness2.setText(String.valueOf(label.getStraightforwardness2()));
            straightforwardness3.setText(String.valueOf(label.getStraightforwardness3()));
            straightforwardness4.setText(String.valueOf(label.getStraightforwardness4()));
            straightforwardness5.setText(String.valueOf(label.getStraightforwardness5()));
            straightforwardnessAvg.setText(String.valueOf(label.getStraightforwardnessAvg()));
            torsion.setText(String.valueOf(label.getTorsion()));
            codeDefect.setText(label.getCodeDefect());
            torsRope.setText(String.valueOf(label.getTorsRope()));
            straightforwardnessRope.setText(String.valueOf(label.getStraightforwardnessRope()));
            numberSpool.setStyle("-fx-border-color: #a7fc2d");

        } else if (numberSpool.getText().isEmpty()) {
            numberSpool.setStyle("-fx-border-color: #ff0000");
            TextFieldService.alert("Поле  ввода пустое!\nОтсканируйте штрих-код катушки");
        } else {
            TextFieldService.alert("Данной катушки в базе нет!");
        }

//            typeSpool.setText("");
//            code.setText("");
//            date_create.setText("");
//            rl.setText("");
//            part.setText("");
//            lot.setText("");
//            length.setText("");
//            welds.setText("");
//            personal_rope.setText("");
//            straightforwardness400.setText("");
//            straightforwardness1.setText("");
//            straightforwardness2.setText("");
//            straightforwardness3.setText("");
//            straightforwardness4.setText("");
//            straightforwardness5.setText("");
//            straightforwardnessAvg.setText("");
//           torsion.setText("");
//            codeDefect.setText("");
//            torsRope.setText("");
//            straightforwardnessRope.setText("");

    }

    public void scanByKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            getInfoAction();
        }
    }
}



