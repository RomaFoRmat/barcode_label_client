package gui.controller;

import com.jfoenix.controls.*;
import gui.application.AppProperties;
import gui.application.Main;

import gui.model.Code;
import gui.model.Constants;
import gui.model.TemplatesLabels;
import gui.model.TestLabel;
import gui.model.dto.TemplateLabelDTO;
import gui.repository.CodeRepository;
import gui.repository.TemplatesLabelsRepository;
import gui.repository.TestLabelRepository;
import gui.service.TextFieldService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    private TableColumn<TemplateLabelDTO, String> tcInsideCodes;

    @FXML
    private TableView<TemplateLabelDTO> tableTemplates;

    @FXML
    private TableColumn<TemplateLabelDTO, Boolean> tcLanguage;

    @FXML
    private TableColumn<TemplateLabelDTO, Boolean> tcConstruct;

    @FXML
    private TableColumn<TemplateLabelDTO, Boolean> tcCode;

    @FXML
    private TableColumn<TemplateLabelDTO, Boolean> tcLR;

    @FXML
    private TableColumn<TemplateLabelDTO, Boolean> tcNumbSpool;

    @FXML
    private TableColumn<TemplateLabelDTO, Boolean> tcDatePrint;

    @FXML
    private TableColumn<TemplateLabelDTO, Boolean> tcPart;

    @FXML
    private TableColumn<TemplateLabelDTO, Boolean> tcLot;

    @FXML
    private TableColumn<TemplateLabelDTO, Boolean> tcLength;

    @FXML
    private TableColumn<TemplateLabelDTO, Boolean> tcWelds;

    private Stage stage;

    private final ObservableList<TemplateLabelDTO> table = FXCollections.observableArrayList();
    private List<Code> codeList = CodeRepository.findAllByConversionIdConversion();
    private final ObservableList<Code> codes = FXCollections.observableArrayList(codeList);


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
        table.clear();

        List<TemplateLabelDTO> templateLabelDTOList = TemplatesLabelsRepository.getAllTemplates(TEMPLATES_ENDPOINT);

        tcInsideCodes.setCellValueFactory(new PropertyValueFactory<>("kod"));
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

        table.addAll(templateLabelDTOList);
        tableTemplates.setItems(table);
        
    }

    @FXML
    public void addTemplate() {
        if (cbCodeSelection.getValue() != null) {

            TemplatesLabels templatesLabels = new TemplatesLabels();
            Long id = cbCodeSelection.getItems().
                    get(cbCodeSelection.getSelectionModel().getSelectedIndex()).getCodePrimaryKey().getIdCode();
            List<TemplatesLabels> templatesLabelsList = TemplatesLabelsRepository.
                    getTemplate("http://" + AppProperties.getHost() + "/api/templates/" + id);

            if (templatesLabelsList != null && templatesLabelsList.isEmpty()) {
                templatesLabels.setIdCode(id);
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
                initializeTableColumns();
                Code code = CodeRepository.getIdKod("http://" + AppProperties.getHost() +
                        "/api/codeDTO/" + templatesLabels.getIdCode());
                TextFieldService.alertInformation("Шаблон для кода " + code.getCode() + " успешно добавлен!");

            }else { TextFieldService.alertWarning("Шаблон для данного кода уже существует!"); }
        } else { TextFieldService.alertWarning("Выберете КОД для создания шаблона!"); }
    }

    @FXML
    public void updateTemplate() {

    }

    @FXML
    public void deleteAction() {

        TemplatesLabels templatesLabels = new TemplatesLabels();

        templatesLabels.setIdCode(cbCodeSelection.getItems().
                get(cbCodeSelection.getSelectionModel().getSelectedIndex()).getCodePrimaryKey().getIdCode());

        TemplatesLabelsRepository.delete("http://" + AppProperties.getHost() + "/api/templates/" + templatesLabels.getIdCode());

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

    @FXML
    public void getSelected() {
        int index ;
        index = tableTemplates.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        cbCodeSelection.getItems().get(cbCodeSelection.getSelectionModel().
                getSelectedIndex()).setCode(tcInsideCodes.getCellData(index));
//        cbCodeSelection.setValue(tcInsideCodes.getCellData(index));
        cbLanguage.setSelected(tcLanguage.getCellData(index));
        cbConstruct.setSelected(tcConstruct.getCellData(index));
        cbCode.setSelected(tcConstruct.getCellData(index));
        cbLR.setSelected(tcLR.getCellData(index));
        cbNumbSpool.setSelected(tcNumbSpool.getCellData(index));
        cbPart.setSelected(tcPart.getCellData(index));
        cbLot.setSelected(tcLot.getCellData(index));
        cbWelds.setSelected(tcWelds.getCellData(index));
        cbLength.setSelected(tcWelds.getCellData(index));
        cbDatePrint.setSelected(tcDatePrint.getCellData(index));
    }
}
