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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tornadofx.control.DateTimePicker;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


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
    private Label lblTorsion;
    @FXML
    private Label lblPersonalRope;

    @FXML
//    private TextField personalRope;
    private TextField tfNumberSpool;
    @FXML
    private TextField tfTorsion;
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
    private CheckBox cbTorsion;
    @FXML
    private CheckBox cbContainer;
    @FXML
    private CheckBox cbPersonal;
    @FXML
    private CheckBox cbRopeMachine;
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

/*  //08.12.2021 - ????????????????????????????????, ??.??. ?????????? ???????????? ???????????? ?? ?????????????????????????????????? ?????????? ?????? ???? ??/??
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
    private TableColumn<TableSpools, String> tcLength;
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
//    private JFXComboBox<String> cbPrint;
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
    @FXML
    private JFXSpinner loadSpinnerTable;

    private final List<FieldModel> fieldModelEngList = new ArrayList();
    private final List<FieldModel> fieldModelRusList = new ArrayList();

    private final UpdaterUtil updaterUtil = new UpdaterUtil(this);
    private static Timer timer = new Timer();

    private ObservableList<String> data = FXCollections.observableArrayList("??????", "ENG");

    @FXML
    private JFXSpinner loadSpinner;
    private Map<String, LabelField> labelFieldMap;

    private  LocalDateTime startDateTime ;
    private ExecutorService executorService;
    private CountDownLatch countDownLatch;

    @FXML
    public void initialize() {
        copyValueFromTable();

        labelFieldMap = createLabelFieldMap();
        initJFXDrawer();

        loadSpinner.setProgress(-1);

        lblFio.setText(Constants.FIO_VIEW);
        TextFieldUtil.setTextFieldNumeric(barcodeSpool, 12);

        /**?????? ?????????????????? ???????????? ???? ???????????????????????? ????????*/
        Platform.runLater(() -> barcodeSpool.requestFocus());

        initClock();

        cbConsumer.setItems(data);
        cbConsumer.getSelectionModel().select(0);

        listAlert.add("???????????????? ???????????? ?????????????????? ?????? ???????????????????????? QR-CODE!\n" +
                "????????????????????????????,?????? ???????????????????? ???????? ???? ????????????!");
        listAlert.add("???????????????? ???????????? ?????????????????? ?????? ???????????????????????? ????????????????!\n" +
                "????????????????????????????,?????? ???????????????????? ???????? ???? ????????????!");

        fieldModelEngList.add(new FieldModel(tfConstruct, cbConstruct, "", CellStyleOption.ENLARGED2));
        fieldModelEngList.add(new FieldModel(tfCode, cbCode, "Code:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(tfLR, cbLR, "", CellStyleOption.ENLARGED));
        fieldModelEngList.add(new FieldModel(tfNumberSpool, cbNumberSpool, "Bob.???:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(tfLength, cbLength, "Length:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(tfPart, cbPart, "Part ???:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(tfLot, cbLot, "Lot ???:", CellStyleOption.BASE));
//        fieldModelEngList.add(new FieldModel(typeSpool, cbTypeSpool, "", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(tfWelds, cbWelds, "Welds:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(tfDatePrint, cbDate, "Date:", CellStyleOption.BASE));
        fieldModelEngList.add(new FieldModel(tfTorsion, cbTorsion, "Torsion:", CellStyleOption.BASE));


        fieldModelRusList.add(new FieldModel(tfConstruct, cbConstruct, "", CellStyleOption.ENLARGED2));
        fieldModelRusList.add(new FieldModel(tfCode, cbCode, "??????:", CellStyleOption.BASE));
        fieldModelRusList.add(new FieldModel(tfLR, cbLR, "", CellStyleOption.ENLARGED));
        fieldModelRusList.add(new FieldModel(tfNumberSpool, cbNumberSpool, "??? ??????.", CellStyleOption.BASE));
        fieldModelRusList.add(new FieldModel(tfPart, cbPart, "??? ????????????:", CellStyleOption.BASE));
        fieldModelRusList.add(new FieldModel(tfWelds, cbWelds, "C??????????:", CellStyleOption.BASE));
        fieldModelRusList.add(new FieldModel(tfDatePrint, cbDate, "????????:", CellStyleOption.BASE));

        initializeTableColumns();

        //?????? ?????????????????????? ?????????????????????? ?????????????? ?? tableColumn dateCreate:
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
     * ???????????????????? ?????????????? ???? ???????????????? ???????????? ??????????????
     */
    public void dateBetweenAction() {
        new Thread(() -> {
            try {
                loadSpinnerTable.setVisible(true);
                tableSpool.clear();
                Platform.runLater(() -> {
                    tableView.setPlaceholder(new Text("???????????????? ???????? ????????????????..."));
                    tableView.getPlaceholder().setStyle("-fx-font-size: 16; -fx-font-family: 'Comic Sans MS';");
                });
                if (dateStart.getDateTimeValue() == null && dateEnd.getDateTimeValue() == null) {
                    dateStart.setDateTimeValue(LocalDateTime.now().with(LocalTime.MIN));
                    dateEnd.setDateTimeValue(LocalDateTime.now().with(LocalTime.MAX));
                }
                List<TableSpools> tableSpoolsListForDate = TableSpoolsRepository
                        .findAllByDateCreateBetween(dateStart.getDateTimeValue().with(LocalTime.MIN),
                                                    dateEnd.getDateTimeValue().with(LocalTime.MAX));
                tableSpool.addAll(tableSpoolsListForDate);
                Platform.runLater(() -> {
                    tableView.setItems(tableSpool);
                    filterTable();
                    tabSpoolList.setText("?????????????? c: " + dateStart.getDateTimeValue().with(LocalTime.MIN)
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + " ????: " + dateEnd.getDateTimeValue()
                            .with(LocalTime.MAX).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
                });
                loadSpinnerTable.setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                loadSpinnerTable.setVisible(false);
                Platform.runLater(() -> {
                    tableView.setPlaceholder(new Text("???? ?????????????? ???????????? ?????????????? ???? ????????????????????"));
                    tableView.getPlaceholder().setStyle("-fx-font-size: 16; -fx-font-family: 'Comic Sans MS';");
                });
            }
        }).start();
    }

    /**
     * ???????????????? ?????????????? ?? ???????? DatePickers
     */
    public void clearTableAndDatePicker() {
        tableSpool.clear();
        dateStart.setDateTimeValue(null);
        dateEnd.setDateTimeValue(null);
        tabSpoolList.setText("??????????????");
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
        /**???????????????????????? ObservableList ?? FilteredList (initially display all data) */
        FilteredList<TableSpools> filteredData = new FilteredList<>(tableSpool, b -> true);

        /** ?????????????????????????? ???????????????? ?????????????? ???????????? ??????, ?????????? ???????????? ????????????????????: */
        tfFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tableSpools -> {
                // ???????? ?????????? ?????????????? ????????, ???????????????????? ?????? ??????????????.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // ???????????????????? ?????????? ???????????? ?????????????? ?? ?????????????? ??????????????.
                String lowerCaseFilter = newValue.toLowerCase();

                if (tableSpools.getNumberSpool() != null &&
                        tableSpools.getNumberSpool().toLowerCase().contains(lowerCaseFilter) ) {
                    return true; //
                } else
                    return false;
            });
        });
        /** ???????????????????????? FilteredList ?? SortedList: */
        SortedList<TableSpools> sortedData = new SortedList<>(filteredData);
        /** ?????????????????????? ???????????????????? SortedList ?? ?????????????????????? TableView: */
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        /** ?????????????????? ?? ?????????????? ?????????????????????????????? ????????????: */
        tableView.setItems(sortedData);
    }

    public void refreshTable() {
        dateBetweenAction();
    }

    /**
     * ?????????????????????? ???????????????? ??????????????
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
     * ???????????????????? ?????????? ??????????????
     *
     */
    @FXML
    public void addSpool(LocalDateTime dateStart, LocalDateTime dateEnd) {
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
        stage.setTitle("???????? ?????????? ??????????????");
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

                List<BarcodeLabel> barcodeLabelList =
                        BarcodeLabelRepository.findByNumberSpoolBetween(dateStart,dateEnd,barcodeSpool.getText());
                    if (barcodeLabelList != null && barcodeLabelList.isEmpty()) {
                        Platform.runLater(() -> {
                            stage.close();
                            barcodeSpool.requestFocus();
                            clearAction();
                        });
                    } else { Platform.runLater(() -> getInfoAction(dateStart, dateEnd)); }
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
//        cbTypeSpool.setSelected(false);
        cbCode.setSelected(false);
        cbConstruct.setSelected(false);
        cbDate.setSelected(false);
        cbLR.setSelected(false);
        cbPart.setSelected(false);
        cbLot.setSelected(false);
        cbLength.setSelected(false);
        cbWelds.setSelected(false);
        cbNumberSpool.setSelected(false);
        cbTorsion.setSelected(false);
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
     * ?????????????? TextFields
     */
    public void clearFields() {
//        tfTypeSpool.clear();
        tfCode.clear();
        tfConstruct.clear();
        tfDatePrint.clear();
        tfLR.clear();
        tfPart.clear();
        tfLot.clear();
        tfLength.clear();
        tfWelds.clear();
        tfNumberSpool.clear();
        tfTorsion.clear();
        barcodeSpool.clear();
        tabInfoSpool.setText("???????????????????? ?? ??????????????");

        barcodeSpool.requestFocus();
    }

    /**
     * ???????????????????????? ?????????????? ?????????? ?? Excel
     */
    public File exportToExcel() {
        try {
            InputStream file = getClass().getClassLoader().getResourceAsStream("template/Export.xlsm");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            int rowExcel = 4;

            List<FieldModel> fieldModels;
            String lastCellValue;

            if (cbConsumer.getValue().equals("??????")) {
                fieldModels = fieldModelRusList;
                lastCellValue = "?????????????? ?? ????????????????";
                System.out.println("?????????????? LabelRus");
            } else {
                fieldModels = fieldModelEngList;
                lastCellValue = "Made in Belarus";
                System.out.println("?????????????? LabelEng");
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
                        row.setHeightInPoints(11);
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

            //???????????????????? ?????????????? ???????????? ?? ????????????????:
//            CellRangeAddress region = new CellRangeAddress(0, rowExcel, 0, 1);
//            RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
//            RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
//            RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
//            RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);

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
     * ???????????????????????? QR-Code ?? Excel
     */
    public File toFormQrCode() {
        try {
            InputStream file = getClass().getClassLoader().getResourceAsStream("template/QR-Code.xlsm");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
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

            if (cbConsumer.getValue().equals("??????")) {
                fieldModels = fieldModelRusList;
                lastCellValue = "?????????????? ?? ????????????????";
                LOGGER.info("Selected Russian-language label:");
                codeBuilder.append("??????????????????????:");
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

            //width X heigt = 117 X 117 ???????? 133 X 133
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
            //???????????????? ???????????????????? InputStream ?????? byte [].
            byte[] bytes = IOUtils.toByteArray(inputStream);
            //?????????????????? ???????????????? ?? workbook
            int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            //?????????????????? ?????????????? ??????????
            inputStream.close();

            //Creates the top-level drawing patriarch(?????????????? ?????????????? ???????????????? ????????????)
            Drawing drawing = sheet.createDrawingPatriarch();
            //Returns an object that handles instantiating concrete classes()
            CreationHelper helper = workbook.getCreationHelper();
            //Create an anchor that is attached to the worksheet(?????????????? ????????????????, ?????????????????????????? ?? ??????????)
            ClientAnchor anchor = helper.createClientAnchor();
            //?????????????????????????? ?????????????? ?????????? ???????? ?????? ??????????????????????
            anchor.setCol1(0);
            anchor.setRow1(0);
            //?????????????? ??????????????????????
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            //?????????????????????????????? ???????????????? ???????????? ??????????????????????
            pict.resize();
            //?????? ???????????????? ?????????????? ?????????????? 40??30:
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
     * ???????????????? QR-Code
     */
    public void generateQrCode() throws IOException {
        if (NodeUtils.checkSelectedAndEmptyFields(vBoxCbList0, vBoxCbList1, vBoxTfList0, vBoxTfList1)) {
            Desktop.getDesktop().open(toFormQrCode());
        } else {
            TextFieldUtil.alertWarning(listAlert.get(0));
        }
    }

    /**
     * ???????????? QR-Code ???????????????????? Excel ???? ???????????????? ??????????????
     */
    public void printQrCode() throws IOException {
        if (NodeUtils.checkSelectedAndEmptyFields(vBoxCbList0, vBoxCbList1, vBoxTfList0, vBoxTfList1)) {
            Desktop.getDesktop().print(toFormQrCode());
            } else {
            TextFieldUtil.alertWarning(listAlert.get(0));
        }
    }

    /**
     * ???????????? ?????????????? ?????????? ???????????????????? Excel ???? ???????????????? ??????????????
     */
    public void printLabel() throws IOException {
        if (NodeUtils.checkSelectedAndEmptyFields(vBoxCbList0, vBoxCbList1, vBoxTfList0, vBoxTfList1)) {
            Desktop.getDesktop().print(exportToExcel());
        } else {
            TextFieldUtil.alertWarning(listAlert.get(1));
        }
    }

    /**
     * ???????????????? ?????????????? ??????????
     */
    public void toFormLabel() throws IOException {
        if (NodeUtils.checkSelectedAndEmptyFields(vBoxCbList0, vBoxCbList1, vBoxTfList0, vBoxTfList1)) {
            Desktop.getDesktop().open(exportToExcel());
        } else {
            TextFieldUtil.alertWarning(listAlert.get(1));
        }
    }

    /**
     * ?????????????????????????? ??????????????
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
        tcLength.setCellValueFactory(new PropertyValueFactory<>("length"));
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
/*        //08.12.2021 -  ?????????? ???????????? ???????????? ?? ?????????????????????????????????? ?????????? ?????? ???? ??/??:
        tcNumbWeldingMachine.setCellValueFactory(new PropertyValueFactory<>("numberWeldingMachine"));
        tcDateRope.setCellValueFactory(new PropertyValueFactory<>("dateRope"));
        tcTorsionRope.setCellValueFactory(new PropertyValueFactory<>("torsRope"));
        tcStraightRope.setCellValueFactory(new PropertyValueFactory<>("straightforwardnessRope"));
*/
    }

    public void clearAction() {
        unselectCheckBox();
        clearFields();
        unDisabledCheckBox();
    }

    public void getInfoAction() {
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        startDateTime = currentDateTime.minusDays(8);
        getInfoAction(null, null);
    }

    /**
     * ???????????????? ???????????????????? ?? ??????????????,???????? ?????????????? ?? ???? ?????? - ????????????????
     */
    public void getInfoAction(LocalDateTime dateStart, LocalDateTime dateEnd) {
//        executorService.submit(()->{
        new Thread (() -> {
            try {
                System.out.println(LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.ms")) + " Method started");
                loadSpinner.setVisible(true);
                lblWait.setVisible(true);
                lblLoad.setVisible(true);
                barcodeSpool.setVisible(false);
                lblDataProcessing.setVisible(true);

//                Platform.runLater(() -> {
                if (!barcodeSpool.getText().isEmpty()) {
                    List<BarcodeLabel> barcodeLabelList =
                            BarcodeLabelRepository.findByNumberSpoolBetween(dateStart,dateEnd,barcodeSpool.getText());
                    System.out.println(LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.ms")) + " Response from the server");
                    if (barcodeLabelList != null && barcodeLabelList.isEmpty()) {
                        Platform.runLater(() -> {
                            Constants.SPOOL_NUMBER = barcodeSpool.getText();
                            addSpool(dateStart, dateEnd);
//                            System.out.println("Countdown: " + countDownLatch.getCount());
//                            countDownLatch.countDown();
                        });
                        return;

                    }  else {
                            Platform.runLater(() -> { setCheckBoxesWithLabel(TemplatesLabelsRepository
                                    .findByIdCode(Long.valueOf(barcodeLabelList.get(0).getCode())).get(0));
//                                System.out.println("Countdown: " + countDownLatch.getCount());
//                                countDownLatch.countDown();
                            });
                    }
                    LOGGER.info("Spool number scan: " + barcodeSpool.getText());

                    Platform.runLater(() -> {
                        BarcodeLabel label = barcodeLabelList.get(0);
//            System.out.println(label);
                        LocalDate dateCurrentPrintLabel = LocalDate.now();
//                        tfTypeSpool.setText(label.getTypeSpool() != null ? String.valueOf(label.getTypeSpool()) : "");
                        tfCode.setText(label.getConsumerCode() != null ? String.valueOf(label.getConsumerCode()) : "");
                        tfConstruct.setText(label.getConstruct() != null ? (label.getConstruct()) : "");
                        tfNumberSpool.setText(label.getNumberSpool() != null ? (label.getNumberSpool()) : "");
                        tfDatePrint.setText(DateUtil.format(dateCurrentPrintLabel));
                        tfLR.setText(label.getRl() != null ? label.getRl() : "");
                        tfPart.setText(label.getPart() != null ? label.getPart() : "");
                        tfLot.setText(label.getLot() != null ? String.valueOf(label.getLot()) : "");
                        tfLength.setText(label.getLength() != null ? label.getLength() : "");
                        tfWelds.setText(label.getWelds() != null ? String.valueOf(label.getWelds()) : "0");
                        tfTorsion.setText(label.getTorsion() != null ? String.valueOf(label.getTorsion()) : "");

                        cbCode.setSelected(label.getConsumerCode() != null);
                        if(!cbCode.isSelected()){
                            cbCode.setDisable(true);
                        }
//                        System.out.println("Countdown: " + countDownLatch.getCount());
//                        countDownLatch.countDown();
                    });
                    barcodeSpool.getStylesheets().clear();
                    barcodeSpool.getStylesheets().add("/css/jfx_success.css");
                    Platform.runLater(() -> {
                        choiceLabelAction();
//                        cbCode.setSelected(label.getConsumerCode() != null);
                        tabInfoSpool.setText("???????????????????? ?? ??????????????: ???" + tfNumberSpool.getText());
                        barcodeSpool.setText("");
//                        System.out.println("Countdown: " + countDownLatch.getCount());
//                        countDownLatch.countDown();
                    });
                    System.out.println(LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.ms")) + " Method ended");
                } else if (barcodeSpool.getText().isEmpty()) {
                    Platform.runLater(() -> {
                    barcodeSpool.getStylesheets().clear();
                    barcodeSpool.getStylesheets().add("/css/jfx_error.css");
                    barcodeSpool.getStylesheets().add("/css/jfx_success.css");
                    clearFields();
                    unselectCheckBox();
                    TextFieldUtil.alertWarning("???????? ?????? c?????????????????? ??????????-???????? ????????????!" +
                                        "\n????????????????????, ???????????????????????? ??????????-?????? ??????????????!");
//                        System.out.println("Countdown: " + countDownLatch.getCount());
//                        countDownLatch.countDown();
                    });
                }

//            });
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
//            executorService.shutdown();
//            System.out.println("Countdown: " + countDownLatch.getCount());
//            countDownLatch.countDown();
//        });
        barcodeSpool.requestFocus();
    }

    public void scanByKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            getInfoAction();
        }
    }

    /**.
     * ?????????? ?????? ?????????????? ??????????????????????(TextField,CheckBox) ???????????????? ?????????????????? ?????????????? ????????????????
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
//                    entry.getValue().getLabelCheckBox().setDisable(!(Boolean) field.get(templatesLabels));
                    entry.getValue().getLabelCheckBox().setSelected((Boolean) field.get(templatesLabels));
                }
            }
            if (templatesLabels.getLanguageLabel()) {
                cbConsumer.setValue("ENG");
                cbTorsion.setDisable(false);
            } else {
                cbTorsion.setDisable(true);
                cbConsumer.setValue("??????");
            }
        } catch (Exception e) {
            System.out.println("Field probably has private access");
            e.printStackTrace();
        }
    }

    /**
     * ????????(?????????????????????????? ????????????) ?????? ?????????????????????????? ???????????? ?????????????? ?? ???????????????? ????????????????
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


    public void contextMenu(){
        TableSpools getSelectedView = tableView.getSelectionModel().getSelectedItem();
        if (getSelectedView != null) {
            String numberSpool = getSelectedView.getNumberSpool();
            barcodeSpool.setText(numberSpool);
            if (dateStart.getValue() == null && dateEnd.getValue() == null) {
                dateStart.setDateTimeValue(LocalDateTime.now().with(LocalTime.MIN));
                dateEnd.setDateTimeValue(LocalDateTime.now().with(LocalTime.MAX));
            }
            if(dateStart.getDateTimeValue() != null && dateEnd.getDateTimeValue()!=null) {
                LocalDateTime start = dateStart.getDateTimeValue().with(LocalTime.MIN);
                LocalDateTime end = dateEnd.getDateTimeValue().with(LocalTime.MAX);
                getInfoAction(start,end);
//                executorService = Executors.newCachedThreadPool();
//                countDownLatch = new CountDownLatch(2);
            } else {
                System.out.println("Something went wrong");
            }
        }
    }

    public void viewFromTable() {
//        executorService = Executors.newCachedThreadPool();
        contextMenu();
        tabInfoSpool.getTabPane().getSelectionModel().select(0);
    }

//    public void printFromTable(){
//        executorService = Executors.newCachedThreadPool();
//        countDownLatch = new CountDownLatch(6);
//        contextMenu();
//        try {
//            boolean isFinished = executorService.awaitTermination(15, TimeUnit.SECONDS);
//            boolean countDownFinished = countDownLatch.await(15,TimeUnit.SECONDS);
//            if (isFinished && countDownFinished) printQrCode();
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    private void copyValueFromTable(){
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setOnKeyPressed(e -> {
            if (e.isControlDown() && e.getCode() == KeyCode.C) {
                ObservableList<TableSpools> selectedItems = tableView.getSelectionModel().getSelectedItems();
                //???????????????? ???????????? ???????????????????? ?????????? ?? ???????????????? ????, ???????????????? - ?????????? ?????????? ?? ??????????????, ?????? ?? excel'e
                String copyString = selectedItems
                        .stream()
                        .map(TableSpools::getNumberSpool)
                        .collect(Collectors.joining(System.lineSeparator()));
                //?????????? ?????????????????? ?????? ?? ?????????? ????????????
                StringSelection stringSelection = new StringSelection(copyString);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            }
        });
    }

    public void refreshItems() {
//        Platform.runLater(this::dateBetweenAction);
        dateBetweenAction();
    }
}
