package gui.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SlideMenuHamburger {
    @FXML
    private VBox vbox;
    @FXML
    private JFXButton btnLab2;
    @FXML
    private JFXButton btnChangeUser;
    @FXML
    private JFXButton btnAbout;
    @FXML
    private JFXButton btnExit;

    private Stage stage;

//    @FXML
//    void aboutTheProgramAction() {
//
//    }
//
//    @FXML
//    void changeUserAction() {
//
//
//    }

    @FXML
    void exitAction() {
        stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void openProjectStart2() throws IOException {
        Runtime.getRuntime().exec("C:\\Program Files (x86)\\LaboratoryResearches2\\ProjectStart2.exe");
        System.out.println("Открыть: Лабораторные испытания СтПЦ-2");
    }
}
