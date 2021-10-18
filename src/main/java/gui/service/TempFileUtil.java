package gui.service;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TempFileUtil {

    public static File createTemporaryLabel() {
        try {
            FileUtils.cleanDirectory(new File("src\\main\\resources\\template\\temp\\"));
        } catch (IOException e) {
            e.getMessage();
        }
        DateTimeFormatter formatForDate = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH.mm.ss");
        String id = String.valueOf(LocalDateTime.now().format(formatForDate));
        System.out.println(id);
        return new File("src\\main\\resources\\template\\temp\\" + "label -" + id + ".xlsx");
    }

    public static File createQrCodePng(){
        try {
            FileUtils.cleanDirectory(new File("src\\main\\resources\\template\\temp\\"));
        } catch (IOException e) {
            e.getMessage();
        }
        DateTimeFormatter formatForDate = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH.mm.ss");
        String id = String.valueOf(LocalDateTime.now().format(formatForDate));
        System.out.println(id);
        return new File("src\\main\\resources\\template\\temp\\" + "qrCode-" + id + ".png");

    }
}
