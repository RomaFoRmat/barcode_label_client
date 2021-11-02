package gui.controller;

import com.jfoenix.controls.JFXComboBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ModalAddSpoolController {

    public ModalAddSpoolController modalAddSpoolController;
    ObservableList<String> data = FXCollections.observableArrayList("L", "R");
    ObservableList<Integer> countSpool = FXCollections.observableArrayList(36, 72);
    ObservableList<String> typeSpool = FXCollections.observableArrayList("BS60", "BS40", "BS80/17", "BS80/33");
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
    @FXML
    private ComboBox<?> cbCode;
    @FXML
    private ComboBox<String> cbTypeSpool;
    @FXML
    private Label lblCountSpool;
    @FXML
    private TextField newNumberSpool;
    @FXML
    private TextField newDateRope;
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
    @FXML
    private JFXComboBox<?> cbMode;
    @FXML
    private Label cbCreateMainGroup;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/modalAllSpool.fxml"));

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        modalAddSpoolController = fxmlLoader.getController();

        cbLr.setItems(data);
        cbCountSpool.setItems(countSpool);

    }

    @FXML
    public void initialize() {
        cbLr.setItems(data);
        cbLr.getSelectionModel().select(0);
        cbCountSpool.setItems(countSpool);
        cbCountSpool.getSelectionModel().select(0);
        cbTypeSpool.setItems(typeSpool);
        dateCreateMain.setValue(LocalDate.now());
    }
}
