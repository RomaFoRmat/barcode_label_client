package gui.service;

import gui.model.TestLabel;
import gui.repository.TestLabelRepository;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

        try {

            FileInputStream file = new FileInputStream(new File(String.valueOf(fileTemp)));

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Cell cell = null;

            //Update the value of cell
            cell = sheet.getRow(4).getCell(0);
            cell.setCellValue("TEST");
            cell = sheet.getRow(5).getCell(1);
            cell.setCellValue("TEST");
//            Row row = sheet.getRow(0);
//            row.createCell(3).setCellValue("Value 2");

            file.close();


            FileOutputStream outFile =new FileOutputStream(new File(String.valueOf(fileTemp)));
            workbook.write(outFile);
            outFile.close();
            Desktop.getDesktop().open(fileTemp);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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