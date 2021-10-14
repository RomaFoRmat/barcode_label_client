package gui.controller;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gui.model.CellStyleOption;
import gui.model.FieldModel;
import gui.model.TestLabel;
import gui.repository.TestLabelRepository;
import gui.service.*;
import gui.service.DateUtil;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;


public class ScanController {

    @FXML
    private AnchorPane anchorPaneMain;
    @FXML
    private JFXButton btn_labelForm;
    @FXML
    private JFXButton btn_printLabel;
    @FXML
    private JFXButton btn_clear;
    @FXML
    private Label lbl_barcodeSpool;
    @FXML
    private JFXTextField barcodeSpool;
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
    private Label lbl_numberSpool;
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
//    private TextField personal_rope;

    private TextField numberSpool;

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
//    private CheckBox cb_persRope;
    private CheckBox cb_numberSpool;

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
    private TextField filterField;

    @FXML
    private Label lblNumbSpool;

    @FXML
    private Label lblSpool;

    @FXML
    private Label lbl_dateTime;
    @FXML
    private Tab tabInfoSpool;

    private TestLabel testLabel;

    public ObservableList<TestLabel> tableSpool = FXCollections.observableArrayList();

//    private List<FieldModel> fieldModelsList;

    private List<FieldModel> fieldModelList = new ArrayList();


    @FXML
    public void initialize() {
        /**для наведения фокуса на определенное поле*/
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                barcodeSpool.requestFocus();
            }
        });

        initClock();

        fieldModelList.add(new FieldModel(construct, cb_construct, "", CellStyleOption.ENLARGED2));
        fieldModelList.add(new FieldModel(code, cb_code, "Code:", CellStyleOption.BASE));
        fieldModelList.add(new FieldModel(rl, cb_lr, "", CellStyleOption.ENLARGED));
        fieldModelList.add(new FieldModel(numberSpool, cb_numberSpool, "Bob.№:", CellStyleOption.BASE));
        fieldModelList.add(new FieldModel(part, cb_part, "Part №:", CellStyleOption.BASE));
        fieldModelList.add(new FieldModel(lot, cb_lot, "Lot №:", CellStyleOption.BASE));
        fieldModelList.add(new FieldModel(length, cb_length, "Length:", CellStyleOption.BASE));
        fieldModelList.add(new FieldModel(typeSpool, cb_typeSpool, "", CellStyleOption.BASE));
        fieldModelList.add(new FieldModel(welds, cb_welds, "Welds:", CellStyleOption.BASE));
        fieldModelList.add(new FieldModel(date_create, cb_date, "Date:", CellStyleOption.BASE));


        initializeTableColumns();
        List<TestLabel> testLabelList = TestLabelRepository.getAllSpools();
        tableSpool.addAll(testLabelList);
        tableView.setItems(tableSpool);



        UpdaterUtil updaterUtil = new UpdaterUtil(this);
        Timer timer = new Timer();
        timer.schedule(updaterUtil, 0, 20000);


    }

    public void filterTable() {
        /**Обворачиваем ObservableList в FilteredList (initially display all data) */
        FilteredList<TestLabel> filteredData = new FilteredList<>(tableSpool, b -> true);

        /** Устанавливаем предикат фильтра всякий раз, когда фильтр изменяется: */
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(testLabel -> {
                // If filter text is empty, display all spools.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Сравниваем номер каждой катушки с текстом фильтра.
                String lowerCaseFilter = newValue.toLowerCase();

                if (testLabel.getNumberSpool().toLowerCase().contains(lowerCaseFilter)) {
                    return true; //
                } else
                    return false;
            });
        });
        /** Обворачиваем FilteredList в SortedList: */
        SortedList<TestLabel> sortedData = new SortedList<>(filteredData);
        /** Привязываем компаратор SortedList к компаратору TableView: */
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        /** Добавляем в таблицу отфильтрованные данные: */
        tableView.setItems(sortedData);
    }

    public void refreshTable(){
        tableSpool.clear();
        List<TestLabel> testLabelList = TestLabelRepository.getAllSpools();
        tableSpool.addAll(testLabelList);
        tableView.setItems(tableSpool);
        filterTable();
    }


    private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            lbl_dateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void unselectCheckBox() {
        cb_typeSpool.setSelected(false);
        cb_code.setSelected(false);
        cb_construct.setSelected(false);
        cb_date.setSelected(false);
        cb_lr.setSelected(false);
        cb_part.setSelected(false);
        cb_lot.setSelected(false);
        cb_length.setSelected(false);
        cb_welds.setSelected(false);
        cb_numberSpool.setSelected(false);
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
        numberSpool.clear();
        straightforwardness1.clear();
        straightforwardness2.clear();
        straightforwardness3.clear();
        straightforwardness4.clear();
        straightforwardness5.clear();
        straightforwardnessAvg.clear();
        torsion.clear();
        torsRope.clear();
        straightforwardnessRope.clear();
        barcodeSpool.setText("");
        tabInfoSpool.setText("Информация о катушке");

        lblNumbSpool.setText("");
        lblSpool.setText("");
    }

    public void exportToExcel() {

        try {

            File fileTemp = new File("src\\main\\resources\\temp\\Export.xlsx");
            FileInputStream file = new FileInputStream(new File(String.valueOf(fileTemp)));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            int rowExcel = 4;

            for (FieldModel field : fieldModelList) {
                Row row = sheet.getRow(rowExcel);
                Cell cell0 = row.createCell(0);
                Cell cell1 = row.createCell(1);

                if (field.getCheckBox().isSelected()) {
                    if (field.getCheckBox().equals(cb_construct)) {
                        sheet.addMergedRegion(new CellRangeAddress(rowExcel, rowExcel, 0, 1));
                        row.createCell(0).setCellValue(field.getTextField().getText());
                        cell0.setCellStyle(CellStylesUtil.getCellStyle(workbook, field.getCellStyleOption()));
                        row.setHeightInPoints(15);
                    } else if (field.getCheckBox().equals(cb_lr)) {
                        row.createCell(1).setCellValue(field.getTextField().getText());
                        cell1.setCellStyle(CellStylesUtil.getCellStyle(workbook, field.getCellStyleOption()));
                        row.setHeightInPoints(15);
                    } else {
                        row.createCell(0).setCellValue(field.getType());
                        cell0.setCellStyle(CellStylesUtil.getCellStyle(workbook, field.getCellStyleOption()));
                        row.createCell(1).setCellValue(field.getTextField().getText());
                        cell1.setCellStyle(CellStylesUtil.getCellStyle(workbook, field.getCellStyleOption()));
                        row.setHeightInPoints(11);
                    }
                    rowExcel++;
                }
            }
//            Cell cellLast = sheet.getRow(rowExcel).createCell(0);
            Row row = sheet.getRow(rowExcel);
            Cell cellLast = row.createCell(0);
            row.setHeightInPoints(11);
            cellLast.setCellValue("Made in Belarus");
            cellLast.setCellStyle(CellStylesUtil.getCellStyle(workbook, CellStyleOption.COUNTRY));
            sheet.addMergedRegion(new CellRangeAddress(rowExcel, rowExcel, 0, 1));


            //добавление внешних границ к этикетке:
            CellRangeAddress region = new CellRangeAddress(0, rowExcel, 0, 1);
            RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
            RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);

            file.close();
            File newLabelFile = File.createTempFile("label", ".xlsx", new File("src\\main\\resources\\temp\\"));
            FileOutputStream outFile = new FileOutputStream(newLabelFile);
//            FileOutputStream outFile = new FileOutputStream("label.xlsx");
            outFile.close();
            newLabelFile.deleteOnExit();

//            Desktop.getDesktop().open(new File("label_"+ lblNumbSpool.getText()+ ".xlsx"));
//            Desktop.getDesktop().open(new File("label.xlsx"));

//            clearFields();
//            unselectCheckBox();
            barcodeSpool.requestFocus();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toFormQrCode() throws WriterException, IOException {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("QR-Code");

            StringBuilder codeBuilder = new StringBuilder();
            String imageFormat = "png";
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 0);

            for (FieldModel field : fieldModelList) {
                if (field.getCheckBox().isSelected()) {
                    if (field.getCheckBox().equals(cb_construct)) {
                        codeBuilder.append("Construct: ").append(field.getTextField().getText()).append("\n");
                    } else {
                        codeBuilder.append(field.getType()).append(field.getTextField().getText()).append("\n");
                    }
                }
            }
            codeBuilder.append("Made in Belarus");
            BitMatrix bitMatrix = new QRCodeWriter().encode(codeBuilder.toString(), BarcodeFormat.QR_CODE, 120, 130, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, new FileOutputStream(new File("qr-code.png")));
            System.out.println(codeBuilder);

            InputStream inputStream = new FileInputStream("qr-code.png");
            //Get the contents of an InputStream as a byte[].
            byte[] bytes = IOUtils.toByteArray(inputStream);
            //Adds a picture to the workbook
            int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            //close the input stream
            inputStream.close();

            //Creates the top-level drawing patriarch.
            Drawing drawing = sheet.createDrawingPatriarch();
            //Returns an object that handles instantiating concrete classes
            CreationHelper helper = workbook.getCreationHelper();
            //Create an anchor that is attached to the worksheet
            ClientAnchor anchor = helper.createClientAnchor();
            //set top-left corner for the image
            anchor.setCol1(0);
            anchor.setRow1(0);
            //Creates a picture
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            //Reset the image to the original size
            pict.resize();

            File newQrCode = File.createTempFile("qr-code", ".xlsx", new File("src\\main\\resources\\temp\\"));
            FileOutputStream fileOut = new FileOutputStream(newQrCode);
//            FileOutputStream fileOut = new FileOutputStream("qr-code.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            newQrCode.deleteOnExit();

            barcodeSpool.requestFocus();
//            Desktop.getDesktop().open(new File("qr-code.xlsx"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void generateQrCode() throws IOException, WriterException {
        if (cb_typeSpool.isSelected() || cb_code.isSelected() || cb_construct.isSelected() || cb_date.isSelected() ||
                cb_lr.isSelected() || cb_part.isSelected() || cb_length.isSelected() || cb_lot.isSelected() ||
                cb_welds.isSelected() || cb_numberSpool.isSelected() || cb_straight300.isSelected() ||
                cb_straight600_1.isSelected() || cb_straight600_2.isSelected() || cb_straight600_3.isSelected() ||
                cb_straight600_4.isSelected() || cb_straight600_5.isSelected() || cb_straight600Avg.isSelected() ||
                cb_torsion.isSelected() || cb_torsRope.isSelected() || cb_straightRope.isSelected()) {
            toFormQrCode();
            Desktop.getDesktop().open(new File("qr-code.xlsx"));
        } else {
            TextFieldService.alert("Выберите нужные параметры для формирования QR-CODE!");
        }
    }

    public void printQR_Code() throws IOException, WriterException {
        if (cb_typeSpool.isSelected() || cb_code.isSelected() || cb_construct.isSelected() || cb_date.isSelected() ||
                cb_lr.isSelected() || cb_part.isSelected() || cb_length.isSelected() || cb_lot.isSelected() ||
                cb_welds.isSelected() || cb_numberSpool.isSelected() || cb_straight300.isSelected() ||
                cb_straight600_1.isSelected() || cb_straight600_2.isSelected() || cb_straight600_3.isSelected() ||
                cb_straight600_4.isSelected() || cb_straight600_5.isSelected() || cb_straight600Avg.isSelected() ||
                cb_torsion.isSelected() || cb_torsRope.isSelected() || cb_straightRope.isSelected()) {
            toFormQrCode();
            Desktop.getDesktop().print(new File("qr-code.xlsx"));
        } else {
            TextFieldService.alert("Выберите нужные параметры для формирования QR-CODE!");
        }
    }


    public void printLabel() throws IOException {
        if (cb_typeSpool.isSelected() || cb_code.isSelected() || cb_construct.isSelected() || cb_date.isSelected() ||
                cb_lr.isSelected() || cb_part.isSelected() || cb_length.isSelected() || cb_lot.isSelected() ||
                cb_welds.isSelected() || cb_numberSpool.isSelected() || cb_straight300.isSelected() ||
                cb_straight600_1.isSelected() || cb_straight600_2.isSelected() || cb_straight600_3.isSelected() ||
                cb_straight600_4.isSelected() || cb_straight600_5.isSelected() || cb_straight600Avg.isSelected() ||
                cb_torsion.isSelected() || cb_torsRope.isSelected() || cb_straightRope.isSelected()) {
            exportToExcel();
            Desktop.getDesktop().print(new File("label.xlsx"));
        }  else {
            TextFieldService.alert("Выберите нужные параметры для формирования QR-CODE!");
        }
//            clearFields();
    }

    public void toFormLabel() throws IOException {

        if (cb_typeSpool.isSelected() || cb_code.isSelected() || cb_construct.isSelected() || cb_date.isSelected() ||
                cb_lr.isSelected() || cb_part.isSelected() || cb_length.isSelected() || cb_lot.isSelected() ||
                cb_welds.isSelected() || cb_numberSpool.isSelected() || cb_straight300.isSelected() ||
                cb_straight600_1.isSelected() || cb_straight600_2.isSelected() || cb_straight600_3.isSelected() ||
                cb_straight600_4.isSelected() || cb_straight600_5.isSelected() || cb_straight600Avg.isSelected() ||
                cb_torsion.isSelected() || cb_torsRope.isSelected() || cb_straightRope.isSelected()) {
            exportToExcel();
            Desktop.getDesktop().open(new File("label.xlsx"));

        } else {

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


    public void clearAction() {
        unselectCheckBox();
        clearFields();
    }


    public void getInfoAction() {
//        barcodeSpool.setStyle("-fx-border-color: #000000");

        if (!barcodeSpool.getText().isEmpty()) {
            List<TestLabel> testLabelList = TestLabelRepository.getTestLabel("http://localhost:8097/api/label/spool/"
                    + barcodeSpool.getText());

            if (testLabelList != null && testLabelList.isEmpty()) {
                clearFields();
                TextFieldService.alert("Данной записи в БД не найдено!");
//                barcodeSpool.setFocusColor(Paint.valueOf("#ff0000"));
                barcodeSpool.getStylesheets().clear();
                barcodeSpool.getStylesheets().add("/css/jfx_error.css");
                barcodeSpool.setText("");
                unselectCheckBox();
            }

            TestLabel label = testLabelList.get(0);
            System.out.println(label);

            typeSpool.setText(label.getTypeSpool() != null ? String.valueOf(label.getTypeSpool()) : "");
            code.setText(label.getCode() != null ? String.valueOf(label.getCode()) : "");
            construct.setText(label.getConstruct() != null ? (label.getConstruct()) : "");
            numberSpool.setText(label.getNumberSpool() != null ? (label.getNumberSpool()) : "");
            date_create.setText(label.getDate_create() != null ? DateUtil.format(label.getDate_create()) : "");
            rl.setText(label.getRl() != null ? label.getRl() : "");
            part.setText(label.getPart() != null ? label.getPart() : "");
            lot.setText(label.getLot() != null ? String.valueOf(label.getLot()) : "");
            length.setText(label.getLength() != 0 ? String.valueOf(label.getLength()) : "");
            welds.setText(label.getWelds() != 0 ? String.valueOf(label.getWelds()) : "0");
//            personal_rope.setText(label.getPersonal_rope() != null ? label.getPersonal_rope() : "");
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


//            barcodeSpool.setFocusColor(Paint.valueOf("#a7fc2d"));
            barcodeSpool.getStylesheets().clear();
            barcodeSpool.getStylesheets().add("/css/jfx_success.css");
            cb_date.setSelected(true);
            cb_numberSpool.setSelected(true);
            cb_code.setSelected(true);
            tabInfoSpool.setText("Информация о катушке: №" + numberSpool.getText());
            barcodeSpool.setText("");

        } else if (barcodeSpool.getText().isEmpty()) {
            barcodeSpool.getStylesheets().clear();
            barcodeSpool.getStylesheets().add("/css/jfx_error.css");
            clearFields();
            unselectCheckBox();
            TextFieldService.alert("Поле ввода пустое!\nОтсканируйте штрих-код катушки");

//            barcodeSpool.setFocusColor(Paint.valueOf("#ff0000"));
        }
    }


    public void scanByKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            getInfoAction();
        }
    }

}



