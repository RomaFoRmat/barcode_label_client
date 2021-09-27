package gui.service;

import gui.model.CellStyleOption;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Times New Roman");
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
        font.setFontHeightInPoints((short) 18);
        font.setFontName("Times New Roman");
        style.setFont(font);
        return style;
    }

    public static CellStyle createStyleForCountry(XSSFWorkbook workbook) {
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

    public static CellStyle createEnlarged2Style(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 13);
        font.setFontName("Times New Roman");
        style.setFont(font);

        return style;
    }

}
