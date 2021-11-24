package gui.controller;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gui.application.AppProperties;
import gui.application.Main;
import gui.model.*;
import gui.repository.PersonalsRepository;
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


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.text.DateFormatter;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
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
    private TextField dateCreate;
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
    private Label lbl_pr300;

    @FXML
    private Label lbl_pr600;

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
//    private TextField personalRope;

    private TextField numberSpool;

    @FXML
    private TextField straightforwardness600_0;

    @FXML
    private TextField straightforwardness600_1;

    @FXML
    private TextField straightforwardness600_2;

    @FXML
    private TextField straightforwardness600_3;

    @FXML
    private TextField straightforwardness600_4;

    @FXML
    private TextField straightforwardness600_5;

    @FXML
    private TextField straightforwardness600Avg;

    @FXML
    private TextField torsion;

    @FXML
    private TextField torsRope;

    @FXML
    private TextField straightforwardnessRope;

    @FXML
    private TextField straightforwardness300;

    @FXML
    private CheckBox cbTypeSpool;

    @FXML
    private CheckBox cbCode;

    @FXML
    private CheckBox cbConstruct;

    @FXML
    private CheckBox cbDate;

    @FXML
    private CheckBox cbLr;

    @FXML
    private CheckBox cbPart;

    @FXML
    private CheckBox cbLot;

    @FXML
    private CheckBox cbLength;

    @FXML
    private CheckBox cbWelds;

    @FXML
//    private CheckBox cb_persRope;
    private CheckBox cbNumberSpool;

    @FXML
    private CheckBox cbStraight300;

    @FXML
    private CheckBox cb_straight600_0;

    @FXML
    private CheckBox cbStraight600_1;

    @FXML
    private CheckBox cbStraight600_2;

    @FXML
    private CheckBox cbStraight600_3;

    @FXML
    private CheckBox cbStraight600_4;

    @FXML
    private CheckBox cbStraight600_5;

    @FXML
    private CheckBox cbStraight600Avg;

    @FXML
    private CheckBox cbTorsion;


//    @FXML
//    private CheckBox cb_torsRope;

//    @FXML
//    private CheckBox cb_straightRope;

    @FXML
    private TableView<TestLabel> tableView;
    @FXML
    private TableColumn<TestLabel, String> tcNumberSpool;
    @FXML
    private TableColumn<TestLabel, String> tcTypeSpool;
    @FXML
    private TableColumn<TestLabel, String> tcCode;
    @FXML
    private TableColumn<TestLabel, String> tcConstruct;
    @FXML
    private TableColumn<TestLabel, LocalDateTime> tcDateCreate;
    @FXML
    private TableColumn<TestLabel, String> tcLR;
    @FXML
    private TableColumn<TestLabel, String> tcPart;
    @FXML
    private TableColumn<TestLabel, Integer> tcLot;
    @FXML
    private TableColumn<TestLabel, LocalDate> tcDateRope;
    @FXML
    private TableColumn<TestLabel, Integer> tcLength;
    @FXML
    private TableColumn<TestLabel, Integer> tcWelds;
    @FXML
    private TableColumn<TestLabel, String> tcPersonalRope;
    @FXML
    private TableColumn<TestLabel, Double> tcNumberRopeMachine;
    @FXML
    private TableColumn<TestLabel, Double> tcNumbWeldingMachine;
    @FXML
    private TableColumn<TestLabel, Double> tcStraight300;
    @FXML
    private TableColumn<TestLabel, Double> tcStraight600_0;
    @FXML
    private TableColumn<TestLabel, Double> tcStraight600_1;
    @FXML
    private TableColumn<TestLabel, Double> tcStraight600_2;
    @FXML
    private TableColumn<TestLabel, Double> tcStraight600_3;
    @FXML
    private TableColumn<TestLabel, Double> tcStraight600_4;
    @FXML
    private TableColumn<TestLabel, Double> tcStraight600_5;
    @FXML
    private TableColumn<TestLabel, Double> tcStraight600Avg;
    @FXML
    private TableColumn<TestLabel, Double> tcTorsion;
    @FXML
    private TableColumn<TestLabel, Double> tcTorsionRope;
    @FXML
    private TableColumn<TestLabel, Double> tcStraightRope;
    @FXML
    private TextField filterField;

    @FXML
    private Label lblNumbSpool;

    @FXML
    private Label lblSpool;

    public ScanController scanController;

    @FXML
    private Label lblDateTime;

    @FXML
    private Tab tabInfoSpool;

    @FXML
    public Tab tabSpoolList;

    private TestLabel testLabel;

    private ObservableList<TestLabel> tableSpool = FXCollections.observableArrayList();

//    private List<FieldModel> fieldModelsList;

    private final List<FieldModel> fieldModelEngList = new ArrayList();
    private final List<FieldModel> fieldModelRusList = new ArrayList();

    private ObservableList<String> data = FXCollections.observableArrayList("РЯДОВОЙ", "ЭКСПОРТ");
    @FXML
    private JFXComboBox<String> cb_consumer;
    @FXML
    private Label lblFio;
    private Stage stage;

//    @FXML
//    private Button btnLab2;

//    @FXML
//    public void initialize(URL location, ResourceBundle resources) {
//
//
//
//    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/scan_spool.fxml"));

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanController = fxmlLoader.getController();
    }

    @FXML
    public void initialize() {

        lblFio.setText(Constants.FIO);

        /**для наведения фокуса на определенное поле*/
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                barcodeSpool.requestFocus();
            }
        });

        initClock();

        cb_consumer.setItems(data);
        cb_consumer.getSelectionModel().select(0);


        fieldModelEngList.add(new FieldModel(construct, cbConstruct, "", CellStyleOption.ENLARGED2));
        fieldModelEngList.add(new FieldModel(code, cbCode, "Code:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(rl, cbLr, "", CellStyleOption.ENLARGED));
        fieldModelEngList.add(new FieldModel(numberSpool, cbNumberSpool, "Bob.№:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(part, cbPart, "Part №:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(lot, cbLot, "Lot №:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(typeSpool, cbTypeSpool, "", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(welds, cbWelds, "Welds:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(dateCreate, cbDate, "Date:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(torsion, cbTorsion, "Torsion:", CellStyleOption.BASE));


        fieldModelRusList.add(new FieldModel(construct, cbConstruct, "", CellStyleOption.ENLARGED2));
        fieldModelRusList.add(new FieldModel(code, cbCode, "Код:", CellStyleOption.BASE));
        fieldModelRusList.add(new FieldModel(rl, cbLr, "", CellStyleOption.ENLARGED));
        fieldModelRusList.add(new FieldModel(numberSpool, cbNumberSpool, "№ кат.", CellStyleOption.BASE));
        fieldModelRusList.add(new FieldModel(welds, cbWelds, "Welds:", CellStyleOption.BASE));
        fieldModelRusList.add(new FieldModel(dateCreate, cbDate, "Дата:", CellStyleOption.BASE));


        initializeTableColumns();

        //Для отображения корректного времени в tableColumn dateCreate:
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        tcDateCreate.setCellFactory(column -> new TableCell<TestLabel, LocalDateTime>() {
            protected void updateItem(LocalDateTime dateTime, boolean empty) {
                super.updateItem(dateTime, empty);
                if (empty) {
                    setText(null);
                } else {
                    this.setText(formatter.format(dateTime));
                }
            }
        });

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

    public void refreshTable() {
        tableSpool.clear();
        List<TestLabel> testLabelList = TestLabelRepository.getAllSpools();
        tableSpool.addAll(testLabelList);
        tableView.setItems(tableSpool);
        filterTable();
    }


    private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            lblDateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

//    public void show() {
//        stage = new Stage();
//        stage.setScene(new Scene(anchorPaneMain));
//        stage.setTitle("АРМ ЛИНИИ ИНСПЕКЦИИ");
//        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
////        stage.showAndWait();
//        stage.show();
//    }

    @FXML
    public void addSpool() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/modalAllSpool.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Ввод новой катушки");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        stage.showAndWait();
    }

    public void unselectCheckBox() {
        cbTypeSpool.setSelected(false);
        cbCode.setSelected(false);
        cbConstruct.setSelected(false);
        cbDate.setSelected(false);
        cbLr.setSelected(false);
        cbPart.setSelected(false);
        cbLot.setSelected(false);
        cbLength.setSelected(false);
        cbWelds.setSelected(false);
        cbNumberSpool.setSelected(false);
        cbStraight300.setSelected(false);
        cbStraight600_1.setSelected(false);
        cbStraight600_2.setSelected(false);
        cbStraight600_3.setSelected(false);
        cbStraight600_4.setSelected(false);
        cbStraight600_5.setSelected(false);
        cbStraight600Avg.setSelected(false);
        cbTorsion.setSelected(false);
//        cb_torsRope.setSelected(false);
//        cb_straightRope.setSelected(false);
    }

    public void clearFields() {
        typeSpool.clear();
        code.clear();
        construct.clear();
        dateCreate.clear();
        rl.clear();
        part.clear();
        lot.clear();
        length.clear();
        welds.clear();
        numberSpool.clear();
        straightforwardness300.clear();
        straightforwardness600_0.clear();
        straightforwardness600_1.clear();
        straightforwardness600_2.clear();
        straightforwardness600_3.clear();
        straightforwardness600_4.clear();
        straightforwardness600_5.clear();
        straightforwardness600Avg.clear();
        torsion.clear();
        torsRope.clear();
        straightforwardnessRope.clear();
        barcodeSpool.setText("");
        tabInfoSpool.setText("Информация о катушке");

        lblNumbSpool.setText("");
        lblSpool.setText("");
    }

    public File exportToExcel() {

        try {

            File fileTemp = new File("src\\main\\resources\\template\\Export.xlsx");
            FileInputStream file = new FileInputStream(new File(String.valueOf(fileTemp)));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            int rowExcel = 4;

            List<FieldModel> fieldModels;
            String lastCellValue;

            if (cb_consumer.getValue().equals("РЯДОВОЙ")) {
                fieldModels = fieldModelRusList;
                lastCellValue = "Сделано в Беларуси";
                System.out.println("Выбрана LabelRus");
            } else {
                fieldModels = fieldModelEngList;
                lastCellValue = "Made in Belarus";
                System.out.println("Выбрана LabelEng");
            }
            for (FieldModel field : fieldModels) {
                Row row = sheet.getRow(rowExcel);
                Cell cell0 = row.createCell(0);
                Cell cell1 = row.createCell(1);

                if (field.getCheckBox().isSelected()) {
                    if (field.getCheckBox().equals(cbConstruct)) {
                        sheet.addMergedRegion(new CellRangeAddress(rowExcel, rowExcel, 0, 1));
                        row.createCell(0).setCellValue(field.getTextField().getText());
                        cell0.setCellStyle(CellStylesUtil.getCellStyle(workbook, field.getCellStyleOption()));
                        row.setHeightInPoints(14);
                    } else if (field.getCheckBox().equals(cbLr)) {
                        row.createCell(1).setCellValue(field.getTextField().getText());
                        cell1.setCellStyle(CellStylesUtil.getCellStyle(workbook, field.getCellStyleOption()));
                        row.setHeightInPoints(14);
                    } else {
                        row.createCell(0).setCellValue(field.getType());
                        cell0.setCellStyle(CellStylesUtil.getCellStyle(workbook, field.getCellStyleOption()));
                        row.createCell(1).setCellValue(field.getTextField().getText());
                        cell1.setCellStyle(CellStylesUtil.getCellStyle(workbook, field.getCellStyleOption()));
                        row.setHeightInPoints(10);
                    }
                    rowExcel++;
                }
            }
            //            Cell cellLast = sheet.getRow(rowExcel).createCell(0);
            Row row = sheet.getRow(rowExcel);
            Cell cellLast = row.createCell(0);
            row.setHeightInPoints(11);
            cellLast.setCellValue(lastCellValue);
            cellLast.setCellStyle(CellStylesUtil.getCellStyle(workbook, CellStyleOption.COUNTRY));
            sheet.addMergedRegion(new CellRangeAddress(rowExcel, rowExcel, 0, 1));

            //добавление внешних границ к этикетке:
            CellRangeAddress region = new CellRangeAddress(0, rowExcel, 0, 1);
            RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
            RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);

            file.close();

            File newLabelFile = TempFileUtil.createTemporaryLabel();
            FileOutputStream outFile = new FileOutputStream(newLabelFile);
            workbook.write(outFile);
            outFile.close();

//            clearFields();
//            unselectCheckBox();
            barcodeSpool.requestFocus();
            return newLabelFile;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public File toFormQrCode() throws WriterException, IOException {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("QR-Code");

            StringBuilder codeBuilder = new StringBuilder();
            String imageFormat = "png";
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 0);

            List<FieldModel> fieldModels;
            String lastCellValue;

            if (cb_consumer.getValue().equals("РЯДОВОЙ")) {
                fieldModels = fieldModelRusList;
                lastCellValue = "Сделано в Беларуси";
                System.out.println("Выбрана LabelRus");
            } else {
                fieldModels = fieldModelEngList;
                lastCellValue = "Made in Belarus";
                System.out.println("Выбрана LabelEng");
            }

            for (FieldModel field : fieldModels) {
                if (field.getCheckBox().isSelected()) {
                    if (field.getCheckBox().equals(cbConstruct)) {
                        codeBuilder.append("Construct:").append(field.getTextField().getText()).append("\n");
                    } else {
                        codeBuilder.append(field.getType()).append(field.getTextField().getText()).append("\n");
                    }
                }
            }
            codeBuilder.append(lastCellValue);

            //width X heigt = 117 X 117 либо 133 X 133
            File imageQrCode = TempFileUtil.createQrCodePng();
            BitMatrix bitMatrix = new QRCodeWriter().encode(codeBuilder.toString(), BarcodeFormat.QR_CODE, 125, 125, hints);
            FileOutputStream outputStreamQr = new FileOutputStream(new File(String.valueOf(imageQrCode)));
            MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, outputStreamQr);
            System.out.println(codeBuilder);
            outputStreamQr.close();
            InputStream inputStream = new FileInputStream(imageQrCode);

            //Получите содержимое InputStream как byte [].
            byte[] bytes = IOUtils.toByteArray(inputStream);
            //Добавляет картинку в workbook
            int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            //закрываем входной поток
            inputStream.close();

            //Creates the top-level drawing patriarch(создаем рисунок верхнего уровня)
            Drawing drawing = sheet.createDrawingPatriarch();
            //Returns an object that handles instantiating concrete classes()
            CreationHelper helper = workbook.getCreationHelper();
            //Create an anchor that is attached to the worksheet (Создаем привязку, прикрепленную к листу)
            ClientAnchor anchor = helper.createClientAnchor();
            //устанавливаем верхний левый угол для изображения
            anchor.setCol1(0);
            anchor.setRow1(0);
            //Создаем изображение
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            //Восстанавливаем исходный размер изображения
            pict.resize();

            File newQrCode = TempFileUtil.createTemporaryLabel();
            FileOutputStream fileOut = new FileOutputStream(newQrCode);
            workbook.write(fileOut);
            fileOut.close();
            barcodeSpool.requestFocus();
            return newQrCode;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void generateQrCode() throws IOException, WriterException {
        if (cbTypeSpool.isSelected() || cbCode.isSelected() || cbConstruct.isSelected() || cbDate.isSelected() ||
                cbLr.isSelected() || cbPart.isSelected() || cbLength.isSelected() || cbLot.isSelected() ||
                cbWelds.isSelected() || cbNumberSpool.isSelected() || cbStraight300.isSelected() ||
                cbStraight600_1.isSelected() || cbStraight600_2.isSelected() || cbStraight600_3.isSelected() ||
                cbStraight600_4.isSelected() || cbStraight600_5.isSelected() || cbStraight600Avg.isSelected() ||
                cbTorsion.isSelected() || cb_straight600_0.isSelected()) {

            Desktop.getDesktop().open(toFormQrCode());

        } else {
            TextFieldService.alert("Выберите нужные параметры для формирования QR-CODE!");
        }
    }

    public void printQR_Code() throws IOException, WriterException {
        if (cbTypeSpool.isSelected() || cbCode.isSelected() || cbConstruct.isSelected() || cbDate.isSelected() ||
                cbLr.isSelected() || cbPart.isSelected() || cbLength.isSelected() || cbLot.isSelected() ||
                cbWelds.isSelected() || cbNumberSpool.isSelected() || cbStraight300.isSelected() ||
                cbStraight600_1.isSelected() || cbStraight600_2.isSelected() || cbStraight600_3.isSelected() ||
                cbStraight600_4.isSelected() || cbStraight600_5.isSelected() || cbStraight600Avg.isSelected() ||
                cbTorsion.isSelected() || cb_straight600_0.isSelected()) {
            Desktop.getDesktop().print(toFormQrCode());
        } else {
            TextFieldService.alert("Выберите нужные параметры для формирования QR-CODE!");
        }
    }


    public void printLabel() throws IOException {
        if (cbTypeSpool.isSelected() || cbCode.isSelected() || cbConstruct.isSelected() || cbDate.isSelected() ||
                cbLr.isSelected() || cbPart.isSelected() || cbLength.isSelected() || cbLot.isSelected() ||
                cbWelds.isSelected() || cbNumberSpool.isSelected() || cbStraight300.isSelected() ||
                cbStraight600_1.isSelected() || cbStraight600_2.isSelected() || cbStraight600_3.isSelected() ||
                cbStraight600_4.isSelected() || cbStraight600_5.isSelected() || cbStraight600Avg.isSelected() ||
                cbTorsion.isSelected() || cb_straight600_0.isSelected()) {
//            exportToExcel();
            Desktop.getDesktop().print(exportToExcel());

        } else {
            TextFieldService.alert("Выберите нужные параметры для формирования QR-CODE!");
        }
//            clearFields();
    }

    public void toFormLabel() throws IOException {

        if (cbTypeSpool.isSelected() || cbCode.isSelected() || cbConstruct.isSelected() || cbDate.isSelected() ||
                cbLr.isSelected() || cbPart.isSelected() || cbLength.isSelected() || cbLot.isSelected() ||
                cbWelds.isSelected() || cbNumberSpool.isSelected() || cbStraight300.isSelected() ||
                cbStraight600_1.isSelected() || cbStraight600_2.isSelected() || cbStraight600_3.isSelected() ||
                cbStraight600_4.isSelected() || cbStraight600_5.isSelected() || cbStraight600Avg.isSelected() ||
                cbTorsion.isSelected() || cb_straight600_0.isSelected()) {
//            exportToExcel();

            Desktop.getDesktop().open(exportToExcel());

        } else {

            TextFieldService.alert("Выберите нужные параметры для формирования этикетки!");
        }
    }

//    public static File createTemporaryLabel() {
//        try {
//            FileUtils.cleanDirectory(new File("src\\main\\resources\\template\\temp\\"));
//        } catch (IOException e) {
//            e.getMessage();
//        }
//        DateTimeFormatter formatForDate = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH.mm.ss");
//        String id = String.valueOf(LocalDateTime.now().format(formatForDate));
//        System.out.println(id);
//        return new File("src\\main\\resources\\template\\temp\\" + "label -" + id + ".xlsx");
//    }

    public void initializeTableColumns() {
        tcNumberSpool.setCellValueFactory(new PropertyValueFactory<>("numberSpool"));
        tcTypeSpool.setCellValueFactory(new PropertyValueFactory<>("typeSpool"));
        tcCode.setCellValueFactory(new PropertyValueFactory<>("consumerCode"));
        tcConstruct.setCellValueFactory(new PropertyValueFactory<>("construct"));
        tcDateCreate.setCellValueFactory(new PropertyValueFactory<>("dateCreate"));
        tcLR.setCellValueFactory(new PropertyValueFactory<>("rl"));
        tcPart.setCellValueFactory(new PropertyValueFactory<>("part"));
        tcLot.setCellValueFactory(new PropertyValueFactory<>("lot"));
        tcLength.setCellValueFactory(new PropertyValueFactory<>("length"));
        tcWelds.setCellValueFactory(new PropertyValueFactory<>("welds"));
        tcPersonalRope.setCellValueFactory(new PropertyValueFactory<>("personalRope"));
        tcNumberRopeMachine.setCellValueFactory(new PropertyValueFactory<>("numberRopeMachine"));
        tcNumbWeldingMachine.setCellValueFactory(new PropertyValueFactory<>("numberWeldingMachine"));
        tcDateRope.setCellValueFactory(new PropertyValueFactory<>("dateRope"));
        tcStraight300.setCellValueFactory(new PropertyValueFactory<>("straightforwardness300"));
        tcStraight600_0.setCellValueFactory(new PropertyValueFactory<>("straightforwardness600_0"));
        tcStraight600_1.setCellValueFactory(new PropertyValueFactory<>("straightforwardness600_1"));
        tcStraight600_2.setCellValueFactory(new PropertyValueFactory<>("straightforwardness600_2"));
        tcStraight600_3.setCellValueFactory(new PropertyValueFactory<>("straightforwardness600_3"));
        tcStraight600_4.setCellValueFactory(new PropertyValueFactory<>("straightforwardness600_4"));
        tcStraight600_5.setCellValueFactory(new PropertyValueFactory<>("straightforwardness600_5"));
        tcStraight600Avg.setCellValueFactory(new PropertyValueFactory<>("straightforwardness600Avg"));
        tcTorsion.setCellValueFactory(new PropertyValueFactory<>("torsion"));
        tcTorsionRope.setCellValueFactory(new PropertyValueFactory<>("torsRope"));
        tcStraightRope.setCellValueFactory(new PropertyValueFactory<>("straightforwardnessRope"));


    }


    public void clearAction() {
        unselectCheckBox();
        clearFields();
    }


    public void  getInfoAction() {

        if (!barcodeSpool.getText().isEmpty()) {
            List<TestLabel> testLabelList = TestLabelRepository.getTestLabel("http://localhost:8097/api/label/spool/"
                    + barcodeSpool.getText());

            if (testLabelList != null && testLabelList.isEmpty()) {
                Constants.SPOOL_NUMBER = barcodeSpool.getText();
                addSpool();
                return ;
            }

            TestLabel label = testLabelList.get(0);
            System.out.println(label);

            LocalDate datePrintLabel = LocalDate.now();

            typeSpool.setText(label.getTypeSpool() != null ? String.valueOf(label.getTypeSpool()) : "");
            code.setText(label.getConsumerCode() != null ? String.valueOf(label.getConsumerCode()) : "");
            construct.setText(label.getConstruct() != null ? (label.getConstruct()) : "");
            numberSpool.setText(label.getNumberSpool() != null ? (label.getNumberSpool()) : "");
//            date_create.setText(label.getDate_create() != null ? DateUtil.format(label.getDate_create()) : "");
            dateCreate.setText(DateUtil.format(datePrintLabel));
            rl.setText(label.getRl() != null ? label.getRl() : "");
            part.setText(label.getPart() != null ? label.getPart() : "");
            lot.setText(label.getLot() != null ? String.valueOf(label.getLot()) : "");
            length.setText(label.getLength() != null ? String.valueOf(label.getLength()) : "");
            welds.setText(label.getWelds() != null ? String.valueOf(label.getWelds()) : "0");
//            personalRope.setText(label.getPersonal_rope() != null ? label.getPersonal_rope() : "");
            straightforwardness300.setText(label.getStraightforwardness300() != null ?
                    String.valueOf((label.getStraightforwardness300())) : "");
            straightforwardness600_0.setText(label.getStraightforwardness600_0() != null ?
                    String.valueOf((label.getStraightforwardness600_0())) : "");
            straightforwardness600_1.setText(label.getStraightforwardness600_1() != null ?
                    String.valueOf(label.getStraightforwardness600_1()) : "");
            straightforwardness600_2.setText(label.getStraightforwardness600_2() != null ?
                    String.valueOf(label.getStraightforwardness600_2()) : "");
            straightforwardness600_3.setText(label.getStraightforwardness600_3() != null ?
                    String.valueOf(label.getStraightforwardness600_3()) : "");
            straightforwardness600_4.setText(label.getStraightforwardness600_4() != null ?
                    String.valueOf(label.getStraightforwardness600_4()) : "");
            straightforwardness600_5.setText(label.getStraightforwardness600_5() != null ?
                    String.valueOf(label.getStraightforwardness600_5()) : "");
            straightforwardness600Avg.setText(label.getStraightforwardness600Avg() != null ?
                    String.valueOf(label.getStraightforwardness600Avg()) : "");
            torsion.setText(label.getTorsion() != null ? String.valueOf(label.getTorsion()) : "");
            torsRope.setText(label.getTorsRope() != null ? String.valueOf(label.getTorsRope()) : "");
            straightforwardnessRope.setText(label.getStraightforwardnessRope() != null ?
                    String.valueOf(label.getStraightforwardnessRope()) : "");
//            if (label.getStraightforwardnessRope() != null) {
//                straightforwardnessRope.setText(String.valueOf(label.getStraightforwardnessRope()));
//            } else {
//                straightforwardnessRope.setText("");
//            }

            barcodeSpool.getStylesheets().clear();
            barcodeSpool.getStylesheets().add("/css/jfx_success.css");
            cbConstruct.setSelected(true);
            cbCode.setSelected(true);
            cbLr.setSelected(true);
            cbNumberSpool.setSelected(true);
            cbDate.setSelected(true);
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

    //Запуск лаб испытаний:
    @FXML
    public void openProjectStart2() throws IOException {
        Runtime.getRuntime().exec("C:\\Program Files (x86)\\LaboratoryResearches2\\ProjectStart2.exe");
        System.out.println("Открыть: Лабораторные испытания СтПЦ-2");
    }

}



