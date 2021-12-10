package gui.service;

import gui.controller.ScanController;
import javafx.application.Platform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

public class UpdaterUtil extends TimerTask {
    //    Date now = new Date();
    ScanController scanController;

    public UpdaterUtil(ScanController scanController) {
        this.scanController = scanController;
    }

    @Override
    public void run() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (!scanController.tabSpoolList.isSelected()) {
                    scanController.refreshTable();
                    System.out.println("UPDATED: " + LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
                }
            }
        });
    }
}
