package gui.service;

import gui.model.TestLabel;
import gui.repository.TestLabelRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
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


        File fileTemp = new File("src\\main\\resources\\temp\\Export.xlsx");
//
//        // Create a FileInputStream object
//        // for getting the information of the file
//        FileInputStream fis = new FileInputStream(fileTemp);

      InputStream inp = new FileInputStream("src\\main\\resources\\temp\\Export.xlsx");
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(4);
            Cell cell = row.getCell(0);
            if (cell == null)
                cell = row.createCell(0);
            cell.setCellValue("TEST");
            // Write the output to a file
            OutputStream fileOut = new FileOutputStream("src\\main\\resources\\temp\\Export.xlsx");
                Desktop.getDesktop().edit(fileTemp);
                wb.write(fileOut);

        }

//        // Ensure if file exist or not
//        if (fileTemp.isFile() && fileTemp.exists()) {
//            Desktop.getDesktop().edit(fileTemp);
//            System.out.println("src\\main\\resources\\temp\\Export.xlsx");
//        } else {
//            System.out.println("Export.xlsx either not exist"
//                    + " or can't open");
//        }

    }