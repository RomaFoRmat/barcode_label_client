package gui.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import gui.application.AppProperties;
import gui.application.Main;

import gui.model.Code;
import gui.model.TemplatesLabels;
import gui.model.TestLabel;
import gui.model.dto.MainValueDTO;
import gui.repository.CodeRepository;
import gui.repository.TemplatesLabelsRepository;
import gui.repository.TestLabelRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static gui.repository.CodeRepository.CODE_ENDPOINT;
import static gui.repository.TemplatesLabelsRepository.TEMPLATES_ENDPOINT;

public class TemplatesLabelsController implements Initializable {


    @FXML
    private AnchorPane templatesPane;
    @FXML
    private JFXComboBox<Code> cbCodeSelection;

    @FXML
    private JFXCheckBox cbConstruct;

    @FXML
    private JFXCheckBox cbLanguage;

    @FXML
    private JFXCheckBox cbCode;

    @FXML
    private JFXCheckBox cbLR;

    @FXML
    private JFXCheckBox cbNumbSpool;

    @FXML
    private JFXCheckBox cbPart;

    @FXML
    private JFXCheckBox cbLot;

    @FXML
    private JFXCheckBox cbWelds;

    @FXML
    private JFXCheckBox cbLength;
    @FXML
    private JFXCheckBox cbDatePrint;

    @FXML
    private TableColumn<Code, String> tcInsideCodes;

    @FXML
    private TableView<TemplatesLabels> tableTemplates;

    @FXML
    private TableColumn<TemplatesLabels, Boolean> tcLanguage;

    @FXML
    private TableColumn<TemplatesLabels, Boolean> tcConstruct;

    @FXML
    private TableColumn<TemplatesLabels, Boolean> tcCode;

    @FXML
    private TableColumn<TemplatesLabels, Boolean> tcLR;

    @FXML
    private TableColumn<TemplatesLabels, Boolean> tcNumbSpool;

    @FXML
    private TableColumn<TemplatesLabels, Boolean> tcDatePrint;

    @FXML
    private TableColumn<TemplatesLabels, Boolean> tcPart;

    @FXML
    private TableColumn<TemplatesLabels, Boolean> tcLot;

    @FXML
    private TableColumn<TemplatesLabels, Boolean> tcLength;

    @FXML
    private TableColumn<TemplatesLabels, Boolean> tcWelds;
    private CheckBox select;

    private Stage stage;

    private final ObservableList<TemplatesLabels> table = FXCollections.observableArrayList();
    private List<Code> codeList = CodeRepository.findAllByConversionIdConversion();
    private final ObservableList<Code> codes = FXCollections.observableArrayList(codeList);

    List<TemplatesLabels> templatesLabelsList = TemplatesLabelsRepository.getAllTemplates(TEMPLATES_ENDPOINT);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stage = new Stage();
        stage.setScene(new Scene(templatesPane));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Шаблонизатор");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));

        cbCodeSelection.setItems(codes);
        initializeTableColumns();


    }

    public void initializeTableColumns() {

        tcInsideCodes.setCellValueFactory(new PropertyValueFactory<>("idCode"));
        tcLanguage.setCellValueFactory(new PropertyValueFactory<>("languageLabel"));
        tcConstruct.setCellValueFactory(new PropertyValueFactory<>("construct"));
        tcCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        tcLR.setCellValueFactory(new PropertyValueFactory<>("lr"));
        tcPart.setCellValueFactory(new PropertyValueFactory<>("part"));
        tcLot.setCellValueFactory(new PropertyValueFactory<>("lot"));
        tcWelds.setCellValueFactory(new PropertyValueFactory<>("welds"));
        tcNumbSpool.setCellValueFactory(new PropertyValueFactory<>("numberSpool"));
        tcDatePrint.setCellValueFactory(new PropertyValueFactory<>("datePrint"));
        tcLength.setCellValueFactory(new PropertyValueFactory<>("lengthSpool"));


        table.addAll(templatesLabelsList);
        tableTemplates.setItems(table);
    }


    public void addTemplate() {
    TemplatesLabels templatesLabels = new TemplatesLabels();

    templatesLabels.setIdCode(cbCodeSelection.getItems().
            get(cbCodeSelection.getSelectionModel().getSelectedIndex()).getCodePrimaryKey().getIdCode());

    templatesLabels.setLanguageLabel(cbLanguage.isSelected());
    templatesLabels.setConstruct(cbConstruct.isSelected());
    templatesLabels.setCode(cbCode.isSelected());
    templatesLabels.setLot(cbLot.isSelected());
    templatesLabels.setLengthSpool(cbLength.isSelected());
    templatesLabels.setNumberSpool(cbNumbSpool.isSelected());
    templatesLabels.setDatePrint(cbDatePrint.isSelected());
    TemplatesLabelsRepository.saveAndFlush(templatesLabels);

    }


    public void show() {
        stage.showAndWait();
    }

}
