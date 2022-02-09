package gui.controller;

import com.jfoenix.controls.*;
import gui.application.AppProperties;
import gui.application.Main;

import gui.model.Code;
import gui.model.Constants;
import gui.model.TemplatesLabels;
import gui.model.dto.TemplateLabelDTO;
import gui.repository.CodeRepository;
import gui.repository.TemplatesLabelsRepository;
import gui.service.TextFieldService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
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

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private Hyperlink linkHelp;

    private Stage stage;

    private final ObservableList<TemplateLabelDTO> table = FXCollections.observableArrayList();
    private List<Code> codeList = CodeRepository.findAllByConversionIdConversion();
    private final ObservableList<Code> codes = FXCollections.observableArrayList(codeList);
    public static final Logger LOGGER = LogManager.getLogger(TemplatesLabelsController.class);


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

    /**
     * Получение объекта в таблице шаблонов
     * */
    private TemplateLabelDTO getSelectedTemplate(){
        return tableTemplates.getSelectionModel().getSelectedItem();
    }

    /**
     * Иниализация таблицы шаблонов
     * */
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

    /**
     * Добавление шаблона
     * */
    @FXML
    public void addTemplate() {
        if (cbCodeSelection.getValue() != null) {
            TemplatesLabels templatesLabels = new TemplatesLabels();
            Code selectedCode = cbCodeSelection.getItems().get(cbCodeSelection.getSelectionModel().getSelectedIndex());

            if (!isTemplateExists(selectedCode.getCode())) {
                templatesLabels.setIdCode(selectedCode.getCodePrimaryKey().getIdCode());
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

                TemplatesLabelsRepository.addTemplate(templatesLabels);
                initializeTableColumns();
                Code code = CodeRepository.getIdKod("http://" + AppProperties.getHost() +
                        "/api/codeDTO/" + templatesLabels.getIdCode());
                TextFieldService.alertInformation("Шаблон для кода " + code.getCode() + " успешно добавлен!");
                LOGGER.info("Added template for code:{} - {}", code.getCode(), Constants.FIO_VIEW );

            }else { TextFieldService.alertWarning("Шаблон для данного кода уже существует!"); }
        } else { TextFieldService.alertWarning("Выберете КОД для создания шаблона!"); }
    }

    /**
     * Редактирование шаблона
     * */
    @FXML
    public void updateTemplate() {
        if(getSelectedTemplate() != null) {
            Long idTemplate = getSelectedTemplate().getTemplatesLabels().getIdTemplate();

            List<TemplatesLabels> templatesLabelsList = TemplatesLabelsRepository.
                    getByIdTemplate("http://" + AppProperties.getHost() + "/api/templates-id/" + idTemplate);

            TemplatesLabels labels = templatesLabelsList.get(0);

            labels.getIdTemplate();
            labels.setIdCode(cbCodeSelection.getItems().
                    get(cbCodeSelection.getSelectionModel().getSelectedIndex()).getCodePrimaryKey().getIdCode());
            labels.setLanguageLabel(cbLanguage.isSelected());
            labels.setConstruct(cbConstruct.isSelected());
            labels.setCode(cbCode.isSelected());
            labels.setLot(cbLot.isSelected());
            labels.setLr(cbLR.isSelected());
            labels.setLengthSpool(cbLength.isSelected());
            labels.setNumberSpool(cbNumbSpool.isSelected());
            labels.setDatePrint(cbDatePrint.isSelected());
            labels.setPart(cbPart.isSelected());
            labels.setWelds(cbWelds.isSelected());

            TemplatesLabelsRepository.update(labels);
            TextFieldService.alertInformation("Шаблон для кода " + getSelectedTemplate().getKod() + " успешно отредактирован!");
            LOGGER.info("Update template for code:{} - {}", getSelectedTemplate().getKod(), Constants.FIO_VIEW );
            initializeTableColumns();
        }else{
            TextFieldService.alertWarning("Для редактирования необходимо выбрать нужный КОД в таблице шаблонов!");
        }
    }

    /**
     * Удаление шаблона
     * */
    @FXML
    public void deleteAction() {
        if(getSelectedTemplate() != null){
        TemplatesLabels templatesLabels = new TemplatesLabels();
        templatesLabels.setIdTemplate(tableTemplates.getSelectionModel().getSelectedItem().
                                        getTemplatesLabels().getIdTemplate());
        TemplatesLabelsRepository.delete("http://" + AppProperties.getHost() + "/api/templates/" +
                                        templatesLabels.getIdTemplate());
        TextFieldService.alertInformation("Шаблон для кода " + getSelectedTemplate().getKod() + " успешно удалён!");
        LOGGER.info("Delete template for code:{} - {}", getSelectedTemplate().getKod(), Constants.FIO_VIEW );
        initializeTableColumns();
        } else {
            TextFieldService.alertWarning("Выберите в таблице нужный КОД для удаления!");
        }
    }

    /**
     * Отображение языка(lblLanguage) в зависимости от значения CheckBox(cbLanguage)
     * */
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

    /**
     * Проверка на наличие шаблона в таблице
     * */
    private boolean isTemplateExists(String idCode){
        for(TemplateLabelDTO template: table){
            if(template.getKod().equals(idCode)){
                return true;
            }
        }
        return false;
    }

    /**
     * Инициализация компонентов(CheckBox,ComboBox) при нажатии строки из таблицы
     * */
    @FXML
    public void getSelected() {
        cbCodeSelection.getSelectionModel().select(searchCodeIndex(getSelectedTemplate().getKod()));
        cbLanguage.setSelected(getSelectedTemplate().getTemplatesLabels().getLanguageLabel());
        cbConstruct.setSelected(getSelectedTemplate().getTemplatesLabels().getConstruct());
        cbCode.setSelected(getSelectedTemplate().getTemplatesLabels().getCode());
        cbLR.setSelected(getSelectedTemplate().getTemplatesLabels().getLr());
        cbNumbSpool.setSelected(getSelectedTemplate().getTemplatesLabels().getNumberSpool());
        cbPart.setSelected(getSelectedTemplate().getTemplatesLabels().getPart());
        cbLot.setSelected(getSelectedTemplate().getTemplatesLabels().getLot());
        cbWelds.setSelected(getSelectedTemplate().getTemplatesLabels().getWelds());
        cbLength.setSelected(getSelectedTemplate().getTemplatesLabels().getLengthSpool());
        cbDatePrint.setSelected(getSelectedTemplate().getTemplatesLabels().getDatePrint());
        languageAction();
    }

    /**
     * Поиск индекса кода(ComboBox)
     * */
    private int searchCodeIndex(String requestCode) {
        for( int i=0;i<cbCodeSelection.getItems().size();i++){
            if(cbCodeSelection.getItems().get(i).getCode().equals(requestCode)){
                return i;
            }
        }
        return 0;
    }

    public void helpAction() {
        TextFieldService.alertHelp("ДЛЯ ДОБАВЛЕНИЯ ШАБЛОНА НЕОБХОДИМО:\n" +
                "1) Выбрать \"КОД\" в поле со списком. \n" +
                "2) Выбрать язык(РУС - по умолчанию, ENG - выделить).\n" +
                "3) Отметить нужные параметры, которые вы желаете видеть на этикетке/QR-Code.\n" +
                "4) Нажать кнопку \"Добавить\".\n" +
                "\n" +
                "ДЛЯ РЕДАКТИРОВАНИЯ ШАБЛОНА НЕОБХОДИМО:\n" +
                "1) Выбрать в таблице,нужный \"КОД\"(строку).\n" +
                "2) Отметить или убрать нужные параметры.\n" +
                "3) Нажать кнопку \"Изменить\".\n" +
                "\n" +
                "ДЛЯ УДАЛЕНИЯ ШАБЛОНА НЕОБХОДИМО:\n" +
                "1) Выбрать в таблице нужный \"КОД\".\n" +
                "2) Нажать кнопку \"Удалить\".");
    }
}
