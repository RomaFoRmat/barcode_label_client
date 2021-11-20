package gui.service;

import gui.controller.ScanController;
import javafx.application.Platform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimerTask;

public class UpdaterUtil extends TimerTask {
//    Date now = new Date();
    ScanController contr;
    public UpdaterUtil(ScanController contr){
        this.contr = contr;
    }
    @Override
    public void run(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (!contr.tabSpoolList.isSelected()) {
                    contr.refreshTable();
                    System.out.println("UPDATED: " + LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
                }
            }
        });
    }
}
