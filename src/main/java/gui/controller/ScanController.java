package gui.controller;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import gui.application.AppProperties;
import gui.application.Main;
import gui.model.*;
import gui.repository.TestLabelRepository;
import gui.service.*;
import gui.service.DateUtil;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tornadofx.control.DateTimePicker;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;


public class ScanController {

    @FXML
    private AnchorPane anchorPaneMain;
    private final ObservableList<TestLabel> tableSpool = FXCollections.observableArrayList();
    private final List<String> listAlert = new ArrayList<>();
    @FXML
    private JFXButton btnLabelForm;
    @FXML
    private JFXButton btnPrintLabel;
    @FXML
    private JFXButton btnClear;
    @FXML
    private Label lblBarcodeSpool;
    @FXML
    private JFXTextField barcodeSpool;
    @FXML
    private Label lblType;
    @FXML
    private Label lblCode;
    @FXML
    private Label lblConstruct;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblLR;
    @FXML
    private Label lblPart;
    @FXML
    private Label lblLot;
    @FXML
    private Label lblLength;
    @FXML
    private Label lblWelds;
    @FXML
    private Label lblNumberSpool;
    @FXML
    private TextField typeSpool;
    @FXML
    private TextField code;
    @FXML
    private TextField construct;
    @FXML
    private TextField datePrint;
    @FXML
    private TextField lr;
    @FXML
    private TextField part;
    @FXML
    private TextField lot;
    @FXML
    private TextField length;
    @FXML
    private TextField welds;

    @FXML
    private Label lblPr300;

    @FXML
    private Label lblPr600;

    @FXML
    private Label lblPr1;

    @FXML
    private Label lblPr2;

    @FXML
    private Label lblPr3;

    @FXML
    private Label lblPr4;

    @FXML
    private Label lblPr5;

    @FXML
    private Label lblPrAvg;

    @FXML
    private Label lblTorsion;

    @FXML
    private Label lblPersonalRope;

    @FXML
    private Label lblPrRope;

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

/*    @FXML
    private TextField torsRope;

    @FXML
    private Label lbl_torsRope;

    @FXML
    private TextField straightforwardnessRope;*/

    @FXML
    private TextField personalRope;
    @FXML
    private TextField numberRopeMachine;
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
    private CheckBox cbNumberSpool;
    @FXML
    private CheckBox cbStraight300;
    @FXML
    private CheckBox cbStraight600_0;
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


/*  //08.12.2021 - Закомментировано, т.к. часть данных убраны с пользовательского ввода для ЛИ м/к
    @FXML
    private TableColumn<TestLabel, LocalDate> tcDateRope;
    @FXML
    private TableColumn<TestLabel, Integer> tcLength;
    @FXML
    private TableColumn<TestLabel, Double> tcNumbWeldingMachine;
    @FXML
    private TableColumn<TestLabel, Double> tcTorsionRope;
    @FXML
    private TableColumn<TestLabel, Double> tcStraightRope;
*/

    @FXML
    private TableColumn<TestLabel, Integer> tcWelds;
    @FXML
    private TableColumn<TestLabel, String> tcPersonalRope;
    @FXML
    private TableColumn<TestLabel, Double> tcNumberRopeMachine;
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
    private TextField filterField;
    @FXML
    private Label lblDateTime;
    @FXML
    private Tab tabInfoSpool;
    @FXML
    public Tab tabSpoolList;
    @FXML
    private JFXHamburger hamburgerMenu;
    @FXML
    private JFXComboBox<String> cbConsumer;
    @FXML
    private Label lblFio;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private DateTimePicker dateStart;
    @FXML
    private DateTimePicker dateEnd;
    @FXML
    private JFXButton btnDateBetween;
    @FXML
    private JFXButton btnQrCode;
    @FXML
    private JFXButton btnPrintQr;
    @FXML
    private VBox vBoxTfList0;
    @FXML
    private VBox vBoxTfList1;

    public static final Logger LOGGER = LogManager.getLogger(ScanController.class);
    @FXML
    private VBox vBoxCbList0;

    private final List<FieldModel> fieldModelEngList = new ArrayList();
    private final List<FieldModel> fieldModelRusList = new ArrayList();

    private final UpdaterUtil updaterUtil = new UpdaterUtil(this);
    private static Timer timer = new Timer();

    private ObservableList<String> data = FXCollections.observableArrayList("РЯДОВОЙ", "ЭКСПОРТ", "AS 50452",
            "MJY", "MP", "SB-B LO");
    @FXML
    private VBox vBoxCbList1;

    private Stage stage;
    @FXML
    private JFXSpinner loadSpinner;

    @FXML
    public void initialize() {

        initJFXDrawer();

        lblFio.setText(Constants.FIO_VIEW);
        TextFieldService.setTextFieldNumeric(barcodeSpool, 12);

        /**для наведения фокуса на определенное поле*/
        Platform.runLater(() -> barcodeSpool.requestFocus());

        initClock();

        cbConsumer.setItems(data);
        cbConsumer.getSelectionModel().select(0);

        listAlert.add("Выберите нужные параметры для формирования QR-CODE!\n" +
                "Удостоверьтесь,что отмеченные поля не пустые!");
        listAlert.add("Выберите нужные параметры для формирования этикетки!\n" +
                "Удостоверьтесь,что отмеченные поля не пустые!");

        fieldModelEngList.add(new FieldModel(construct, cbConstruct, "", CellStyleOption.ENLARGED2));
        fieldModelEngList.add(new FieldModel(code, cbCode, "Code:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(lr, cbLr, "", CellStyleOption.ENLARGED));
        fieldModelEngList.add(new FieldModel(numberSpool, cbNumberSpool, "Bob.№:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(length, cbLength, "Length:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(part, cbPart, "Part №:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(lot, cbLot, "Lot №:", CellStyleOption.BASE));
//        fieldModelEngList.add(new FieldModel(typeSpool, cbTypeSpool, "", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(welds, cbWelds, "Welds:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(datePrint, cbDate, "Date:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(torsion, cbTorsion, "Torsion:", CellStyleOption.BASE));


        fieldModelRusList.add(new FieldModel(construct, cbConstruct, "", CellStyleOption.ENLARGED2));
        fieldModelRusList.add(new FieldModel(code, cbCode, "Код:", CellStyleOption.BASE));
        fieldModelRusList.add(new FieldModel(lr, cbLr, "", CellStyleOption.ENLARGED));
        fieldModelRusList.add(new FieldModel(numberSpool, cbNumberSpool, "№ кат.", CellStyleOption.BASE));
        fieldModelRusList.add(new FieldModel(welds, cbWelds, "Cварка:", CellStyleOption.BASE));
        fieldModelRusList.add(new FieldModel(datePrint, cbDate, "Дата:", CellStyleOption.BASE));

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

//        loadSpinner();
        //check timer:
        if (timer != null) {
            timer.cancel();
            timer = new Timer();
            timer.schedule(updaterUtil, 0, 20000);
        }

    }

    public void dateBetweenAction() {
        tableSpool.clear();
        if (dateStart.getDateTimeValue() == null && dateEnd.getDateTimeValue() == null) {
            dateStart.setDateTimeValue(LocalDateTime.now().with(LocalTime.MIN));
            dateEnd.setDateTimeValue(LocalDateTime.now().with(LocalTime.MAX));
        }
        List<TestLabel> testLabelListForDate = TestLabelRepository.getAllSpoolsBetween(
                "http://" + AppProperties.getHost() + "/api/allSpool/"
                        + dateStart.getDateTimeValue().with(LocalTime.MIN) + "/"
                        + dateEnd.getDateTimeValue().with(LocalTime.MAX));
        tableSpool.addAll(testLabelListForDate);
        tableView.setItems(tableSpool);
        filterTable();
        tabSpoolList.setText("Катушки c: " + dateStart.getDateTimeValue().with(LocalTime.MIN)
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + " по: " + dateEnd.getDateTimeValue()
                .with(LocalTime.MAX).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

    }

    public void loadSpinner() {
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(loadSpinner.progressProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(0.7),
                        new KeyValue(loadSpinner.progressProperty(), 0.7)
                ),
                new KeyFrame(
                        Duration.seconds(2),
                        new KeyValue(loadSpinner.progressProperty(), 1)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void clearTableAndDatePicker() {
        tableSpool.clear();
        dateStart.setDateTimeValue(null);
        dateEnd.setDateTimeValue(null);
        tabSpoolList.setText("Катушки");
    }

    public void initJFXDrawer() {
        try {
            VBox vBox = FXMLLoader.load(getClass().getResource(Constants.SIDE_MENU));
            drawer.setSidePane(vBox);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HamburgerSlideCloseTransition transitionTask = new HamburgerSlideCloseTransition(hamburgerMenu);
        transitionTask.setRate(-1);
        hamburgerMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            transitionTask.setRate(transitionTask.getRate() * -1);
            transitionTask.play();

            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });
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
        dateBetweenAction();
    }

    private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            lblDateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    public void choiceLabelAction() {
        choiceLabelType(cbConsumer.getValue());
        barcodeSpool.requestFocus();
    }

    //тип этикетки
    public void choiceLabelType(String typeLabel) {
        switch (typeLabel) {
            case ("РЯДОВОЙ"):
                unselectCheckBox();
                unDisabledCheckBox();
                cbConstruct.setSelected(true);
                cbLr.setSelected(true);
                if(!code.getText().trim().isEmpty()){
                    cbCode.setSelected(true);
                }
                cbNumberSpool.setSelected(true);
                cbDate.setSelected(true);
                cbPart.setDisable(true);
                cbLot.setDisable(true);
                cbTorsion.setDisable(true);
                cbWelds.setDisable(true);
                cbLength.setDisable(true);
                break;
            case ("ЭКСПОРТ"):
                unselectCheckBox();
                unDisabledCheckBox();
                cbConstruct.setSelected(true);
                cbLr.setSelected(true);
                cbNumberSpool.setSelected(true);
                cbDate.setSelected(true);
                break;
            case ("AS 50452"):
                unselectCheckBox();
                unDisabledCheckBox();
                cbConstruct.setSelected(true);
                cbCode.setSelected(true);
                cbLr.setSelected(true);
                cbNumberSpool.setSelected(true);
                cbDate.setSelected(true);
                cbWelds.setSelected(true);
                cbTorsion.setDisable(true);
                cbPart.setDisable(true);
                cbLot.setDisable(true);
                cbTorsion.setDisable(true);
                cbLength.setDisable(true);
                break;
            case ("MJY"):
                unselectCheckBox();
                unDisabledCheckBox();
                cbConstruct.setSelected(true);
                cbCode.setSelected(true);
                cbLr.setSelected(true);
                cbNumberSpool.setSelected(true);
                cbDate.setSelected(true);
                cbLot.setSelected(true);
                cbPart.setDisable(true);
                cbWelds.setDisable(true);
                cbTorsion.setDisable(true);
                cbLength.setDisable(true);
                cbLength.setDisable(true);
                break;
            case ("MP"):
                unselectCheckBox();
                unDisabledCheckBox();
                cbConstruct.setSelected(true);
                cbCode.setSelected(true);
                cbLr.setSelected(true);
                cbNumberSpool.setSelected(true);
                cbDate.setSelected(true);
                cbPart.setSelected(true);
                cbLot.setSelected(true);
                cbTorsion.setDisable(true);
                cbLength.setDisable(true);
                cbLength.setDisable(true);
                break;
            case ("SB-B LO"):
                unselectCheckBox();
                unDisabledCheckBox();
                cbConstruct.setSelected(true);
                cbCode.setSelected(true);
                cbLr.setSelected(true);
                cbNumberSpool.setSelected(true);
                cbDate.setSelected(true);
                cbLength.setSelected(true);
                cbPart.setSelected(true);
                cbLot.setSelected(true);
                cbWelds.setDisable(true);
                cbTorsion.setDisable(true);
        }
    }

    @FXML
    public void addSpool() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/modalAddSpool.fxml"));

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

        List<TestLabel> labelList = TestLabelRepository.getTestLabel
                        ("http://localhost:8097/api/label/spool/" + barcodeSpool.getText());

        if (labelList != null && labelList.isEmpty()) {
            stage.close();
            barcodeSpool.clear();
            barcodeSpool.requestFocus();
        } else {
            getInfoAction();
        }
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

    public void unDisabledCheckBox(){
        cbCode.setDisable(false);
        cbConstruct.setDisable(false);
        cbDate.setDisable(false);
        cbLr.setDisable(false);
        cbPart.setDisable(false);
        cbLot.setDisable(false);
        cbLength.setDisable(false);
        cbWelds.setDisable(false);
        cbNumberSpool.setDisable(false);
        cbTorsion.setDisable(false);
    }

    public void clearFields() {
        typeSpool.clear();
        code.clear();
        construct.clear();
        datePrint.clear();
        lr.clear();
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
        personalRope.clear();
        numberRopeMachine.clear();
        barcodeSpool.clear();
        tabInfoSpool.setText("Информация о катушке");

        barcodeSpool.requestFocus();
    }

    public File exportToExcel() {
        try {
            InputStream file = getClass().getClassLoader().getResourceAsStream("template/Export.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            int rowExcel = 4;

            List<FieldModel> fieldModels;
            String lastCellValue;

            if (cbConsumer.getValue().equals("РЯДОВОЙ")) {
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

    public File toFormQrCode() {
        try {
            InputStream file = getClass().getClassLoader().getResourceAsStream("template/QR-Code.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
//            XSSFWorkbook workbook = new XSSFWorkbook();
//            Sheet sheet = workbook.createSheet("QR-Code");

            StringBuilder codeBuilder = new StringBuilder();
            String imageFormat = "png";
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
//            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//            hints.put(EncodeHintType.CHARACTER_SET, "windows-1251");
            hints.put(EncodeHintType.MARGIN, 0);
//            hints.put(EncodeHintType.MARGIN, -1);

            List<FieldModel> fieldModels;
            String lastCellValue;

            if (cbConsumer.getValue().equals("РЯДОВОЙ")) {
                fieldModels = fieldModelRusList;
                lastCellValue = "Сделано в Беларуси";
                LOGGER.info("Selected Russian-language label:");
                codeBuilder.append("Конструкция:");
            } else {
                fieldModels = fieldModelEngList;
                lastCellValue = "Made in Belarus";
                LOGGER.info("Selected English-language label");
                codeBuilder.append("Construct:");
            }

            for (FieldModel field : fieldModels) {
                if (field.getCheckBox().isSelected()) {
                    if (field.getCheckBox().equals(cbConstruct)) {
                        codeBuilder.append(field.getTextField().getText()).append("\n");
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
            LOGGER.info("Print/View QR-CODE:{} - {}",
                    Arrays.toString(codeBuilder.toString().split("\n")),
                    Constants.FIO_VIEW);
//            System.out.println(codeBuilder);
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
            //Create an anchor that is attached to the worksheet(создаем привязку, прикрепленную к листу)
            ClientAnchor anchor = helper.createClientAnchor();
            //устанавливаем верхний левый угол для изображения
            anchor.setCol1(0);
            anchor.setRow1(0);
            //Создаем изображение
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            //Восстанавливаем исходный размер изображения
            pict.resize();

            //для подгонки нужного размера 40х30:
            Row thirdRow = sheet.getRow(2);
            Row fourthRow = sheet.getRow(3);
            thirdRow.setHeightInPoints(9);
            fourthRow.setHeightInPoints(8);

            //2230
            sheet.setColumnWidth(1, 2300);

            file.close();

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

    public void generateQrCode() throws IOException {
        if (NodeUtils.checkSelectedFields(vBoxCbList0, vBoxCbList1) &&
                NodeUtils.checkEmptyFields(vBoxTfList0, vBoxTfList1)) {
            Desktop.getDesktop().open(toFormQrCode());
        } else {
            TextFieldService.alertWarning(listAlert.get(0));
        }
    }

    public void printQrCode() throws IOException {
        if (NodeUtils.checkSelectedFields(vBoxCbList0, vBoxCbList1) &&
                NodeUtils.checkEmptyFields(vBoxTfList0, vBoxTfList1)) {
            Desktop.getDesktop().print(toFormQrCode());
        } else {
            TextFieldService.alertWarning(listAlert.get(0));
        }
    }

    public void printLabel() throws IOException {
        if (NodeUtils.checkSelectedFields(vBoxCbList0, vBoxCbList1) &&
                NodeUtils.checkEmptyFields(vBoxTfList0, vBoxTfList1)) {
            Desktop.getDesktop().print(exportToExcel());
        } else {
            TextFieldService.alertWarning(listAlert.get(1));
        }
    }

    public void toFormLabel() throws IOException {
        if (NodeUtils.checkSelectedFields(vBoxCbList0, vBoxCbList1) &&
                NodeUtils.checkEmptyFields(vBoxTfList0, vBoxTfList1)) {
            Desktop.getDesktop().open(exportToExcel());
        } else {
            TextFieldService.alertWarning(listAlert.get(1));
        }
    }

    public void initializeTableColumns() {
        tcNumberSpool.setCellValueFactory(new PropertyValueFactory<>("numberSpool"));
        tcTypeSpool.setCellValueFactory(new PropertyValueFactory<>("typeSpool"));
        tcCode.setCellValueFactory(new PropertyValueFactory<>("consumerCode"));
        tcConstruct.setCellValueFactory(new PropertyValueFactory<>("construct"));
        tcDateCreate.setCellValueFactory(new PropertyValueFactory<>("dateCreate"));
        tcLR.setCellValueFactory(new PropertyValueFactory<>("rl"));
        tcPart.setCellValueFactory(new PropertyValueFactory<>("part"));
        tcLot.setCellValueFactory(new PropertyValueFactory<>("lot"));
        tcWelds.setCellValueFactory(new PropertyValueFactory<>("welds"));
        tcPersonalRope.setCellValueFactory(new PropertyValueFactory<>("personalRope"));
        tcNumberRopeMachine.setCellValueFactory(new PropertyValueFactory<>("numberRopeMachine"));
        tcStraight300.setCellValueFactory(new PropertyValueFactory<>("straightforwardness300"));
        tcStraight600_0.setCellValueFactory(new PropertyValueFactory<>("straightforwardness600_0"));
        tcStraight600_1.setCellValueFactory(new PropertyValueFactory<>("straightforwardness600_1"));
        tcStraight600_2.setCellValueFactory(new PropertyValueFactory<>("straightforwardness600_2"));
        tcStraight600_3.setCellValueFactory(new PropertyValueFactory<>("straightforwardness600_3"));
        tcStraight600_4.setCellValueFactory(new PropertyValueFactory<>("straightforwardness600_4"));
        tcStraight600_5.setCellValueFactory(new PropertyValueFactory<>("straightforwardness600_5"));
        tcStraight600Avg.setCellValueFactory(new PropertyValueFactory<>("straightforwardness600Avg"));
        tcTorsion.setCellValueFactory(new PropertyValueFactory<>("torsion"));

/*        //08.12.2021 - Закомментировано, т.к. часть данных убраны с пользовательского ввода для ЛИ м/к
        tcLength.setCellValueFactory(new PropertyValueFactory<>("length"));
        tcNumbWeldingMachine.setCellValueFactory(new PropertyValueFactory<>("numberWeldingMachine"));
        tcDateRope.setCellValueFactory(new PropertyValueFactory<>("dateRope"));
        tcTorsionRope.setCellValueFactory(new PropertyValueFactory<>("torsRope"));
        tcStraightRope.setCellValueFactory(new PropertyValueFactory<>("straightforwardnessRope"));
*/
    }

    public void clearAction() {
        unselectCheckBox();
        clearFields();
    }

    private Task taskWorker(int seconds){
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for(int i=0; i<seconds; i++){
                    updateProgress(i+325,seconds);
                    Thread.sleep(2);
                }
                return null;
            }
        };
    }

    public void getInfoAction() {

        Task task = taskWorker(500);
        loadSpinner.setVisible(true);
        loadSpinner.progressProperty().unbind();
        loadSpinner. progressProperty().bind(task.progressProperty());

        Thread thread = new Thread(task);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();


        if (!barcodeSpool.getText().isEmpty()) {
            task.setOnSucceeded(event -> {
                loadSpinner.setVisible(false);
                List<TestLabel> testLabelList = TestLabelRepository.getTestLabel("http://localhost:8097/api/label/spool/"
                        + barcodeSpool.getText());

                if (testLabelList != null && testLabelList.isEmpty()) {
                    Constants.SPOOL_NUMBER = barcodeSpool.getText();
                    addSpool();
                    return;
                }
                LOGGER.info("Spool number scan: " + barcodeSpool.getText());

                TestLabel label = testLabelList.get(0);
//            System.out.println(label);
                LocalDate dateCurrentPrintLabel = LocalDate.now();

                typeSpool.setText(label.getTypeSpool() != null ? String.valueOf(label.getTypeSpool()) : "");
                code.setText(label.getConsumerCode() != null ? String.valueOf(label.getConsumerCode()) : "");
                construct.setText(label.getConstruct() != null ? (label.getConstruct()) : "");
                numberSpool.setText(label.getNumberSpool() != null ? (label.getNumberSpool()) : "");
//            date_create.setText(label.getDate_create() != null ? DateUtil.format(label.getDate_create()) : "");
                datePrint.setText(DateUtil.format(dateCurrentPrintLabel));
                lr.setText(label.getRl() != null ? label.getRl() : "");
                part.setText(label.getPart() != null ? label.getPart() : "");
                lot.setText(label.getLot() != null ? String.valueOf(label.getLot()) : "");
                length.setText(label.getLength() != null ? String.valueOf(label.getLength()) : "");
                welds.setText(label.getWelds() != null ? String.valueOf(label.getWelds()) : "0");
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
                numberRopeMachine.setText(label.getNumberRopeMachine() != null ? String.valueOf(label.getNumberRopeMachine()) : "");
                personalRope.setText(label.getPersonalRope() != null ? label.getPersonalRope() : "");
//            torsRope.setText(label.getTorsRope() != null ? String.valueOf(label.getTorsRope()) : "");
//            straightforwardnessRope.setText(label.getStraightforwardnessRope() != null ?
//                    String.valueOf(label.getStraightforwardnessRope()) : "");

                barcodeSpool.getStylesheets().clear();
                barcodeSpool.getStylesheets().add("/css/jfx_success.css");

                choiceLabelAction();
//            cbCode.setSelected(label.getConsumerCode() != null);

                tabInfoSpool.setText("Информация о катушке: №" + numberSpool.getText());
                barcodeSpool.setText("");

            });



        } else if (barcodeSpool.getText().isEmpty()) {
            barcodeSpool.getStylesheets().clear();
            barcodeSpool.getStylesheets().add("/css/jfx_error.css");
            clearFields();
            unselectCheckBox();
            TextFieldService.alertWarning("Поле ввода пустое!\nОтсканируйте штрих-код катушки");
        }

    }

    public void scanByKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            getInfoAction();
        }
    }
}
