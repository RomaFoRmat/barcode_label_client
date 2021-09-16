package gui.service;

import gui.model.TestLabel;
import gui.repository.TestLabelRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bouncycastle.util.test.Test;

import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

public class LabelListCreator {

    public static void createExcelList() throws IOException {

        // Create a file object
        // for the path of existing Excel file
        // Give the path of the file as parameter
        // from where file is to be read
        File fileTemp = new File("src\\main\\resources\\temp\\Export.xlsx");

        // Create a FileInputStream object
        // for getting the information of the file
        FileInputStream fis = new FileInputStream(fileTemp);

        // Getting the workbook instance for XLSX file
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("1х2х0,30SHT Ульян");
        XSSFRow row = sheet.createRow(4);
        row.createCell(1).setCellValue("123");

        FileOutputStream fos = new FileOutputStream("Export.xlsx");
        workbook.write(fos);
        fos.close();

        // Ensure if file exist or not
        if (fileTemp.isFile() && fileTemp.exists()) {
            Desktop.getDesktop().edit(fileTemp);
            System.out.println("src\\main\\resources\\temp\\Export.xlsx");
        } else {
            System.out.println("Export.xlsx either not exist"
                    + " or can't open");
        }

    }
}