package gui.controller;


import gui.application.AppProperties;
import gui.model.TestLabel;
import gui.repository.TestLabelRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

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

        List<TestLabel> testLabelList = TestLabelRepository.getTestLabel("http://localhost:8097/api/label/spool/" + numberSpool.getText());
        TestLabel label = testLabelList.get(0);
//        TestLabel testLabel = TestLabelRepository.getTestLabel("http://localhost:8097/api/label/spool/" +numberSpool.getText());


        typeSpool.setText(String.valueOf(testLabelList.get(0)));
        code.setText(String.valueOf(testLabelList.get(0)));
//        code.setText(testLabel.getCode());
//        date_create.setText(String.valueOf(testLabel.getDate_create()));
//        rl.setText(testLabel.getRl());
//        part.setText(testLabel.getPart());
//        lot.setText(String.valueOf(testLabel.getLot()));
//        welds.setText(String.valueOf(testLabel.getWelds()));
//        personal_rope.setText(testLabel.getPersonal_rope());


    }


}

