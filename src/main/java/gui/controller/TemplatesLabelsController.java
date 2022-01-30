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
import javafx.scene.control.*;
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
    private CheckBox cbConstruct;

    @FXML
    private CheckBox cbLanguage;

    @FXML
    private CheckBox cbCode;

    @FXML
    private CheckBox cbLR;

    @FXML
    private CheckBox cbNumbSpool;

    @FXML
    private CheckBox cbPart;

    @FXML
    private CheckBox cbLot;

    @FXML
    private CheckBox cbWelds;

    @FXML
    private CheckBox cbLength;
    @FXML
    private CheckBox cbDatePrint;

    @FXML
    private Label lblLanguage;

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
        languageAction();


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
    templatesLabels.setLr(cbLR.isSelected());
    templatesLabels.setLengthSpool(cbLength.isSelected());
    templatesLabels.setNumberSpool(cbNumbSpool.isSelected());
    templatesLabels.setDatePrint(cbDatePrint.isSelected());
    templatesLabels.setPart(cbPart.isSelected());
    templatesLabels.setWelds(cbWelds.isSelected());
    TemplatesLabelsRepository.saveAndFlush(templatesLabels);

    }
    @FXML
    public void languageAction(){
        if (!cbLanguage.isSelected()){
            lblLanguage.setText("выбран РУС язык");
        } else {
            lblLanguage.setText("выбран ENG язык");
        }
    }

    public void show() {
        stage.showAndWait();
    }

}
