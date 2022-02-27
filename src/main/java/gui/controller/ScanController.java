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
import gui.repository.BarcodeLabelRepository;
import gui.repository.TemplatesLabelsRepository;
import gui.repository.TableSpoolsRepository;
import gui.util.*;
import gui.util.DateUtil;
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

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;


public class ScanController {

    @FXML
    private AnchorPane anchorPaneMain;
    private final ObservableList<TableSpools> tableSpool = FXCollections.observableArrayList();
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
    private Label lblContainer;
    @FXML
    private TextField tfTypeSpool;
    @FXML
    private TextField tfCode;
    @FXML
    private TextField tfConstruct;
    @FXML
    private TextField tfDatePrint;
    @FXML
    private TextField tfLR;
    @FXML
    private TextField tfPart;
    @FXML
    private TextField tfLot;
    @FXML
    private TextField tfContainer;
    @FXML
    private TextField tfLength;
    @FXML
    private TextField tfWelds;

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

    private TextField tfNumberSpool;

    @FXML
    private TextField tfStraightforwardness600_0;

    @FXML
    private TextField tfStraightforwardness600_1;

    @FXML
    private TextField tfStraightforwardness600_2;

    @FXML
    private TextField tfStraightforwardness600_3;

    @FXML
    private TextField tfStraightforwardness600_4;

    @FXML
    private TextField tfStraightforwardness600_5;

    @FXML
    private TextField tfStraightforwardness600Avg;

    @FXML
    private TextField tfTorsion;

/*    @FXML
    private TextField torsRope;

    @FXML
    private Label lbl_torsRope;

    @FXML
    private TextField straightforwardnessRope;*/

    @FXML
    private TextField tfPersonalRope;
    @FXML
    private TextField tfNumberRopeMachine;
    @FXML
    private TextField tfStraightforwardness300;
    @FXML
    private CheckBox cbTypeSpool;
    @FXML
    private CheckBox cbCode;
    @FXML
    private CheckBox cbConstruct;
    @FXML
    private CheckBox cbDate;
    @FXML
    private CheckBox cbLR;
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
    @FXML
    private CheckBox cbContainer;
    @FXML
    private CheckBox cbPersonal;
    @FXML
    private CheckBox cbRopeMachine;


//    @FXML
//    private CheckBox cb_torsRope;

//    @FXML
//    private CheckBox cb_straightRope;

    @FXML
    private TableView<TableSpools> tableView;
    @FXML
    private TableColumn<TableSpools, String> tcNumberSpool;
    @FXML
    private TableColumn<TableSpools, String> tcTypeSpool;
    @FXML
    private TableColumn<TableSpools, String> tcCodeConsumer;
    @FXML
    private TableColumn<TableSpools, String> tcCodeProvider;
    @FXML
    private TableColumn<TableSpools, String> tcConstruct;
    @FXML
    private TableColumn<TableSpools, LocalDateTime> tcDateCreate;
    @FXML
    private TableColumn<TableSpools, String> tcLR;
    @FXML
    private TableColumn<TableSpools, String> tcPart;
    @FXML
    private TableColumn<TableSpools, Integer> tcLot;
    @FXML
    private TableColumn<TableSpools, Integer> tcContainer;

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
    private TableColumn<TableSpools, Integer> tcWelds;
    @FXML
    private TableColumn<TableSpools, String> tcPersonalRope;
    @FXML
    private TableColumn<TableSpools, Double> tcNumberRopeMachine;
    @FXML
    private TableColumn<TableSpools, Double> tcStraight300;
    @FXML
    private TableColumn<TableSpools, Double> tcStraight600_0;
    @FXML
    private TableColumn<TableSpools, Double> tcStraight600_1;
    @FXML
    private TableColumn<TableSpools, Double> tcStraight600_2;
    @FXML
    private TableColumn<TableSpools, Double> tcStraight600_3;
    @FXML
    private TableColumn<TableSpools, Double> tcStraight600_4;
    @FXML
    private TableColumn<TableSpools, Double> tcStraight600_5;
    @FXML
    private TableColumn<TableSpools, Double> tcStraight600Avg;
    @FXML
    private TableColumn<TableSpools, Double> tcTorsion;
    @FXML
    private TextField tfFilterField;
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
    @FXML
    private VBox vBoxCbList0;
    public static final Logger LOGGER = LogManager.getLogger(ScanController.class);
    @FXML
    private VBox vBoxCbList1;
    @FXML
    private Label lblLoad;
    @FXML
    private Label lblWait;
    @FXML
    private Label lblDataProcessing;

    private final List<FieldModel> fieldModelEngList = new ArrayList();
    private final List<FieldModel> fieldModelRusList = new ArrayList();

    private final UpdaterUtil updaterUtil = new UpdaterUtil(this);
    private static Timer timer = new Timer();

    private ObservableList<String> data = FXCollections.observableArrayList("РУС", "ENG");

    @FXML
    private JFXSpinner loadSpinner;
    private Map<String, LabelField> labelFieldMap;

    @FXML
    public void initialize() {
        labelFieldMap = createLabelFieldMap();
        initJFXDrawer();
        loadSpinner.setProgress(-1);

        lblFio.setText(Constants.FIO_VIEW);
        TextFieldUtil.setTextFieldNumeric(barcodeSpool, 12);

        /**для наведения фокуса на определенное поле*/
        Platform.runLater(() -> barcodeSpool.requestFocus());

        initClock();

        cbConsumer.setItems(data);
        cbConsumer.getSelectionModel().select(0);

        listAlert.add("Выберите нужные параметры для формирования QR-CODE!\n" +
                "Удостоверьтесь,что отмеченные поля не пустые!");
        listAlert.add("Выберите нужные параметры для формирования этикетки!\n" +
                "Удостоверьтесь,что отмеченные поля не пустые!");

        fieldModelEngList.add(new FieldModel(tfConstruct, cbConstruct, "", CellStyleOption.ENLARGED2));
        fieldModelEngList.add(new FieldModel(tfCode, cbCode, "Code:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(tfLR, cbLR, "", CellStyleOption.ENLARGED));
        fieldModelEngList.add(new FieldModel(tfNumberSpool, cbNumberSpool, "Bob.№:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(tfLength, cbLength, "Length:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(tfPart, cbPart, "Part №:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(tfLot, cbLot, "Lot №:", CellStyleOption.BASE));
//        fieldModelEngList.add(new FieldModel(typeSpool, cbTypeSpool, "", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(tfWelds, cbWelds, "Welds:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(tfDatePrint, cbDate, "Date:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(tfTorsion, cbTorsion, "Torsion:", CellStyleOption.BASE));


        fieldModelRusList.add(new FieldModel(tfConstruct, cbConstruct, "", CellStyleOption.ENLARGED2));
        fieldModelRusList.add(new FieldModel(tfCode, cbCode, "Код:", CellStyleOption.BASE));
        fieldModelRusList.add(new FieldModel(tfLR, cbLR, "", CellStyleOption.ENLARGED));
        fieldModelRusList.add(new FieldModel(tfNumberSpool, cbNumberSpool, "№ кат.", CellStyleOption.BASE));
        fieldModelRusList.add(new FieldModel(tfPart, cbPart, "№ партии:", CellStyleOption.BASE));
        fieldModelRusList.add(new FieldModel(tfWelds, cbWelds, "Cварка:", CellStyleOption.BASE));
        fieldModelRusList.add(new FieldModel(tfDatePrint, cbDate, "Дата:", CellStyleOption.BASE));

        initializeTableColumns();

        //Для отображения корректного времени в tableColumn dateCreate:
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        tcDateCreate.setCellFactory(column -> new TableCell<TableSpools, LocalDateTime>() {
            protected void updateItem(LocalDateTime dateTime, boolean empty) {
                super.updateItem(dateTime, empty);
                if (empty) {
                    setText(null);
                } else {
                    this.setText(formatter.format(dateTime));
                }
            }
        });

        //check timer:
        if (timer != null) {
            timer.cancel();
            timer = new Timer();
            timer.schedule(updaterUtil, 0, 10000);
        }
    }

    /**
     * Отобразить катушки за заданный период времени
     */
    public void dateBetweenAction() {
        tableSpool.clear();
        if (dateStart.getDateTimeValue() == null && dateEnd.getDateTimeValue() == null) {
            dateStart.setDateTimeValue(LocalDateTime.now().with(LocalTime.MIN));
            dateEnd.setDateTimeValue(LocalDateTime.now().with(LocalTime.MAX));
        }
        List<TableSpools> tableSpoolsListForDate = TableSpoolsRepository.getAllSpoolsBetween(
                "http://" + AppProperties.getHost() + "/api/allSpool/"
                        + dateStart.getDateTimeValue().with(LocalTime.MIN) + "/"
                        + dateEnd.getDateTimeValue().with(LocalTime.MAX));
        tableSpool.addAll(tableSpoolsListForDate);
        tableView.setItems(tableSpool);
        filterTable();
        tabSpoolList.setText("Катушки c: " + dateStart.getDateTimeValue().with(LocalTime.MIN)
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + " по: " + dateEnd.getDateTimeValue()
                .with(LocalTime.MAX).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

    }

    /**
     * Очистить таблицу и поля DatePickers
     */
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
                barcodeSpool.requestFocus();
            } else {
                drawer.open();
            }
        });
    }

    public void filterTable() {
        /**Обворачиваем ObservableList в FilteredList (initially display all data) */
        FilteredList<TableSpools> filteredData = new FilteredList<>(tableSpool, b -> true);

        /** Устанавливаем предикат фильтра всякий раз, когда фильтр изменяется: */
        tfFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tableSpools -> {
                // If filter text is empty, display all spools.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Сравниваем номер каждой катушки с текстом фильтра.
                String lowerCaseFilter = newValue.toLowerCase();

                if (tableSpools.getNumberSpool().toLowerCase().contains(lowerCaseFilter)) {
                    return true; //
                } else
                    return false;
            });
        });
        /** Обворачиваем FilteredList в SortedList: */
        SortedList<TableSpools> sortedData = new SortedList<>(filteredData);
        /** Привязываем компаратор SortedList к компаратору TableView: */
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        /** Добавляем в таблицу отфильтрованные данные: */
        tableView.setItems(sortedData);
    }

    public void refreshTable() {
        dateBetweenAction();
    }

    /**
     * Отображение текущего времени
     */
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
        barcodeSpool.requestFocus();
    }

    /**
     * Добавление новой катушки
     */
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
        new Thread(() -> {
            try {
                loadSpinner.setVisible(true);
                lblWait.setVisible(true);
                lblLoad.setVisible(true);
                barcodeSpool.setVisible(false);
                lblDataProcessing.setVisible(true);

                List<BarcodeLabel> barcodeLabelList = BarcodeLabelRepository.
                            getBarcodeLabel("http://localhost:8097/api/spool/" + barcodeSpool.getText());
                if (barcodeLabelList != null && barcodeLabelList.isEmpty()) {
//                if (barcodeSpool.getText().isEmpty()) {
                Platform.runLater(() -> {
                        stage.close();
//                        barcodeSpool.clear();
                        barcodeSpool.requestFocus();
                        clearAction();
                    });
                } else {
                    Platform.runLater(this::getInfoAction);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                loadSpinner.setVisible(false);
                lblWait.setVisible(false);
                lblLoad.setVisible(false);
                barcodeSpool.setVisible(true);
                lblDataProcessing.setVisible(false);
            }
        }).start();
    }

    public void unselectCheckBox() {
        cbTypeSpool.setSelected(false);
        cbCode.setSelected(false);
        cbConstruct.setSelected(false);
        cbDate.setSelected(false);
        cbLR.setSelected(false);
        cbPart.setSelected(false);
        cbLot.setSelected(false);
        cbLength.setSelected(false);
        cbWelds.setSelected(false);
        cbNumberSpool.setSelected(false);
//        cbStraight300.setSelected(false);
//        cbStraight600_1.setSelected(false);
//        cbStraight600_2.setSelected(false);
//        cbStraight600_3.setSelected(false);
//        cbStraight600_4.setSelected(false);
//        cbStraight600_5.setSelected(false);
//        cbStraight600Avg.setSelected(false);
        cbTorsion.setSelected(false);
        cbContainer.setSelected(false);
//        cb_torsRope.setSelected(false);
//        cb_straightRope.setSelected(false);
    }

    public void unDisabledCheckBox() {
        cbCode.setDisable(false);
        cbConstruct.setDisable(false);
        cbDate.setDisable(false);
        cbLR.setDisable(false);
        cbPart.setDisable(false);
        cbLot.setDisable(false);
        cbLength.setDisable(false);
        cbWelds.setDisable(false);
        cbNumberSpool.setDisable(false);
        cbTorsion.setDisable(false);
    }

    /**
     * Очистка TextFields
     */
    public void clearFields() {
        tfTypeSpool.clear();
        tfCode.clear();
        tfConstruct.clear();
        tfDatePrint.clear();
        tfLR.clear();
        tfPart.clear();
        tfLot.clear();
        tfLength.clear();
        tfWelds.clear();
        tfNumberSpool.clear();
//        tfStraightforwardness300.clear();
//        tfStraightforwardness600_0.clear();
//        tfStraightforwardness600_1.clear();
//        tfStraightforwardness600_2.clear();
//        tfStraightforwardness600_3.clear();
//        tfStraightforwardness600_4.clear();
//        tfStraightforwardness600_5.clear();
//        tfStraightforwardness600Avg.clear();
        tfTorsion.clear();
        tfPersonalRope.clear();
        tfNumberRopeMachine.clear();
        tfContainer.clear();
        barcodeSpool.clear();
        tabInfoSpool.setText("Информация о катушке");

        barcodeSpool.requestFocus();
    }

    /**
     * Формирование обычной бирки в Excel
     */
    public File exportToExcel() {
        try {
            InputStream file = getClass().getClassLoader().getResourceAsStream("template/Export.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            int rowExcel = 4;

            List<FieldModel> fieldModels;
            String lastCellValue;

            if (cbConsumer.getValue().equals("РУС")) {
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
                    } else if (field.getCheckBox().equals(cbLR)) {
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

    /**
     * Формирование QR-Code в Excel
     */
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

            if (cbConsumer.getValue().equals("РУС")) {
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

            //size 40x30:
            sheet.setColumnWidth(0, 2280);
            sheet.setColumnWidth(1, 2200);

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

    /**
     * Просмотр QR-Code
     */
    public void generateQrCode() throws IOException {
        if (NodeUtils.checkSelectedAndEmptyFields(vBoxCbList0, vBoxCbList1, vBoxTfList0, vBoxTfList1)) {
            Desktop.getDesktop().open(toFormQrCode());
        } else {
            TextFieldUtil.alertWarning(listAlert.get(0));
        }
    }

    /**
     * Печать QR-Code средствами Excel на активный принтер
     */
    public void printQrCode() throws IOException {
        if (NodeUtils.checkSelectedAndEmptyFields(vBoxCbList0, vBoxCbList1, vBoxTfList0, vBoxTfList1)) {
            Desktop.getDesktop().print(toFormQrCode());
        } else {
            TextFieldUtil.alertWarning(listAlert.get(0));
        }
    }

    /**
     * Печать обычной бирки средствами Excel на активный принтер
     */
    public void printLabel() throws IOException {
        if (NodeUtils.checkSelectedAndEmptyFields(vBoxCbList0, vBoxCbList1, vBoxTfList0, vBoxTfList1)) {
            Desktop.getDesktop().print(exportToExcel());
        } else {
            TextFieldUtil.alertWarning(listAlert.get(1));
        }
    }

    /**
     * Просмотр обычной бирки
     */
    public void toFormLabel() throws IOException {
        if (NodeUtils.checkSelectedAndEmptyFields(vBoxCbList0, vBoxCbList1, vBoxTfList0, vBoxTfList1)) {
            Desktop.getDesktop().open(exportToExcel());
        } else {
            TextFieldUtil.alertWarning(listAlert.get(1));
        }
    }

    /**
     * Инициализация таблицы
     */
    public void initializeTableColumns() {
        tcNumberSpool.setCellValueFactory(new PropertyValueFactory<>("numberSpool"));
        tcCodeProvider.setCellValueFactory(new PropertyValueFactory<>("code"));
        tcTypeSpool.setCellValueFactory(new PropertyValueFactory<>("typeSpool"));
        tcCodeConsumer.setCellValueFactory(new PropertyValueFactory<>("consumerCode"));
        tcConstruct.setCellValueFactory(new PropertyValueFactory<>("construct"));
        tcDateCreate.setCellValueFactory(new PropertyValueFactory<>("dateCreate"));
        tcLR.setCellValueFactory(new PropertyValueFactory<>("rl"));
        tcContainer.setCellValueFactory(new PropertyValueFactory<>("container"));
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

/*        //08.12.2021 -  часть данных убраны с пользовательского ввода для ЛИ м/к:
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

    /**
     * Получить информацию о катушке,если таковой в БД нет - добавить
     */

    public void getInfoAction() {
        new Thread(() -> {
            try {
                System.out.println(LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + " Method started");
                loadSpinner.setVisible(true);
                lblWait.setVisible(true);
                lblLoad.setVisible(true);
                barcodeSpool.setVisible(false);
                lblDataProcessing.setVisible(true);
                if (!barcodeSpool.getText().isEmpty()) {
                    List<BarcodeLabel> barcodeLabelList = BarcodeLabelRepository.
                            getBarcodeLabel("http://localhost:8097/api/spool/" + barcodeSpool.getText());
                    System.out.println(LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + " Response from the server");
                    if (barcodeLabelList != null && barcodeLabelList.isEmpty()) {
                        Constants.SPOOL_NUMBER = barcodeSpool.getText();
                        Platform.runLater(this::addSpool);
                        return;
                    } else {
                        Platform.runLater(() -> setCheckBoxesWithLabel(TemplatesLabelsRepository
                                .findByIdCode(Long.valueOf(barcodeLabelList.get(0).getCode())).get(0)));
                    }
                    LOGGER.info("Spool number scan: " + barcodeSpool.getText());

                    Platform.runLater(() -> {
                        BarcodeLabel label = barcodeLabelList.get(0);
//            System.out.println(label);
                        LocalDate dateCurrentPrintLabel = LocalDate.now();

                        tfTypeSpool.setText(label.getTypeSpool() != null ? String.valueOf(label.getTypeSpool()) : "");
                        tfCode.setText(label.getConsumerCode() != null ? String.valueOf(label.getConsumerCode()) : "");
                        tfConstruct.setText(label.getConstruct() != null ? (label.getConstruct()) : "");
                        tfNumberSpool.setText(label.getNumberSpool() != null ? (label.getNumberSpool()) : "");
                        //            date_create.setText(label.getDate_create() != null ? DateUtil.format(label.getDate_create()) : "");
                        tfDatePrint.setText(DateUtil.format(dateCurrentPrintLabel));
                        tfLR.setText(label.getRl() != null ? label.getRl() : "");
                        tfPart.setText(label.getPart() != null ? label.getPart() : "");
                        tfLot.setText(label.getLot() != null ? String.valueOf(label.getLot()) : "");
                        tfContainer.setText(label.getContainer() != null ? String.valueOf(label.getContainer()): "");
                        tfLength.setText(label.getLength() != null ? String.valueOf(label.getLength()) : "");
                        tfWelds.setText(label.getWelds() != null ? String.valueOf(label.getWelds()) : "0");
//                        tfStraightforwardness600Avg.setText(label.getStraightforwardness600Avg() != null ?
//                                String.valueOf(label.getStraightforwardness600Avg()) : "");
                        tfTorsion.setText(label.getTorsion() != null ? String.valueOf(label.getTorsion()) : "");
                        tfNumberRopeMachine.setText(label.getNumberRopeMachine() != null ?
                                String.valueOf(label.getNumberRopeMachine()) : "");
                        tfPersonalRope.setText(label.getPersonalRope() != null ? label.getPersonalRope() : "");
                        //            torsRope.setText(label.getTorsRope() != null ? String.valueOf(label.getTorsRope()) : "");
                        //            straightforwardnessRope.setText(label.getStraightforwardnessRope() != null ?
                        //                    String.valueOf(label.getStraightforwardnessRope()) : "");
                    });
                    barcodeSpool.getStylesheets().clear();
                    barcodeSpool.getStylesheets().add("/css/jfx_success.css");
                    Platform.runLater(() -> {
                        choiceLabelAction();
//                        cbCode.setSelected(label.getConsumerCode() != null);
                        tabInfoSpool.setText("Информация о катушке: №" + tfNumberSpool.getText());
                    });
                    barcodeSpool.setText("");
                    System.out.println(LocalDateTime.now().
                            format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + " Method ended");
                } else if (barcodeSpool.getText().isEmpty()) {
                    barcodeSpool.getStylesheets().clear();
                    barcodeSpool.getStylesheets().add("/css/jfx_error.css");
                    barcodeSpool.getStylesheets().add("/css/jfx_success.css");
                    Platform.runLater(() -> {
                        clearFields();
                        unselectCheckBox();
                        TextFieldUtil.alertWarning("Поле для cчитывания штрих-кода пустое!" +
                                "\nПожалуйста, отсканируйте штрих-код катушки!");
                    });
                }
                System.out.println(LocalDateTime.now() + " Task succeeded. Setting spinner false");
                loadSpinner.setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                loadSpinner.setVisible(false);
                lblWait.setVisible(false);
                lblLoad.setVisible(false);
                lblDataProcessing.setVisible(false);
                barcodeSpool.setVisible(true);
            }
        }).start();
        barcodeSpool.requestFocus();
    }

    public void scanByKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            getInfoAction();
        }
    }

    /**
     * Метод для выборки компонентов(TextField,CheckBox) согласно заданному шаблону этикетки
     */
    private void setCheckBoxesWithLabel(TemplatesLabels templatesLabels) {
        try {
//            for (Field field : templatesLabels.getClass().getFields()) {
//                if (labelFieldMap.containsKey(field.getName())) {
//                    LabelField labelField = labelFieldMap.get(field.getName());
//                    labelField.getLabelCheckBox().setSelected((Boolean) field.get(templatesLabels));
//                }
//            }
            Field[] fields = templatesLabels.getClass().getFields();
            for (Map.Entry<String, LabelField> entry : labelFieldMap.entrySet()) {
                Field field = Arrays.stream(fields).filter(f -> f.getName().equals(entry.getKey())).findFirst().orElse(null);
                if (field == null) {
                    entry.getValue().getLabelTextField().setEditable(false);
                    entry.getValue().getLabelCheckBox().setDisable(false);
                } else {
                    entry.getValue().getLabelTextField().setEditable((Boolean) field.get(templatesLabels));
                    entry.getValue().getLabelCheckBox().setDisable(!(Boolean) field.get(templatesLabels));
                    entry.getValue().getLabelCheckBox().setSelected((Boolean) field.get(templatesLabels));
                }
            }
            if (templatesLabels.getLanguageLabel()) {
                cbConsumer.setValue("ENG");
                cbTorsion.setDisable(false);
            } else {
                cbTorsion.setDisable(true);
                cbConsumer.setValue("РУС");
            }
        } catch (Exception e) {
            System.out.println("Field probably has private access");
            e.printStackTrace();
        }
    }

    /**
     * Мапа(ассоциативный массив) для сопоставления номера катушки с таблицей шаблонов
     */
    private Map<String, LabelField> createLabelFieldMap() {
        Map<String, LabelField> labelFields = new HashMap<>();
        labelFields.put("construct", new LabelField(cbConstruct, tfConstruct));
        labelFields.put("code", new LabelField(cbCode, tfCode));
        labelFields.put("lr", new LabelField(cbLR, tfLR));
        labelFields.put("numberSpool", new LabelField(cbNumberSpool, tfNumberSpool));
        labelFields.put("part", new LabelField(cbPart, tfPart));
        labelFields.put("lot", new LabelField(cbLot, tfLot));
        labelFields.put("datePrint", new LabelField(cbDate, tfDatePrint));
        labelFields.put("welds", new LabelField(cbWelds, tfWelds));
        labelFields.put("lengthSpool", new LabelField(cbLength, tfLength));

        return labelFields;
    }
}
