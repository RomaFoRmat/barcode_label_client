package gui.controller;



import gui.model.TestLabel;
import gui.repository.TestLabelRepository;
import gui.service.DateUtil;
import gui.service.TextFieldService;
import javafx.application.Platform;
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

//    @FXML
//    private TextField codeDefect;

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

//    @FXML
//    private TextField textField_machine;

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
        /**для наведения фокуса на определенное поле*/
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                numberSpool.requestFocus();
            }
        });

    }


    public void getInfoAction() {
        numberSpool.setStyle("-fx-border-color: #000000");
        if (!numberSpool.getText().isEmpty()) {
            List<TestLabel> testLabelList = TestLabelRepository.getTestLabel("http://localhost:8097/api/label/spool/" + numberSpool.getText());
            TestLabel label = testLabelList.get(0);
            System.out.println(label);

            if (label.getTypeSpool() != null) {
                typeSpool.setText(String.valueOf(label.getTypeSpool()));
            } else {
                typeSpool.setText("");
            }
            if (label.getCode() != null) {
                code.setText(label.getCode());
            } else {
                code.setText("");
            }
            if (label.getDate_create() != null) {
                date_create.setText(DateUtil.format(label.getDate_create()));
            } else {
                date_create.setText("");
            }
            if (label.getRl() != null) {
                rl.setText(label.getRl());
            } else {
                rl.setText("");
            }
            if (label.getPart() != null) {
                part.setText(label.getPart());
            } else
                part.setText("");
            if (label.getLot() != 0) {
                lot.setText(String.valueOf(label.getLot()));
            } else {
                lot.setText("");
            }
            if (label.getLength() != 0) {
                length.setText(String.valueOf(label.getLength()));
            } else {
                length.setText("");
            }
            welds.setText(String.valueOf(label.getWelds()));
            if (label.getPersonal_rope() != null) {
                personal_rope.setText(label.getPersonal_rope());
            } else {
                personal_rope.setText("");
            }
            if (label.getStraightforwardness400() != null) {
                straightforwardness400.setText(String.valueOf(label.getStraightforwardness400()));
            } else {
                straightforwardness400.setText("");
            }
            if (label.getStraightforwardness1() != null) {
                straightforwardness1.setText(String.valueOf(label.getStraightforwardness1()));
            } else {
                straightforwardness1.setText("");
            }
            if (label.getStraightforwardness2() != null) {
                straightforwardness2.setText(String.valueOf(label.getStraightforwardness2()));
            } else {
                straightforwardness2.setText("");
            }
            if (label.getStraightforwardness3() != null) {
                straightforwardness3.setText(String.valueOf(label.getStraightforwardness3()));
            } else {
                straightforwardness3.setText("");
            }
            if (label.getStraightforwardness4() != null) {
                straightforwardness4.setText(String.valueOf(label.getStraightforwardness4()));
            } else {
                straightforwardness4.setText("");
            }
            if (label.getStraightforwardness5() != null) {
                straightforwardness5.setText(String.valueOf(label.getStraightforwardness5()));
            } else {
                straightforwardness5.setText("");
            }
            if (label.getStraightforwardnessAvg() != null) {
                straightforwardnessAvg.setText(String.valueOf(label.getStraightforwardnessAvg()));
            } else {
                straightforwardnessAvg.setText("");
            }
            if (label.getTorsion() != null) {
                torsion.setText(String.valueOf(label.getTorsion()));
            } else {
                torsion.setText("");
            }
            if (label.getTorsRope() != null) {
                torsRope.setText(String.valueOf(label.getTorsRope()));
            } else {
                torsRope.setText("");
            }
            if (label.getStraightforwardnessRope() != null) {
                straightforwardnessRope.setText(String.valueOf(label.getStraightforwardnessRope()));
            } else {
                straightforwardnessRope.setText("");
            }
            numberSpool.setStyle("-fx-border-color: #a7fc2d");
            numberSpool.setText("");

        } else if (numberSpool.getText().isEmpty()) {
            numberSpool.setStyle("-fx-border-color: #ff0000");

            TextFieldService.alert("Поле ввода пустое!\nОтсканируйте штрих-код катушки");
        }
    }


    public void scanByKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            getInfoAction();
        }
    }
}



