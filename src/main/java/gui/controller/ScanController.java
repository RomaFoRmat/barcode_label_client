package gui.controller;


import gui.model.TestLabel;
import gui.repository.TestLabelRepository;
import gui.service.DateUtil;
import gui.service.LabelListCreator;
import gui.service.TextFieldService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.List;


public class ScanController {

    @FXML
    private AnchorPane anchorPaneMain;

    public ObservableList<TestLabel> tableSpool = FXCollections.observableArrayList();
    @FXML
    private Button btn_labelForm;
    @FXML
    private Label lbl_spool;
    @FXML
    private TextField numberSpool;
    @FXML
    private Button btn_getInfo;
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
    private TextField part;
    @FXML
    private TextField lot;
    @FXML
    private TextField length;
    @FXML
    private TextField welds;

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
    private Label lbl_torsRope;

    @FXML
    private Label lbl_prRope;
    @FXML
    private TextField personal_rope;

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
    private TextField torsRope;

    @FXML
    private TextField straightforwardnessRope;

    @FXML
    private TextField straightforwardness300;

    @FXML
    private CheckBox cb_typeSpool;

    @FXML
    private CheckBox cb_code;

    @FXML
    private CheckBox cb_construct;

    @FXML
    private CheckBox cb_date;

    @FXML
    private CheckBox cb_lr;

    @FXML
    private CheckBox cb_part;

    @FXML
    private CheckBox cb_lot;

    @FXML
    private CheckBox cb_length;

    @FXML
    private CheckBox cb_welds;

    @FXML
    private CheckBox cb_persRope;

    @FXML
    private CheckBox cb_straight300;

    @FXML
    private CheckBox cb_straight600_1;

    @FXML
    private CheckBox cb_straight600_2;

    @FXML
    private CheckBox cb_straight600_3;

    @FXML
    private CheckBox cb_straight600_4;

    @FXML
    private CheckBox cb_straight600_5;

    @FXML
    private CheckBox cb_straight600Avg;

    @FXML
    private CheckBox cb_torsion;

    @FXML
    private CheckBox cb_torsRope;

    @FXML
    private CheckBox cb_straightRope;

    @FXML
    private TableView<TestLabel> tableView;
    @FXML
    private TableColumn<TestLabel, String> tc_numberSpool;
    @FXML
    private TableColumn<TestLabel, String> tc_typeSpool;
    @FXML
    private TableColumn<TestLabel, String> tc_code;
    @FXML
    private TableColumn<TestLabel, String> tc_construct;
    @FXML
    private TableColumn<TestLabel, LocalDate> tc_date;
    @FXML
    private TableColumn<TestLabel, String> tc_lr;
    @FXML
    private TableColumn<TestLabel, String> tc_part;
    @FXML
    private TableColumn<TestLabel, Integer> tc_lot;
    @FXML
    private TableColumn<TestLabel, Integer> tc_length;
    @FXML
    private TableColumn<TestLabel, Integer> tc_welds;
    @FXML
    private TableColumn<TestLabel, String> tc_personalRope;
    @FXML
    private TableColumn<TestLabel, Float> tc_straight_300;
    @FXML
    private TableColumn<TestLabel, Float> tc_straight600_1;
    @FXML
    private TableColumn<TestLabel, Float> tc_straight600_2;
    @FXML
    private TableColumn<TestLabel, Float> tc_straight600_3;
    @FXML
    private TableColumn<TestLabel, Float> tc_straight600_4;
    @FXML
    private TableColumn<TestLabel, Float> tc_straight600_5;
    @FXML
    private TableColumn<TestLabel, Float> tc_straight600Avg;
    @FXML
    private TableColumn<TestLabel, Float> tc_torsion;
    @FXML
    private TableColumn<TestLabel, Float> tc_torsionRope;
    @FXML
    private TableColumn<TestLabel, Float> tc_straightRope;

    @FXML
    private Label lblNumbSpool;

    @FXML
    private Label lblSpool;

    @FXML
    private Tab tabInfoSpool;

    private TestLabel testLabel;

    @FXML
    public void initialize() {
        /**для наведения фокуса на определенное поле*/
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                numberSpool.requestFocus();
            }
        });

        initializeTableColumns();

        List<TestLabel> testLabelList = TestLabelRepository.getAllSpools();
        tableSpool.addAll(testLabelList);
        tableView.setItems(tableSpool);

//        TestLabel testLabel = new TestLabel();
//        testLabel.setTypeSpool("BS-60");
//        testLabel.setLength(23424);
//        tableSpool.addAll(testLabel);
//        tableView.setItems(tableSpool);

    }

    public void unselectCheckBox(){
        cb_typeSpool.setSelected(false);
        cb_code.setSelected(false);
        cb_construct.setSelected(false);
        cb_date.setSelected(false);
        cb_lr.setSelected(false);
        cb_part.setSelected(false);
        cb_lot.setSelected(false);
        cb_length.setSelected(false);
        cb_welds.setSelected(false);
        cb_persRope.setSelected(false);
        cb_straight300.setSelected(false);
        cb_straight600_1.setSelected(false);
        cb_straight600_2.setSelected(false);
        cb_straight600_3.setSelected(false);
        cb_straight600_4.setSelected(false);
        cb_straight600_5.setSelected(false);
        cb_straight600Avg.setSelected(false);
        cb_torsion.setSelected(false);
        cb_torsRope.setSelected(false);
        cb_straightRope.setSelected(false);
    }

    public void clearFields() {
        typeSpool.clear();
        code.clear();
        construct.clear();
        date_create.clear();
        rl.clear();
        part.clear();
        lot.clear();
        length.clear();
        welds.clear();
        personal_rope.clear();
        straightforwardness1.clear();
        straightforwardness2.clear();
        straightforwardness3.clear();
        straightforwardness4.clear();
        straightforwardness5.clear();
        straightforwardnessAvg.clear();
        torsion.clear();
        torsRope.clear();
        straightforwardnessRope.clear();
        numberSpool.setText("");

        lblNumbSpool.setText("");
        lblSpool.setText("");
    }

//    public void clickColor(){
//        if(cb_typeSpool.isSelected() && cb_code.isSelected()){
//            typeSpool.setStyle("-fx-background-color: #fffb02");
////            typeSpool.setStyle("-fx-border-color: #fcef00");
//        } else { typeSpool.setStyle("-fx-border-color: #1d1d1d");}
//
////        if (cb_code.isSelected()) {
////            code.setStyle("-fx-background-color: #86f670");
////        } else {
////            code.setStyle("-fx-border-color: #1d1d1d");
////        }
//
//
//    }

    public void Excel(){
        try {

            File fileTemp = new File("src\\main\\resources\\temp\\Export.xlsx");
            FileInputStream file = new FileInputStream(new File(String.valueOf(fileTemp)));

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Cell cell = null;

            cell = sheet.getRow(7).getCell(1);
            cell.setCellValue(lblNumbSpool.getText());
            //Update the value of cell
            if(cb_typeSpool.isSelected()) {
                cell = sheet.getRow(4).getCell(0);
                cell.setCellValue(typeSpool.getText());
            } else {
                cell = sheet.getRow(4).getCell(0);
                cell.setCellValue("");
            }
            if (cb_code.isSelected()) {
                cell = sheet.getRow(5).getCell(1);
                cell.setCellValue(code.getText());
            } else {
                cell = sheet.getRow(5).getCell(1);
                cell.setCellValue("");
            }
            if (cb_lr.isSelected()){
                cell = sheet.getRow(6).getCell(1);
                cell.setCellValue(rl.getText());
            } else {
                cell = sheet.getRow(6).getCell(1);
                cell.setCellValue("");
            }
            if (cb_date.isSelected()){
                cell = sheet.getRow(8).getCell(1);
                cell.setCellValue(date_create.getText());
            }else {
                cell = sheet.getRow(8).getCell(1);
                cell.setCellValue("");
            }
            if (cb_length.isSelected()){
                cell = sheet.getRow(9).getCell(1);
                cell.setCellValue(length.getText());
            } else {
                cell = sheet.getRow(9).getCell(1);
                cell.setCellValue("");
            }
            if (cb_part.isSelected()){
                cell = sheet.getRow(10).getCell(1);
                cell.setCellValue(part.getText());
            } else {
                cell = sheet.getRow(10).getCell(1);
                cell.setCellValue("");
            }
            if (cb_lot.isSelected()){
                cell = sheet.getRow(11).getCell(1);
                cell.setCellValue(lot.getText());
            } else {
                cell = sheet.getRow(11).getCell(1);
                cell.setCellValue("");
            }

            file.close();

            FileOutputStream outFile =new FileOutputStream(new File(String.valueOf(fileTemp)));
            workbook.write(outFile);
            outFile.close();
            Desktop.getDesktop().open(fileTemp);

            clearFields();
            lblNumbSpool.setText("");
            lblSpool.setText("");

            unselectCheckBox();
            numberSpool.requestFocus();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void CheckBoxEmpty(){
//        boolean isMyCheckBoxEmpty= (cb_code.get)
//    }


    public void toFormLabel() throws IOException {
//        LabelListCreator.createExcelList();

        if(cb_typeSpool.isSelected() || cb_code.isSelected() || cb_construct.isSelected() || cb_date.isSelected() ||
                cb_lr.isSelected() || cb_part.isSelected() || cb_length.isSelected()|| cb_lot.isSelected() ||
                cb_welds.isSelected() || cb_persRope.isSelected() || cb_straight300.isSelected() ||
                cb_straight600_1.isSelected() || cb_straight600_2.isSelected() || cb_straight600_3.isSelected() ||
                cb_straight600_4.isSelected() ||  cb_straight600_5.isSelected() || cb_straight600Avg.isSelected() ||
                cb_torsion.isSelected() || cb_torsRope.isSelected() || cb_straightRope.isSelected())
        {
        Excel();
        }
        else
        {
            TextFieldService.alert("Выберите нужные параметры для формирования этикетки!");
        }
    }

    public void initializeTableColumns() {
        tc_numberSpool.setCellValueFactory(new PropertyValueFactory<>("numberSpool"));
        tc_typeSpool.setCellValueFactory(new PropertyValueFactory<>("typeSpool"));
        tc_code.setCellValueFactory(new PropertyValueFactory<>("code"));
        tc_construct.setCellValueFactory(new PropertyValueFactory<>("construct"));
        tc_date.setCellValueFactory(new PropertyValueFactory<>("date_create"));
        tc_lr.setCellValueFactory(new PropertyValueFactory<>("rl"));
        tc_part.setCellValueFactory(new PropertyValueFactory<>("part"));
        tc_lot.setCellValueFactory(new PropertyValueFactory<>("lot"));
        tc_length.setCellValueFactory(new PropertyValueFactory<>("length"));
        tc_welds.setCellValueFactory(new PropertyValueFactory<>("welds"));
        tc_personalRope.setCellValueFactory(new PropertyValueFactory<>("personal_rope"));
        tc_straight_300.setCellValueFactory(new PropertyValueFactory<>("straightforwardness300"));
        tc_straight600_1.setCellValueFactory(new PropertyValueFactory<>("straightforwardness1"));
        tc_straight600_2.setCellValueFactory(new PropertyValueFactory<>("straightforwardness2"));
        tc_straight600_3.setCellValueFactory(new PropertyValueFactory<>("straightforwardness3"));
        tc_straight600_4.setCellValueFactory(new PropertyValueFactory<>("straightforwardness4"));
        tc_straight600_5.setCellValueFactory(new PropertyValueFactory<>("straightforwardness5"));
        tc_straight600Avg.setCellValueFactory(new PropertyValueFactory<>("straightforwardnessAvg"));
        tc_torsion.setCellValueFactory(new PropertyValueFactory<>("torsion"));
        tc_torsionRope.setCellValueFactory(new PropertyValueFactory<>("torsRope"));
        tc_straightRope.setCellValueFactory(new PropertyValueFactory<>("straightforwardnessRope"));

    }


    public void getInfoAction() {
        numberSpool.setStyle("-fx-border-color: #000000");
        if (!numberSpool.getText().isEmpty()) {
            List<TestLabel> testLabelList = TestLabelRepository.getTestLabel("http://localhost:8097/api/label/spool/"
                    + numberSpool.getText());

            if (testLabelList != null && testLabelList.isEmpty()) {
                clearFields();
                numberSpool.setStyle("-fx-background-color: #ff0000");
                TextFieldService.alert("Данной записи в БД не найдено!");
                numberSpool.setStyle("-fx-border-color: #ff0000");
                numberSpool.setText("");
            }

            TestLabel label = testLabelList.get(0);
            System.out.println(label);

            typeSpool.setText(label.getTypeSpool() != null ? String.valueOf(label.getTypeSpool()) : "");
            code.setText(label.getCode() != null ? String.valueOf(label.getCode()) : "");
            construct.setText(label.getConstruct() != null ? (label.getConstruct()) : "");
            date_create.setText(label.getDate_create() != null ? DateUtil.format(label.getDate_create()) : "");
            rl.setText(label.getRl() != null ? label.getRl() : "");
            part.setText(label.getPart() != null ? label.getPart() : "");
            lot.setText(label.getLot() != 0 ? String.valueOf(label.getLot()) : "");
            length.setText(label.getLength() != 0 ? String.valueOf(label.getLength()) : "");
            welds.setText(label.getWelds() != 0 ? String.valueOf(label.getWelds()) : "0");
            personal_rope.setText(label.getPersonal_rope() != null ? label.getPersonal_rope() : "");
            straightforwardness300.setText(label.getStraightforwardness300() != null ?
                    String.valueOf(label.getStraightforwardness300()) : "");
            straightforwardness1.setText(label.getStraightforwardness1() != null ?
                    String.valueOf(label.getStraightforwardness1()) : "");
            straightforwardness2.setText(label.getStraightforwardness2() != null ?
                    String.valueOf(label.getStraightforwardness2()) : "");
            straightforwardness3.setText(label.getStraightforwardness3() != null ?
                    String.valueOf(label.getStraightforwardness3()) : "");
            straightforwardness4.setText(label.getStraightforwardness4() != null ?
                    String.valueOf(label.getStraightforwardness4()) : "");
            straightforwardness5.setText(label.getStraightforwardness5() != null ?
                    String.valueOf(label.getStraightforwardness5()) : "");
            straightforwardnessAvg.setText(label.getStraightforwardnessAvg() != null ?
                    String.valueOf(label.getStraightforwardnessAvg()) : "");
            torsion.setText(label.getTorsion() != null ? String.valueOf(label.getTorsion()) : "");
            torsRope.setText(label.getTorsRope() != null ? String.valueOf(label.getTorsRope()) : "");
            straightforwardnessRope.setText(label.getStraightforwardnessRope() != null ?
                    String.valueOf(label.getStraightforwardnessRope()) : "");
//            if (label.getStraightforwardnessRope() != null) {
//                straightforwardnessRope.setText(String.valueOf(label.getStraightforwardnessRope()));
//            } else {
//                straightforwardnessRope.setText("");
//            }

            numberSpool.setStyle("-fx-border-color: #a7fc2d");
            lblSpool.setText("Катушка №:");
            lblNumbSpool.setText(numberSpool.getText());
//            tabInfoSpool.setText("Информация о катушке: №"+ numberSpool.getText());
            numberSpool.setText("");

        } else if (numberSpool.getText().isEmpty()) {
            numberSpool.setStyle("-fx-background-color: #ff0000");
            clearFields();
            TextFieldService.alert("Поле ввода пустое!\nОтсканируйте штрих-код катушки");
            numberSpool.setStyle("-fx-border-color: #ff0000");
        }
    }


    public void scanByKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            getInfoAction();
        }
    }
}



