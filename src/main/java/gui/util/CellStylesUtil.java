package gui.util;

import gui.model.CellStyleOption;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.CellStyle;

public class CellStylesUtil {

    public static CellStyle getCellStyle(XSSFWorkbook workbook, CellStyleOption cellStyleOption) {

        switch (cellStyleOption) {
            case BASE:
                return createBaseStyle(workbook);
            case ENLARGED:
                return createEnlargedStyle(workbook);
            case ENLARGED2:
                return createEnlarged2Style(workbook);
            case COUNTRY:
                return createStyleForCountry(workbook);
            default:
                return null;
        }
    }

    public static CellStyle createBaseStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 8);
        font.setFontName("Times New Roman");
//        style.setBorderBottom(BorderStyle.THIN);
//        font.setUnderline(Font.U_SINGLE);
        style.setFont(font);
        return style;
    }

    public static CellStyle createEnlargedStyle(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 15);
        font.setFontName("Times New Roman ");
        style.setFont(font);
        return style;
    }

    public static CellStyle createStyleForCountry(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 9);
        font.setFontName("Times New Roman");
        style.setFont(font);
        return style;
    }

    public static CellStyle createEnlarged2Style(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Times New Roman");
        style.setFont(font);

        return style;
    }

}
