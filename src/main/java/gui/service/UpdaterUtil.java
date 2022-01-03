package gui.service;

import gui.controller.ScanController;
import javafx.application.Platform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

public class UpdaterUtil extends TimerTask {
    //    Date now = new Date();
    ScanController scanContr;

    public UpdaterUtil(ScanController scanContr) {
        this.scanContr = scanContr;
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            if (!scanContr.tabSpoolList.isSelected()) {
                scanContr.refreshTable();
                System.out.println("UPDATED: " + LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            }
        });
    }
}
