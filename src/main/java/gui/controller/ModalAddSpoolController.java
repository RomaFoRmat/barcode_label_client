package gui.controller;

import com.jfoenix.controls.*;

import gui.application.AppProperties;
import gui.model.dto.*;
import gui.util.SearchComboBoxUtil;
import gui.util.TextFieldUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gui.model.*;
import gui.repository.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tornadofx.control.DateTimePicker;

import static gui.model.Constants.SPOOL_NUMBER;
import static gui.model.Constants.SPOOL_NUMBER_MODAL;

public class ModalAddSpoolController {

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
    private TextField tfContainer;
    @FXML
    private ComboBox<String> cbLr;
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
    private Label lblNumbContainer;
    @FXML
    private Label lblWelds;
    @FXML
    private Label lblTorsion;
    @FXML
    private Label lblStraight300;
    @FXML
    private Label lblStraight600;
    @FXML
    private Label lblStraight600_1;
    @FXML
    private Label lblStraight600_2;
    @FXML
    private Label lblStraight600_3;
    @FXML
    private Label lblStraight600_4;
    @FXML
    private Label lblStraight600_5;
    @FXML
    private CheckBox cbStraight300;
    @FXML
    private CheckBox cbStraight600;
    @FXML
    private CheckBox cbWelds;
    @FXML
    private CheckBox cbTorsion;
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
    private CheckBox personalStamp;
    @FXML
    private JFXButton btnCreate;
    @FXML
    private JFXComboBox<String> cbMode;
    @FXML
    private StackPane stackPaneMain;
    @FXML
    private Label lblCaptionCode;
    @FXML
    private ImageView imgCtrl;
    @FXML
    private Label lblKey;
    @FXML
    private ImageView imgEnter;
    @FXML
    private ImageView imgEsc;
    @FXML
    private DateTimePicker dateStart;
    @FXML
    private DateTimePicker dateEnd;
    @FXML
    private Label lblAmount;
    @FXML
    private Label lblSpools;
    @FXML
    private Label lblPart;
    @FXML
    private CheckMenuItem checkMenuOneMonth;

    @FXML
    private CheckMenuItem checkMenuSixMonths;

    @FXML
    private CheckMenuItem checkMenuWeek;

    @FXML
    private CheckMenuItem checkMenuYear;

    @FXML
    private CheckMenuItem checkMenuTime;

    private Map<Long, ModalField> modalFieldMap;

    public static final Logger LOGGER = LogManager.getLogger(ModalAddSpoolController.class.getName());
    private Stage stage;
    private List<Code> codeList = CodeRepository.findAllByConversionIdConversion();
    private final ObservableList<Code> codes = FXCollections.observableArrayList(codeList);

    private final List<MainGroup> idGroupList = MainGroupRepository.getAllIdGroupMonth();
    private final ObservableList<MainGroup> idGroups = FXCollections.observableArrayList(idGroupList);

    private final List<MainGroup> idGroupListWeek = MainGroupRepository.getAllIdGroupWeek();
    private final ObservableList<MainGroup> idGroupWeek = FXCollections.observableArrayList(idGroupListWeek);

    private final List<MainGroup> idGroupListMonths = MainGroupRepository.getAllIdGroupSixMonths();
    private final ObservableList<MainGroup> idGroupSixMonth = FXCollections.observableArrayList(idGroupListMonths);

    private final List<MainGroup> idGroupListYear = MainGroupRepository.getAllIdGroupYear();
    private final ObservableList<MainGroup> idGroupYear = FXCollections.observableArrayList(idGroupListYear);

    private final ObservableList<String> typeSpool = FXCollections.observableArrayList("BS-40", "BS-60", "BS-80/17", "BS-80/33");
    private final ObservableList<String> mode = FXCollections.observableArrayList("????????????????", "?????????? ?????????????? ????????????");
    //    @FXML
//    private DatePicker newDateRope;

    @FXML
    public void initialize() {
        modalFieldMap = statusCheckBoxMap();
        //?????????????????????? ?????????? ???????????????? ?? ???????????? ?? ?????????????????????????????? ???????? ?????? TestValue/MainValue:
        TextFieldUtil.setTextFieldNumeric(numberPart, 10);
        TextFieldUtil.setTextFieldNumeric(tfContainer, 10);
        TextFieldUtil.setTextFieldNumeric(newNumberRopeMachine, 3);
        TextFieldUtil.setLimitingFields(newNumberRopeMachine, 721);
        TextFieldUtil.setTextFieldNumeric(newNumberSpool, 12);
//        TextFieldUtil.setTextFieldNumeric(newLength, 10);
        TextFieldUtil.setTextFieldNumeric(newPersonalRope, 7);
        TextFieldUtil.setTextFieldNumeric(newCountOfWelds, 3);
        TextFieldUtil.setFieldForStraight(newTorsion, 7);
        TextFieldUtil.setFieldForStraight(newStraight300, 7);
        TextFieldUtil.setFieldForStraight(newStraight600, 7);
        TextFieldUtil.setFieldForStraight(newStraight600_1, 7);
        TextFieldUtil.setFieldForStraight(newStraight600_2, 7);
        TextFieldUtil.setFieldForStraight(newStraight600_3, 7);
        TextFieldUtil.setFieldForStraight(newStraight600_4, 7);
        TextFieldUtil.setFieldForStraight(newStraight600_5, 7);
//        TextFieldService.setFieldForStraight(newTorsionRope, 7);
//        TextFieldService.setFieldForStraight(newStraightRope, 7);
//        TextFieldService.setTextFieldNumeric(newNumberWeldingMachine, 7);
        newNumberSpool.setText(Constants.SPOOL_NUMBER);
        cbLr.setItems(data);
        cbLr.getSelectionModel().select(0);
        cbTypeSpool.setItems(typeSpool);
        cbTypeSpool.getSelectionModel().select(1);
        cbMode.setItems(mode);
        cbMode.getSelectionModel().select(1);
        selectionMode(cbMode.getValue());
        cbCode.setItems(codes);
        cbSelectMain.setItems(idGroupWeek);
        cbSelectMain.getSelectionModel().select(0);
//        checkMenuOneMonth.setSelected(true);
//        selectContextMenu();

        SearchComboBoxUtil.autoCompleteComboBoxPlus(cbCode,
                (typedText, itemToCompare) -> itemToCompare.getCode().toLowerCase().contains(typedText.toLowerCase())
                        || itemToCompare.getDescription().toLowerCase().contains(typedText.toLowerCase()));
        SearchComboBoxUtil.getComboBoxValue(cbCode);
        selectMasterRecord();

//        SearchComboBoxUtil.autoCompleteComboBoxPlus(cbSelectMain,
//                (typedText, itemToCompare) -> itemToCompare.getIdGroup().toString().contains(typedText.toLowerCase())
//                        || itemToCompare.getDateCreate().toString().toLowerCase().contains(typedText.toLowerCase()));
//        SearchComboBoxUtil.getComboBoxValue(cbSelectMain);
    }

    //?????? ???????????? ?????????????????? ????????????
    @FXML
    public void selectionAction() {
        selectionMode(cbMode.getValue());
    }


    public void modalAddSpoolCancel() {
        stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void hotKey(KeyEvent keyEvent) throws UnknownHostException {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            modalAddSpoolCancel();
        } else if (cancelBtn.isVisible()) {
            if (keyEvent.getCode() == KeyCode.ENTER && keyEvent.isControlDown()) {
                okBtnAction();
            }
        }
    }

    public void addMainGroup() throws UnknownHostException {

        MainGroup mainGroup = new MainGroup();

        List<MainValueDTO> mainValueDTOs = new ArrayList<>();

        if (!cbCode.getSelectionModel().isEmpty()) {
            //???????????????????? ???????????????? ?????? ???????? "??????":
            MainValueDTO codeDTO = new MainValueDTO();
            codeDTO.setIdHead(11691L);
            codeDTO.setValue(String.valueOf(cbCode.getItems().get(cbCode.getSelectionModel().getSelectedIndex()).getCodePrimaryKey().getIdCode()));
            codeDTO.setIdGroup(mainGroup.getIdGroup());


            //???????????????????? ???????????????? ?????? ???????? "?????? ??????????????":
            MainValueDTO typeSpoolDTO = new MainValueDTO();
            typeSpoolDTO.setIdGroup(mainGroup.getIdGroup());
            typeSpoolDTO.setIdHead(12507L);
            typeSpoolDTO.setValue(cbTypeSpool.getValue() != null ? cbTypeSpool.getValue() : "");

            //???????????????????? ???????????????? ?????? ???????? "L/R":
            MainValueDTO lrDTO = new MainValueDTO();
            lrDTO.setIdGroup(mainGroup.getIdGroup());
            lrDTO.setIdHead(11694L);
            lrDTO.setValue(cbLr.getValue() != null ? cbLr.getValue() : "");

            //???????????????????? ???????????????? ?????? ???????? "??? ????????????:"
            MainValueDTO partDTO = new MainValueDTO();
            lrDTO.setIdGroup(mainGroup.getIdGroup());
            partDTO.setIdGroup(mainGroup.getIdGroup());
            partDTO.setIdHead(11693L);
            partDTO.setValue(numberPart.getText() != null ? numberPart.getText() : "");

            //???????????????????? ???????????????? ?????? ???????? "??? ????????:"
            MainValueDTO lotDTO = new MainValueDTO();
            lotDTO.setIdGroup(mainGroup.getIdGroup());
            lotDTO.setIdHead(11692L);
            lotDTO.setValue(numberLot.getText() != null ? numberLot.getText() : "");

            //???????????????????? ???????????????? ?????? ???????? "??? ????????????????????"
            MainValueDTO containerDTO = new MainValueDTO();
            containerDTO.setIdGroup(mainGroup.getIdGroup());
            containerDTO.setIdHead(2063171L);
//            containerDTO.setNumberValue(Double.valueOf(tfContainer.getText() != null ? tfContainer.getText() : ""));
            containerDTO.setNumberValue(!tfContainer.getText().equals("") ? Double.valueOf(tfContainer.getText()) : null);

            //???????????????????? ???????????????? ?????? ???????? "????????????????":
            MainValueDTO protocolDTO = new MainValueDTO();
            protocolDTO.setIdGroup(mainGroup.getIdGroup());
            protocolDTO.setIdHead(1889350L);

            String lastCurrentProtocol = String.valueOf(MainValueRepository.getLastProtocol(AppProperties.getHost() + "/api/lastProtocol"));

            if (lastCurrentProtocol.equals("[]")) {
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
            mainValueDTOs.add(containerDTO);
            mainValueDTOs.add(protocolDTO);

            //???????????????????? ???????????????? DATE_TABLE(???????? ????????????????)
            MainGroupRequestDTO mainGroupRequestDTO = new MainGroupRequestDTO();
            mainGroupRequestDTO.setWhoCreate(Constants.FIO);
            String namePersonal = String.valueOf(AccessPersonalRepository.
                    getAccessPersonal(AppProperties.getHost() + "/api/getAccessPersonal/" + Constants.ID_PERSONALS));
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

            okDialogSuccess();

            MainValueDTO valueMainDTOs = mainValueDTOs.get(0);
            System.out.println(valueMainDTOs);

        } else {
            TextFieldUtil.alertWarning("???????? \"??????\" ???????????????? ???????????? ????????????????! \n???????????????? ???????????????? ???? ?????????????????????? ????????????!");
        }
    }

    public void okDialogSuccess() {
        JFXDialogLayout message = new JFXDialogLayout();
//        message.setHeading(new Text("??????????!"));
        message.setBody(new Text("?????????????? ???????????? ?????????????? ??????????????!"));
        message.setAlignment(Pos.TOP_RIGHT);
        message.setStyle("-fx-font-size: 16; -fx-font-family: 'Comic Sans MS';");
        JFXDialog dialog = new JFXDialog(stackPaneMain, message, JFXDialog.DialogTransition.CENTER);
        JFXButton btnDialog = new JFXButton("OK");

        btnDialog.setOnAction(event -> dialog.close());
        message.addEventHandler(KeyEvent.KEY_PRESSED, event2 -> {
            if (event2.getCode() == KeyCode.ENTER) {
                btnDialog.fire();
                event2.consume();
            }
        });
        message.setActions(btnDialog);
        dialog.show();
    }

    public void okBtnAction() throws UnknownHostException {

        if (cbMode.getValue().equals("?????????? ?????????????? ????????????")) {
//            selectMasterRecord();
            MainGroup mainGroup = new MainGroup();
            mainGroup.setIdGroup(Long.valueOf(String.valueOf(cbSelectMain.getItems().get(cbSelectMain.getSelectionModel().getSelectedIndex()).getIdGroup())));
            ForeignGroup foreignGroup = new ForeignGroup();
            foreignGroup.setMainGroup(mainGroup);

            ForeignGroupResponseDTO foreignGroupResponseDTO = new ForeignGroupResponseDTO();

            List<TestValueDTO> testValueDTOs = new ArrayList<>();

            //???????????????????? ???????????????? ?????? ???????? "??? ??????????????":
            TestValueDTO numberSpoolDTO = new TestValueDTO();
            numberSpoolDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            numberSpoolDTO.setIdTestHead(11697L);
            numberSpoolDTO.setTextValue(newNumberSpool.getText() != null ? newNumberSpool.getText() : "");
            numberSpoolDTO.setIdConversion(11690L);
            numberSpoolDTO.setMainGroup(mainGroup);


            //???????????????????? ???????????????? ?????? ???????? "??? ???????????????? ????????????":
            TestValueDTO numberRopeMachineDTO = new TestValueDTO();
            numberRopeMachineDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            numberRopeMachineDTO.setIdTestHead(11699L);
            numberRopeMachineDTO.setValue(!newNumberRopeMachine.getText().equals("") ? Double.valueOf(newNumberRopeMachine.getText()) : null);
            numberRopeMachineDTO.setIdConversion(11690L);
            numberRopeMachineDTO.setMainGroup(mainGroup);

            //???????????????????? ???????????????? ?????? ???????? "?????????????????? ??????????????????":
            TestValueDTO personalRopeDTO = new TestValueDTO();
            personalRopeDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            personalRopeDTO.setIdTestHead(11700L);
            personalRopeDTO.setTextValue(newPersonalRope.getText() != null ? newPersonalRope.getText() : "");
            personalRopeDTO.setIdConversion(11690L);
            personalRopeDTO.setMainGroup(mainGroup);

            //???????????????????? ???????????????? ?????? ???????? "??????-???? ????????????":
            TestValueDTO numbOfWeldsDTO = new TestValueDTO();
            numbOfWeldsDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            numbOfWeldsDTO.setIdTestHead(11730L);
            numbOfWeldsDTO.setValue(!newCountOfWelds.getText().equals("") ? Double.valueOf(newCountOfWelds.getText()) : null);
            numbOfWeldsDTO.setIdConversion(11690L);
            numbOfWeldsDTO.setMainGroup(mainGroup);

            /**
             *  08.12.2021 ???????????????? ???????????????? ???????? ???????????? ???????????? ?????????? ?????????????????? ?? ?????????????????????????????????? ?????????? ,??????: "???????? ????",
             * "??? ???????????????????? ????????????????", "??????????","???????????????? ????????????????", "?????????????????????????????? ????????????????".
             */

            //???????????????????? ???????????????? ?????? ???????? "???????? ????":
//            TestValueDTO dataRopeDTO = new TestValueDTO();
//            dataRopeDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
//            dataRopeDTO.setIdTestHead(11727L);
//            dataRopeDTO.setDateValue(newDateRope.getValue() != null ? newDateRope.getValue() : LocalDate.parse(""));
//            dataRopeDTO.setIdConversion(11690L);
//            dataRopeDTO.setMainGroup(mainGroup);

//            //???????????????????? ???????????????? ?????? ???????? "??????????":
//            TestValueDTO lengthDTO = new TestValueDTO();
//            lengthDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
//            lengthDTO.setIdTestHead(11701L);
//            lengthDTO.setValue(!newLength.getText().equals("") ? Double.valueOf(newLength.getText()) : null);
//            lengthDTO.setIdConversion(11690L);
//            lengthDTO.setMainGroup(mainGroup);

            //???????????????????? ???????????????? ?????? ???????? "??? ???????????????????? ????????????????":
//            TestValueDTO numbWeldingMachineDTO = new TestValueDTO();
//            numbWeldingMachineDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
//            numbWeldingMachineDTO.setIdTestHead(1875350L);
//            numbWeldingMachineDTO.setValue(!newNumberWeldingMachine.getText().equals("") ? Double.valueOf(newNumberWeldingMachine.getText()) : null);
//            numbWeldingMachineDTO.setIdConversion(11690L);
//            numbWeldingMachineDTO.setMainGroup(mainGroup);

//            //???????????????????? ???????????????? ?????? ???????? "???????????????? ????????????????":
//            TestValueDTO torsionRopeDTO = new TestValueDTO();
//            torsionRopeDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
//            torsionRopeDTO.setIdTestHead(11703L);
//            torsionRopeDTO.setValue(!newTorsionRope.getText().equals("") ? Double.valueOf(newTorsionRope.getText()) : null);
//            torsionRopeDTO.setIdConversion(11690L);
//            torsionRopeDTO.setMainGroup(mainGroup);
//
//            //???????????????????? ???????????????? ?????? ???????? "?????????????????????????????? ????????????????":
//            TestValueDTO straightRopeDTO = new TestValueDTO();
//            straightRopeDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
//            straightRopeDTO.setIdTestHead(11702L);
//            straightRopeDTO.setValue(!newStraightRope.getText().equals("") ? Double.valueOf(newStraightRope.getText()) : null);
//            straightRopeDTO.setIdConversion(11690L);
//            straightRopeDTO.setMainGroup(mainGroup);

            //???????????????????? ???????????????? ?????? ???????? "????????????????":
            TestValueDTO torsionDTO = new TestValueDTO();
            torsionDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            torsionDTO.setIdTestHead(11729L);
            torsionDTO.setValue(!newTorsion.getText().equals("") ? Double.valueOf(newTorsion.getText()) : null);
            torsionDTO.setIdConversion(11690L);
            torsionDTO.setMainGroup(mainGroup);

            //???????????????????? ???????????????? ?????? ???????? "?????????????????????????????? 300":
            TestValueDTO straight300DTO = new TestValueDTO();
            straight300DTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            straight300DTO.setIdTestHead(11728L);
            straight300DTO.setValue(!newStraight300.getText().equals("") ? Double.valueOf(newStraight300.getText()) : null);
            straight300DTO.setIdConversion(11690L);
            straight300DTO.setMainGroup(mainGroup);

            //???????????????????? ???????????????? ?????? ???????? "?????????????????????????????? 600":
            TestValueDTO straight600DTO = new TestValueDTO();
            straight600DTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            straight600DTO.setIdTestHead(1918897L);
            straight600DTO.setValue(!newStraight600.getText().equals("") ? Double.valueOf(newStraight600.getText()) : null);
            straight600DTO.setIdConversion(11690L);
            straight600DTO.setMainGroup(mainGroup);

            //???????????????????? ???????????????? ?????? ???????? "?????????????????????????????? 600_1":
            TestValueDTO straight600_1DTO = new TestValueDTO();
            straight600_1DTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            straight600_1DTO.setIdTestHead(11731L);
            straight600_1DTO.setValue(!newStraight600_1.getText().equals("") ? Double.valueOf(newStraight600_1.getText()) : null);
            straight600_1DTO.setIdConversion(11690L);
            straight600_1DTO.setMainGroup(mainGroup);

            //???????????????????? ???????????????? ?????? ???????? "?????????????????????????????? 600_2":
            TestValueDTO straight600_2DTO = new TestValueDTO();
            straight600_2DTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            straight600_2DTO.setIdTestHead(11732L);
            straight600_2DTO.setValue(!newStraight600_2.getText().equals("") ? Double.valueOf(newStraight600_2.getText()) : null);
            straight600_2DTO.setIdConversion(11690L);
            straight600_2DTO.setMainGroup(mainGroup);

            //???????????????????? ???????????????? ?????? ???????? "?????????????????????????????? 600_3":
            TestValueDTO straight600_3DTO = new TestValueDTO();
            straight600_3DTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            straight600_3DTO.setIdTestHead(11733L);
            straight600_3DTO.setValue(!newStraight600_3.getText().equals("") ? Double.valueOf(newStraight600_3.getText()) : null);
            straight600_3DTO.setIdConversion(11690L);
            straight600_3DTO.setMainGroup(mainGroup);

            //???????????????????? ???????????????? ?????? ???????? "?????????????????????????????? 600_4":
            TestValueDTO straight600_4DTO = new TestValueDTO();
            straight600_4DTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            straight600_4DTO.setIdTestHead(11734L);
            straight600_4DTO.setValue(!newStraight600_4.getText().equals("") ? Double.valueOf(newStraight600_4.getText()) : null);
            straight600_4DTO.setIdConversion(11690L);
            straight600_4DTO.setMainGroup(mainGroup);

            //???????????????????? ???????????????? ?????? ???????? "?????????????????????????????? 600_5":
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

                //???????????????????? ???????????????? ?????? ???????? "?????????????????????????????? 600 ??????????????":
                TestValueDTO straight600AvgDTO = new TestValueDTO();
                straight600AvgDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
                straight600AvgDTO.setIdTestHead(11736L);
                straight600AvgDTO.setValue(resultAVG);
                straight600AvgDTO.setIdConversion(11690L);
                straight600AvgDTO.setMainGroup(mainGroup);
                testValueDTOs.add(straight600AvgDTO);
            }

            //???????????????????? ???????????????? ?????? ???????? "??????????????":
            TestValueDTO sampleDTO = new TestValueDTO();
            sampleDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            sampleDTO.setIdTestHead(11698L);
            sampleDTO.setValue(newSample.isSelected() ? 1d : 0d);
            sampleDTO.setIdConversion(11690L);
            sampleDTO.setMainGroup(mainGroup);

            //???????????????????? ???????????????? ?????? ???????? "????(???????????? ????????????)":
            TestValueDTO personalStampDTO = new TestValueDTO();
            personalStampDTO.setIdForeignGroup(foreignGroup.getIdForeignGroup());
            personalStampDTO.setIdTestHead(2129173L);
            personalStampDTO.setValue(personalStamp.isSelected() ? 1d : 0d);
            personalStampDTO.setIdConversion(11690L);
            personalStampDTO.setMainGroup(mainGroup);


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
            testValueDTOs.add(personalStampDTO);

            ForeignGroupRequestDTO foreignGroupRequestDTO = new ForeignGroupRequestDTO();
            foreignGroupRequestDTO.setWhoCreate(Constants.FIO);
            String namePersonal = String.valueOf(AccessPersonalRepository.
                    getAccessPersonal(AppProperties.getHost() +
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

    public void selectMasterRecord() {
        reset();
        Long idGroup = cbSelectMain.getItems().get(cbSelectMain.getSelectionModel().getSelectedIndex()).getIdGroup();
        Code code = CodeRepository.findByIdKod(MainValueRepository.findByValue11691(idGroup));
        lblCaptionCode.setText(code.getCode() + " - " + code.getDescription());
        Long idCode = code.getCodePrimaryKey().getIdCode();
        Integer amount = ForeignGroupRepository.findByMainGroupAmount(idGroup);
        setLabel(amount);
        lblAmount.setText(String.valueOf(amount));
        List<Limit> limitList = LimitRepository.findByLimitUniqueKeyIdCode(idCode);

        if (limitList != null) {
            for (Limit limit : limitList) {
                Long idTestHead = limit.getLimitUniqueKey().getIdTestHead();
                for (Map.Entry<Long, ModalField> entry : modalFieldMap.entrySet()) {
                    if (idTestHead.equals(entry.getKey())) {
                        entry.getValue().getCheckBox().setSelected(true);
                        entry.getValue().getTextField().setDisable(false);
                        entry.getValue().getLabel().setDisable(false);
                    }
                }
            }
        }
    }

    private void setLabel (Integer amount) {
        if ((amount % 10 == 1) && (amount % 100 != 11)) {
            lblSpools.setText("??????????????");
            isDoubleDigit(amount);
        } else if ((amount % 10 == 2 || amount % 10 == 3 || amount % 10 == 4)
                && !(amount % 100 == 12 || amount % 100 == 13 || amount % 100 == 14)) {
            lblSpools.setText("??????????????");
            isDoubleDigit(amount);
        } else {
            lblSpools.setText("??????????????");
            isDoubleDigit(amount);
        }

    }

    public void isDoubleDigit(Integer amount) {
        boolean b = amount > 9 && amount < 100;
        if (b) {
            lblSpools.setLayoutX(609);
        }else {
            lblSpools.setLayoutX(604);
        }
    }


    public void selectionMode(String mode) {
        if (mode.equals("?????????? ?????????????? ????????????")) {
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
            lblNumbContainer.setDisable(true);
            tfContainer.setDisable(true);
            lblCaptionCode.setDisable(false);
            lblKey.setVisible(true);
            imgCtrl.setVisible(true);
            imgEnter.setVisible(true);
            imgEsc.setVisible(true);
            lblPart.setVisible(true);
            lblSpools.setVisible(true);
            lblAmount.setVisible(true);

            LOGGER.info("Selected mode: \"??????????\"");
        } else if (mode.equals("????????????????")) {
            Platform.runLater(() -> cbCode.requestFocus());
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
            lblNumbContainer.setDisable(false);
            tfContainer.setDisable(false);
            lblCaptionCode.setDisable(true);
            lblKey.setVisible(false);
            imgCtrl.setVisible(false);
            imgEnter.setVisible(false);
            imgEsc.setVisible(false);
            lblPart.setVisible(false);
            lblSpools.setVisible(false);
            lblAmount.setVisible(false);

            LOGGER.info("Selected mode: \"????????????????\"");
        }
    }

    public void reset() {
        cbTorsion.setSelected(false);
        cbWelds.setSelected(false);
        cbStraight600_1.setSelected(false);
        cbStraight600_2.setSelected(false);
        cbStraight600_3.setSelected(false);
        cbStraight600_4.setSelected(false);
        cbStraight600_5.setSelected(false);
        cbSelectionTorsion();
        cbSelectionWelds();
        cbSelection600_1();
        cbSelection600_2();
        cbSelection600_3();
        cbSelection600_4();
        cbSelection600_5();
    }

    public void cbSelectionTorsion() {
        newTorsion.setDisable(!cbTorsion.isSelected());
        lblTorsion.setDisable(!cbTorsion.isSelected());
    }

    public void cbSelectionWelds() {
        newCountOfWelds.setDisable(!cbWelds.isSelected());
        lblWelds.setDisable(!cbWelds.isSelected());
    }

    public void cbSelection300() {
        newStraight300.setDisable(!cbStraight300.isSelected());
        lblStraight300.setDisable(!cbStraight300.isSelected());
    }

    public void cbSelection600() {
        newStraight600.setDisable(!cbStraight600.isSelected());
        lblStraight600.setDisable(!cbStraight600.isSelected());
    }

    public void cbSelection600_1() {
        newStraight600_1.setDisable(!cbStraight600_1.isSelected());
        lblStraight600_1.setDisable(!cbStraight600_1.isSelected());
    }

    public void cbSelection600_2() {
        newStraight600_2.setDisable(!cbStraight600_2.isSelected());
        lblStraight600_2.setDisable(!cbStraight600_2.isSelected());
    }

    public void cbSelection600_3() {
        newStraight600_3.setDisable(!cbStraight600_3.isSelected());
        lblStraight600_3.setDisable(!cbStraight600_3.isSelected());
    }

    public void cbSelection600_4() {
        newStraight600_4.setDisable(!cbStraight600_4.isSelected());
        lblStraight600_4.setDisable(!cbStraight600_4.isSelected());
    }

    public void cbSelection600_5() {
        newStraight600_5.setDisable(!cbStraight600_5.isSelected());
        lblStraight600_5.setDisable(!cbStraight600_5.isSelected());
    }


    private Map<Long, ModalField> statusCheckBoxMap() {
        Map<Long, ModalField> checkBoxMap = new HashMap<>();
        checkBoxMap.put(11728L, new ModalField(cbStraight300, newStraight300, lblStraight300));
        checkBoxMap.put(11729L, new ModalField(cbTorsion, newTorsion, lblTorsion));
        checkBoxMap.put(11730L, new ModalField(cbWelds, newCountOfWelds, lblWelds));
        checkBoxMap.put(1918897L, new ModalField(cbStraight600, newStraight600, lblStraight600));
        checkBoxMap.put(11731L, new ModalField(cbStraight600_1, newStraight600_1, lblStraight600_1));
        checkBoxMap.put(11732L, new ModalField(cbStraight600_2, newStraight600_2, lblStraight600_2));
        checkBoxMap.put(11733L, new ModalField(cbStraight600_3, newStraight600_3, lblStraight600_3));
        checkBoxMap.put(11734L, new ModalField(cbStraight600_4, newStraight600_4, lblStraight600_4));
        checkBoxMap.put(11735L, new ModalField(cbStraight600_5, newStraight600_5, lblStraight600_5));

        return checkBoxMap;
    }

//    private void selectContextMenu() {
//        if (checkMenuWeek.isSelected()) {
//            showIdForTheWeek();
//        } else if (checkMenuOneMonth.isSelected()) {
//            showIdForTheMonth();
//        } else if (checkMenuSixMonths.isSelected()) {
//            showIdForTheMonth();
//        } else if (checkMenuYear.isSelected()) {
//            showIdForTheYear();
//        } else if (checkMenuTime.isSelected()) {
//            showForTheTimePeriod();
//        }
//    }

//    public void showIdForTheMonth() {
//        checkMenuWeek.setSelected(false);
//        checkMenuSixMonths.setSelected(false);
//        checkMenuYear.setSelected(false);
//        checkMenuTime.setSelected(false);
//        dateStart.setVisible(false);
//        dateEnd.setVisible(false);
////        cbSelectMain.getSelectionModel().clearSelection();
////        cbSelectMain.getItems().clear();
////        cbSelectMain.setItems(null);
//        cbSelectMain.setItems(idGroups);
//        cbSelectMain.getSelectionModel().select(0);
//        selectMasterRecord();
//    }
//
//    public void showIdForTheWeek() {
//        checkMenuOneMonth.setSelected(false);
//        checkMenuSixMonths.setSelected(false);
//        checkMenuYear.setSelected(false);
//        checkMenuTime.setSelected(false);
//        dateStart.setVisible(false);
//        dateEnd.setVisible(false);
////        cbSelectMain.getSelectionModel().clearSelection();
////        cbSelectMain.getItems().clear();
////        cbSelectMain.setItems(null);
//        cbSelectMain.setItems(idGroupWeek);
//        cbSelectMain.getSelectionModel().select(0);
//        selectMasterRecord();
//    }
//
//    public void showIdForTheSixMonths() {
//        checkMenuOneMonth.setSelected(false);
//        checkMenuYear.setSelected(false);
//        checkMenuTime.setSelected(false);
//        checkMenuWeek.setSelected(false);
//        dateStart.setVisible(false);
//        dateEnd.setVisible(false);
//        checkMenuWeek.setSelected(false);
////        cbSelectMain.getSelectionModel().clearSelection();
////        cbSelectMain.getItems().clear();
////        cbSelectMain.setItems(null);
//        cbSelectMain.setItems(idGroupSixMonth);
//        cbSelectMain.getSelectionModel().select(0);
//        selectMasterRecord();
//    }
//
//    public void showIdForTheYear() {
//        checkMenuOneMonth.setSelected(false);
//        checkMenuSixMonths.setSelected(false);
//        checkMenuTime.setSelected(false);
//        checkMenuWeek.setSelected(false);
//        dateStart.setVisible(false);
//        dateEnd.setVisible(false);
//        checkMenuWeek.setSelected(false);
////        cbSelectMain.getSelectionModel().clearSelection();
////        cbSelectMain.getItems().clear();
////        cbSelectMain.setItems(null);
//        cbSelectMain.setItems(idGroupYear);
//        cbSelectMain.getSelectionModel().select(0);
//        selectMasterRecord();
//    }
//
//    public void showForTheTimePeriod() {
//        checkMenuOneMonth.setSelected(false);
//        checkMenuSixMonths.setSelected(false);
//        checkMenuYear.setSelected(false);
//        checkMenuWeek.setSelected(false);
//        dateStart.setVisible(true);
//        dateEnd.setVisible(true);
//    }

}