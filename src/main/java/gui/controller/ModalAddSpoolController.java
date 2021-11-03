package gui.controller;

import com.jfoenix.controls.JFXComboBox;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.model.Code;
import gui.model.MainGroup;
import gui.model.TestLabel;
import gui.repository.CodeRepository;
import gui.repository.MainGroupRepository;
import gui.repository.TestLabelRepository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private Button okBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField numberPart;
    @FXML
    private TextField numberLot;
    @FXML
    private ComboBox<String> cbLr;
    @FXML
    private ComboBox<Integer> cbCountSpool;
    @FXML
    private DatePicker dateCreateMain;
    private final ObservableList<String> data = FXCollections.observableArrayList("L", "R");
    @FXML
    private ComboBox<String> cbTypeSpool;
    @FXML
    private Label lblCountSpool;
    @FXML
    private TextField newNumberSpool;
    private final ObservableList<Integer> countSpool = FXCollections.observableArrayList(36, 72);
    @FXML
    private TextField newNumberRopeMachine;
    @FXML
    private TextField newPersonalRope;
    @FXML
    private TextField newNumberWeldingMachine;
    @FXML
    private TextField newCountOfWelds;
    @FXML
    private TextField newLength;
    @FXML
    private TextField newTorsionRope;
    @FXML
    private TextField newStraightRope;
    @FXML
    private TextField newTorsion;
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
    @FXML
    private TextField newDefectCode;
    @FXML
    private CheckBox newSample;
    private final ObservableList<String> typeSpool = FXCollections.observableArrayList("BS40", "BS60", "BS80/17", "BS80/33");
    private final ObservableList<String> mode = FXCollections.observableArrayList("СОЗДАНИЕ", "ВЫБОР");
    public ModalAddSpoolController modalAddSpoolController;
    @FXML
    private ComboBox<String> cbCode;
    @FXML
    private DatePicker newDateRope;
    @FXML
    private JFXComboBox<String> cbMode;
    @FXML
    private Label lblSelectMainGroup;
    @FXML
    private JFXComboBox<String> cbSelectMain;
    @FXML
    private VBox vBoxMain0;
    @FXML
    private VBox vBoxMain1;
    private Stage stage;
    private MainGroup mainGroup;
    private List<String> codeList = CodeRepository.findAllByConversionIdConversion();
    private ObservableList<String> codes = FXCollections.observableArrayList(codeList);

    private List<String> idGroupList = MainGroupRepository.getAllIdGroup();
    private ObservableList<String> idGroups = FXCollections.observableArrayList(idGroupList);

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/modalAllSpool.fxml"));

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        modalAddSpoolController = fxmlLoader.getController();

    }

    @FXML
    public void initialize() {
        cbLr.setItems(data);
        cbLr.getSelectionModel().select(0);
        cbCountSpool.setItems(countSpool);
        cbCountSpool.getSelectionModel().select(0);
        cbTypeSpool.setItems(typeSpool);
        cbTypeSpool.getSelectionModel().select(1);
        dateCreateMain.setValue(LocalDate.now());
        cbMode.setItems(mode);
        cbMode.getSelectionModel().select(1);
        cbCode.setItems(codes);
        cbSelectMain.setItems(idGroups);
//        selectionMode();
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

    public void okBtn() {


    }

    public void selectionMode() {
        if (cbMode.getValue().equals("ВЫБОР")) {
            vBoxMain0.setDisable(true);
            vBoxMain1.setDisable(true);
            System.out.println("Select mode ВЫБОР ");
        } else {
            cbSelectMain.setDisable(true);
            vBoxMain0.setDisable(false);
            vBoxMain1.setDisable(false);
            System.out.println("Select mode СОЗДАНИЕ");
        }
    }


}