package gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import gui.application.AppProperties;
import gui.model.dto.*;
import gui.service.TextFieldService;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gui.model.*;
import gui.repository.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModalAddSpoolController implements Serializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane dialogPane;
    @FXML
    private Label cbSelectMainGroup;
    @FXML
    private JFXButton okBtn;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private TextField numberPart;
    @FXML
    private TextField numberLot;
    @FXML
    private ComboBox<String> cbLr;

    //    private final ObservableList<Integer> countSpool = FXCollections.observableArrayList(36, 48, 72);
    private final ObservableList<String> data = FXCollections.observableArrayList("L", "R");

    @FXML
    private ComboBox<String> cbTypeSpool;
    @FXML
    private TextField newNumberRopeMachine;
    @FXML
    private TextField newPersonalRope;

    @FXML
    private TextField newNumberSpool;
    @FXML
    private TextField newLength;
    @FXML
    private TextField newCountOfWelds;
    @FXML
    private TextField newStraight300;
    @FXML
    private TextField newStraight600;
    @FXML
    private TextField newStraight600_1;
    @FXML
    private TextField newStraight600_2;
    @FXML
    private TextField newStraight600_3;
    @FXML
    private TextField newStraight600_4;
    @FXML
    private TextField newStraight600_5;
    @FXML
    private TextField newStraight600Avg;
    @FXML
    private TextField newAvgResidualTorsion;
    //    @FXML
//    private TextField newTorsionRope;
//    @FXML
//    private TextField newStraightRope;
//    @FXML
//    private TextField newNumberWeldingMachine;
    @FXML
    private TextField newTorsion;

    public ModalAddSpoolController modalAddSpoolController;
    @FXML
    private ComboBox<Code> cbCode;

    @FXML
    private CheckBox newSample;
    @FXML
    private Label lblSelectMainGroup;
    @FXML
    private JFXComboBox<MainGroup> cbSelectMain;
    @FXML
    private VBox vBoxMain0;
    @FXML
    private VBox vBoxMain1;
    @FXML
    private VBox vBoxMain2;
    @FXML
    private VBox vBoxMain3;
    @FXML
    private VBox vBoxMain4;
    @FXML
    private Label lblCreateMainGroup;
    @FXML
    private Label lblAddNewSpool;
    @FXML
    private JFXButton btnCreate;
    public static final Logger LOGGER = LogManager.getLogger(ModalAddSpoolController.class.getName());
    private Stage stage;
    private List<Code> codeList = CodeRepository.findAllByConversionIdConversion();
    private final ObservableList<Code> codes = FXCollections.observableArrayList(codeList);

    private List<MainGroup> idGroupList = MainGroupRepository.getAllIdGroup();
    private ObservableList<MainGroup> idGroups = FXCollections.observableArrayList(idGroupList);

    private final ObservableList<String> typeSpool = FXCollections.observableArrayList("BS-40", "BS-60", "BS-80/17", "BS-80/33");
    private final ObservableList<String> mode = FXCollections.observableArrayList("СОЗДАНИЕ", "ВЫБОР ТЕКУЩЕЙ ЗАПИСИ");
    //    @FXML
//    private DatePicker newDateRope;
    @FXML
    private JFXComboBox<String> cbMode;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/modalAddSpool.fxml"));

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        modalAddSpoolController = fxmlLoader.getController();

    }

    @FXML
    public void initialize() {
        //Ограничение ввода символов и длинны в соответствующие поля для TestValue/MainValue:
        TextFieldService.setTextFieldNumeric(numberPart, 10);
        TextFieldService.setTextFieldNumeric(newNumberRopeMachine, 3);
        TextFieldService.setTextFieldNumeric(newNumberSpool, 12);
        TextFieldService.setTextFieldNumeric(newLength, 10);
        TextFieldService.setTextFieldNumeric(newPersonalRope, 7);
        TextFieldService.setTextFieldNumeric(newCountOfWelds, 3);
        TextFieldService.setFieldForStraight(newTorsion, 7);
        TextFieldService.setFieldForStraight(newStraight300, 7);
        TextFieldService.setFieldForStraight(newStraight600, 7);
        TextFieldService.setFieldForStraight(newStraight600_1, 7);
        TextFieldService.setFieldForStraight(newStraight600_2, 7);
        TextFieldService.setFieldForStraight(newStraight600_3, 7);
        TextFieldService.setFieldForStraight(newStraight600_4, 7);
        TextFieldService.setFieldForStraight(newStraight600_5, 7);
//        TextFieldService.setFieldForStraight(newTorsionRope, 7);
//        TextFieldService.setFieldForStraight(newStraightRope, 7);
//        TextFieldService.setTextFieldNumeric(newNumberWeldingMachine, 7);

        cbLr.setItems(data);
        cbLr.getSelectionModel().select(0);
        cbTypeSpool.setItems(typeSpool);
        cbTypeSpool.getSelectionModel().select(1);
        cbMode.setItems(mode);
        cbMode.getSelectionModel().select(1);
        selectionMode(cbMode.getValue());
        cbCode.setItems(codes);
        cbSelectMain.setItems(idGroups);
        cbSelectMain.getSelectionModel().select(0);
        newNumberSpool.setText(Constants.SPOOL_NUMBER);
    }

    //для выбора активного режима
    @FXML
    public void selectionAction(ActionEvent event) {
        selectionMode(cbMode.getValue());
    }


    public void modalAddSpoolCancel() {
        stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }


    public void cancelModalKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            modalAddSpoolCancel();
        }
    }

    public void addMainGroup() throws UnknownHostException {

        MainGroup mainGroup = new MainGroup();

        List<MainValueDTO> mainValueDTOs = new ArrayList<>();

        if (!cbCode.getSelectionModel().isEmpty()) {
            //установить значение для поля "КОД":
            MainValueDTO codeDTO = new MainValueDTO();
            codeDTO.setIdHead(11691L);
            codeDTO.setValue(String.valueOf(cbCode.getItems().get(cbCode.getSelectionModel().getSelectedIndex()).getCodePrimaryKey().getIdCode()));
            codeDTO.setIdGroup(mainGroup.getIdGroup());


            //установить значение для поля "Тип катушки":
            MainValueDTO typeSpoolDTO = new MainValueDTO();
            typeSpoolDTO.setIdGroup(mainGroup.getIdGroup());
            typeSpoolDTO.setIdHead(12507L);
            typeSpoolDTO.setValue(cbTypeSpool.getValue() != null ? cbTypeSpool.getValue() : "");

            //установить значение для поля "L/R":
            MainValueDTO lrDTO = new MainValueDTO();
            lrDTO.setIdGroup(mainGroup.getIdGroup());
            lrDTO.setIdHead(11694L);
            lrDTO.setValue(cbLr.getValue() != null ? cbLr.getValue() : "");

            //установить значение для поля "№ партии:"
            MainValueDTO partDTO = new MainValueDTO();
            lrDTO.setIdGroup(mainGroup.getIdGroup());
            partDTO.setIdGroup(mainGroup.getIdGroup());
            partDTO.setIdHead(11693L);
            partDTO.setValue(numberPart.getText() != null ? numberPart.getText() : "");

            //установить значение для поля "№ лота:"
            MainValueDTO lotDTO = new MainValueDTO();
            lotDTO.setIdGroup(mainGroup.getIdGroup());
            lotDTO.setIdHead(11692L);
            lotDTO.setValue(numberLot.getText() != null ? numberLot.getText() : "");

            //установить значение для поля "Протокол":
            MainValueDTO protocolDTO = new MainValueDTO();
            protocolDTO.setIdGroup(mainGroup.getIdGroup());
            protocolDTO.setIdHead(1889350L);
            String lastCurrentProtocol = String.valueOf(MainValueRepository.getLastProtocol("http://" + AppProperties.getHost() + "/api/lastProtocol"));
            if (lastCurrentProtocol.equals("[null]")) {
                protocolDTO.setNumberValue(1.0);
            } else {
                lastCurrentProtocol = lastCurrentProtocol.replaceAll("\\[", "").replaceAll("\\]", "");
                double result = Double.parseDouble(lastCurrentProtocol);
                result++;
                protocolDTO.setNumberValue(result);
            }

            mainValueDTOs.add(codeDTO);
            mainValueDTOs.add(typeSpoolDTO);
            mainValueDTOs.add(lrDTO);
            mainValueDTOs.add(partDTO);
            mainValueDTOs.add(lotDTO);
            mainValueDTOs.add(protocolDTO);

            //заполнение таблички DATE_TABLE(Дата создания)
            MainGroupRequestDTO mainGroupRequestDTO = new MainGroupRequestDTO();
            mainGroupRequestDTO.setWhoCreate(Constants.FIO);
            String namePersonal = String.valueOf(AccessPersonalRepository.
                    getAccessPersonal("http://" + AppProperties.getHost() +
                            "/api/getAccessPersonal/" + Constants.ID_PERSONALS));
            namePersonal = namePersonal.replaceAll("\\[", "").replaceAll("\\]", "");
            System.out.println(namePersonal);
            Long idLaboratory = Long.valueOf(namePersonal);
            mainGroupRequestDTO.setLaboratory(idLaboratory);
            mainGroupRequestDTO.setMainValueDTOList(mainValueDTOs);
            mainGroupRequestDTO.setIpAddressCreate(Constants.IP_ADDRESS);

            MainGroupResponseDTO newIdGroup = MainGroupRepository.addIdMain(mainGroupRequestDTO);

            cbSelectMain.getItems().add(0, newIdGroup.getMainGroup());
            cbSelectMain.getSelectionModel().select(0);
            cbMode.getSelectionModel().select(1);

            String idGroup = String.valueOf(newIdGroup.getMainGroup()).replaceAll("[\\s].*", "");
            LOGGER.info("Created idMainGroup entry:{} - {}; Hostname/Ip:{}",
                    idGroup, Constants.FIO_VIEW, InetAddress.getLocalHost());

            MainValueDTO valueMainDTOs = mainValueDTOs.get(0);
            System.out.println(valueMainDTOs);

        } else {
            TextFieldService.alertWarning("Поле \"КОД\" содержит пустое значение! \nВыберите значение из выпадающего списка!");
        }
    }

    public void okBtnAction() throws UnknownHostException {

        if (cbMode.getValue().equals("ВЫБОР ТЕКУЩЕЙ ЗАПИСИ")) {
            MainGroup mainGroup = new MainGroup();
            mainGroup.setIdGroup(Long.valueOf(String.valueOf(cbSelectMain.getItems().get(cbSelectMain.getSelectionModel().getSelectedIndex()).getIdGroup())));
            ForeignGroup foreignGroup = new ForeignGroup();
            foreignGroup.setMainGroup(mainGroup);

            ForeignGroupResponseDTO foreignGroupResponseDTO = new ForeignGroupResponseDTO();

            List<TestValueDTO> testValueDTOs = new ArrayList<>();

            //установить значение для поля "№ катушки":
            TestValueDTO numberSpoolDTO = new TestValueDTO();
            numberSpoolDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            numberSpoolDTO.setIdTestHead(11697L);
            numberSpoolDTO.setTextValue(newNumberSpool.getText() != null ? newNumberSpool.getText() : "");
            numberSpoolDTO.setIdConversion(11690L);
            numberSpoolDTO.setMainGroup(mainGroup);


            //установить значение для поля "№ канатной машины":
            TestValueDTO numberRopeMachineDTO = new TestValueDTO();
            numberRopeMachineDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            numberRopeMachineDTO.setIdTestHead(11699L);
            numberRopeMachineDTO.setValue(!newNumberRopeMachine.getText().equals("") ? Double.valueOf(newNumberRopeMachine.getText()) : null);
            numberRopeMachineDTO.setIdConversion(11690L);
            numberRopeMachineDTO.setMainGroup(mainGroup);

            //установить значение для поля "Табельный канатчика":
            TestValueDTO personalRopeDTO = new TestValueDTO();
            personalRopeDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            personalRopeDTO.setIdTestHead(11700L);
            personalRopeDTO.setTextValue(newPersonalRope.getText() != null ? newPersonalRope.getText() : "");
            personalRopeDTO.setIdConversion(11690L);
            personalRopeDTO.setMainGroup(mainGroup);

            //установить значение для поля "кол-во сварок":
            TestValueDTO numbOfWeldsDTO = new TestValueDTO();
            numbOfWeldsDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            numbOfWeldsDTO.setIdTestHead(11730L);
            numbOfWeldsDTO.setValue(!newCountOfWelds.getText().equals("") ? Double.valueOf(newCountOfWelds.getText()) : null);
            numbOfWeldsDTO.setIdConversion(11690L);
            numbOfWeldsDTO.setMainGroup(mainGroup);

            /**
             *  08.12.2021 Канатным участком было решено убрать такие параметры с пользовательского ввода ,как: "Дата КУ",
             * "№ сварочного аппарата", "Длина","Кручение канатное", "Прямолинейность канатное".
             */

            //установить значение для поля "Дата КУ":
//            TestValueDTO dataRopeDTO = new TestValueDTO();
//            dataRopeDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
//            dataRopeDTO.setIdTestHead(11727L);
//            dataRopeDTO.setDateValue(newDateRope.getValue() != null ? newDateRope.getValue() : LocalDate.parse(""));
//            dataRopeDTO.setIdConversion(11690L);
//            dataRopeDTO.setMainGroup(mainGroup);

//            //установить значение для поля "Длина":
//            TestValueDTO lengthDTO = new TestValueDTO();
//            lengthDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
//            lengthDTO.setIdTestHead(11701L);
//            lengthDTO.setValue(!newLength.getText().equals("") ? Double.valueOf(newLength.getText()) : null);
//            lengthDTO.setIdConversion(11690L);
//            lengthDTO.setMainGroup(mainGroup);

            //установить значение для поля "№ сварочного аппарата":
//            TestValueDTO numbWeldingMachineDTO = new TestValueDTO();
//            numbWeldingMachineDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
//            numbWeldingMachineDTO.setIdTestHead(1875350L);
//            numbWeldingMachineDTO.setValue(!newNumberWeldingMachine.getText().equals("") ? Double.valueOf(newNumberWeldingMachine.getText()) : null);
//            numbWeldingMachineDTO.setIdConversion(11690L);
//            numbWeldingMachineDTO.setMainGroup(mainGroup);

//            //установить значение для поля "Кручение канатное":
//            TestValueDTO torsionRopeDTO = new TestValueDTO();
//            torsionRopeDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
//            torsionRopeDTO.setIdTestHead(11703L);
//            torsionRopeDTO.setValue(!newTorsionRope.getText().equals("") ? Double.valueOf(newTorsionRope.getText()) : null);
//            torsionRopeDTO.setIdConversion(11690L);
//            torsionRopeDTO.setMainGroup(mainGroup);
//
//            //установить значение для поля "Прямолинейность канатное":
//            TestValueDTO straightRopeDTO = new TestValueDTO();
//            straightRopeDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
//            straightRopeDTO.setIdTestHead(11702L);
//            straightRopeDTO.setValue(!newStraightRope.getText().equals("") ? Double.valueOf(newStraightRope.getText()) : null);
//            straightRopeDTO.setIdConversion(11690L);
//            straightRopeDTO.setMainGroup(mainGroup);

            //установить значение для поля "Кручение":
            TestValueDTO torsionDTO = new TestValueDTO();
            torsionDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            torsionDTO.setIdTestHead(11729L);
            torsionDTO.setValue(!newTorsion.getText().equals("") ? Double.valueOf(newTorsion.getText()) : null);
            torsionDTO.setIdConversion(11690L);
            torsionDTO.setMainGroup(mainGroup);

            //установить значение для поля "Прямолинейность 300":
            TestValueDTO straight300DTO = new TestValueDTO();
            straight300DTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            straight300DTO.setIdTestHead(11728L);
            straight300DTO.setValue(!newStraight300.getText().equals("") ? Double.valueOf(newStraight300.getText()) : null);
            straight300DTO.setIdConversion(11690L);
            straight300DTO.setMainGroup(mainGroup);

            //установить значение для поля "Прямолинейность 600":
            TestValueDTO straight600DTO = new TestValueDTO();
            straight600DTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            straight600DTO.setIdTestHead(1918897L);
            straight600DTO.setValue(!newStraight600.getText().equals("") ? Double.valueOf(newStraight600.getText()) : null);
            straight600DTO.setIdConversion(11690L);
            straight600DTO.setMainGroup(mainGroup);

            //установить значение для поля "Прямолинейность 600_1":
            TestValueDTO straight600_1DTO = new TestValueDTO();
            straight600_1DTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            straight600_1DTO.setIdTestHead(11731L);
            straight600_1DTO.setValue(!newStraight600_1.getText().equals("") ? Double.valueOf(newStraight600_1.getText()) : null);
            straight600_1DTO.setIdConversion(11690L);
            straight600_1DTO.setMainGroup(mainGroup);

            //установить значение для поля "Прямолинейность 600_2":
            TestValueDTO straight600_2DTO = new TestValueDTO();
            straight600_2DTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            straight600_2DTO.setIdTestHead(11732L);
            straight600_2DTO.setValue(!newStraight600_2.getText().equals("") ? Double.valueOf(newStraight600_2.getText()) : null);
            straight600_2DTO.setIdConversion(11690L);
            straight600_2DTO.setMainGroup(mainGroup);

            //установить значение для поля "Прямолинейность 600_3":
            TestValueDTO straight600_3DTO = new TestValueDTO();
            straight600_3DTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            straight600_3DTO.setIdTestHead(11733L);
            straight600_3DTO.setValue(!newStraight600_3.getText().equals("") ? Double.valueOf(newStraight600_3.getText()) : null);
            straight600_3DTO.setIdConversion(11690L);
            straight600_3DTO.setMainGroup(mainGroup);

            //установить значение для поля "Прямолинейность 600_4":
            TestValueDTO straight600_4DTO = new TestValueDTO();
            straight600_4DTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            straight600_4DTO.setIdTestHead(11734L);
            straight600_4DTO.setValue(!newStraight600_4.getText().equals("") ? Double.valueOf(newStraight600_4.getText()) : null);
            straight600_4DTO.setIdConversion(11690L);
            straight600_4DTO.setMainGroup(mainGroup);

            //установить значение для поля "Прямолинейность 600_5":
            TestValueDTO straight600_5DTO = new TestValueDTO();
            straight600_5DTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            straight600_5DTO.setIdTestHead(11735L);
            straight600_5DTO.setValue(!newStraight600_5.getText().equals("") ? Double.valueOf(newStraight600_5.getText()) : null);
            straight600_5DTO.setIdConversion(11690L);
            straight600_5DTO.setMainGroup(mainGroup);


            Double str600 = !newStraight600.getText().equals("") ? Double.valueOf(newStraight600.getText()) : null;
            Double str600_1 = !newStraight600_1.getText().equals("") ? Double.valueOf(newStraight600_1.getText()) : null;
            Double str600_2 = !newStraight600_2.getText().equals("") ? Double.valueOf(newStraight600_2.getText()) : null;
            Double str600_3 = !newStraight600_3.getText().equals("") ? Double.valueOf(newStraight600_3.getText()) : null;
            Double str600_4 = !newStraight600_4.getText().equals("") ? Double.valueOf(newStraight600_4.getText()) : null;
            Double str600_5 = !newStraight600_5.getText().equals("") ? Double.valueOf(newStraight600_5.getText()) : null;

            List<Double> strAVGList = new ArrayList<>();

            if (str600 != null) {
                strAVGList.add(str600);
            }
            if (str600_1 != null) {
                strAVGList.add(str600_1);
            }
            if (str600_2 != null) {
                strAVGList.add(str600_2);
            }
            if (str600_3 != null) {
                strAVGList.add(str600_3);
            }
            if (str600_4 != null) {
                strAVGList.add(str600_4);
            }
            if (str600_5 != null) {
                strAVGList.add(str600_5);
            }

            if (strAVGList != null && !strAVGList.isEmpty()) {
                double sum = 0;
                Iterator<Double> iter1 = strAVGList.iterator();
                while (iter1.hasNext()) {
                    sum += iter1.next();
                }
                double average = sum / strAVGList.size();
                double resultAVG = Math.round(average);
                System.out.println("Average = " + resultAVG);

                //установить значение для поля "Прямолинейность 600 среднее":
                TestValueDTO straight600AvgDTO = new TestValueDTO();
                straight600AvgDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
                straight600AvgDTO.setIdTestHead(11736L);
                straight600AvgDTO.setValue(resultAVG);
                straight600AvgDTO.setIdConversion(11690L);
                straight600AvgDTO.setMainGroup(mainGroup);
                testValueDTOs.add(straight600AvgDTO);
            }

            //установить значение для поля "Образец":
            TestValueDTO sampleDTO = new TestValueDTO();
            sampleDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            sampleDTO.setIdTestHead(11698L);
            sampleDTO.setValue(newSample.isSelected() ? 1d : 0d);
            sampleDTO.setIdConversion(11690L);
            sampleDTO.setMainGroup(mainGroup);


            testValueDTOs.add(numberSpoolDTO);
            testValueDTOs.add(numberRopeMachineDTO);
            testValueDTOs.add(personalRopeDTO);
            testValueDTOs.add(numbOfWeldsDTO);
            testValueDTOs.add(torsionDTO);
            testValueDTOs.add(straight300DTO);
            testValueDTOs.add(straight600DTO);
            testValueDTOs.add(straight600_1DTO);
            testValueDTOs.add(straight600_2DTO);
            testValueDTOs.add(straight600_3DTO);
            testValueDTOs.add(straight600_4DTO);
            testValueDTOs.add(straight600_5DTO);
//            testValueDTOs.add(dataRopeDTO);
//            testValueDTOs.add(numbWeldingMachineDTO);
//            testValueDTOs.add(lengthDTO);
//            testValueDTOs.add(torsionRopeDTO);
//            testValueDTOs.add(straightRopeDTO);
            testValueDTOs.add(sampleDTO);

            ForeignGroupRequestDTO foreignGroupRequestDTO = new ForeignGroupRequestDTO();
            foreignGroupRequestDTO.setWhoCreate(Constants.FIO);
            String namePersonal = String.valueOf(AccessPersonalRepository.
                    getAccessPersonal("http://" + AppProperties.getHost() +
                            "/api/getAccessPersonal/" + Constants.ID_PERSONALS));
            namePersonal = namePersonal.replaceAll("\\[", "").replaceAll("\\]", "");
//            System.out.println(namePersonal);
            Long idLaboratory = Long.valueOf(namePersonal);
            foreignGroupRequestDTO.setLaboratory(idLaboratory);
            foreignGroupRequestDTO.setTestValueDTOList(testValueDTOs);
            foreignGroupRequestDTO.setIpAddressCreate(Constants.IP_ADDRESS);

//            ForeignGroupRepository.addIdForeign(foreignGroupRequestDTO);
            ForeignGroupResponseDTO newIdForeign = ForeignGroupRepository.addIdForeign(foreignGroupRequestDTO);

            String createdIdForeign = String.valueOf(newIdForeign.getForeignGroup());
            Pattern pattern = Pattern.compile("\\=([^=,]+)\\,");
            Matcher matcher = pattern.matcher(createdIdForeign);
            if (matcher.find()) {
                LOGGER.info("Created idForeignGroup entry:{} in idMainGroup:{} - {}; {}",
                        matcher.group(1), newIdForeign.getForeignGroup().getMainGroup().getIdGroup(),
                        Constants.FIO_VIEW, InetAddress.getLocalHost());
            }
            modalAddSpoolCancel();

            TestValueDTO valueForeign = testValueDTOs.get(0);
            System.out.println(testValueDTOs);


        } else {

            addMainGroup();
        }
    }

    public void selectionMode(String mode) {
        if (mode.equals("ВЫБОР ТЕКУЩЕЙ ЗАПИСИ")) {
            lblSelectMainGroup.setDisable(false);
            cbSelectMain.setDisable(false);
            lblCreateMainGroup.setDisable(true);
            vBoxMain0.setDisable(true);
            vBoxMain1.setDisable(true);
            vBoxMain2.setDisable(false);
            vBoxMain3.setDisable(false);
            vBoxMain4.setDisable(false);
            okBtn.setVisible(true);
            cancelBtn.setVisible(true);
            btnCreate.setVisible(false);
            lblAddNewSpool.setDisable(false);

            LOGGER.info("Selected mode: \"ВЫБОР\"");
        } else if (mode.equals("СОЗДАНИЕ")) {
            cbSelectMain.setDisable(true);
            lblSelectMainGroup.setDisable(true);
            lblCreateMainGroup.setDisable(false);
            vBoxMain0.setDisable(false);
            vBoxMain1.setDisable(false);
            vBoxMain2.setDisable(true);
            vBoxMain3.setDisable(true);
            vBoxMain4.setDisable(true);
            okBtn.setVisible(false);
            cancelBtn.setVisible(false);
            btnCreate.setVisible(true);
            lblAddNewSpool.setDisable(true);

            LOGGER.info("Selected mode: \"СОЗДАНИЕ\"");
        }
    }
}