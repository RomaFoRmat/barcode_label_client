package gui.model;

import java.time.LocalDateTime;

public class Constants {
    public static String SPOOL_NUMBER;
    public static String FIO_VIEW;
    public static String FIO;
    public static Long ID_PERSONALS;
    public static String IP_ADDRESS;
    public static LocalDateTime DATE_START;
    public static LocalDateTime DATE_END;
    public static Double MAX_VERSION;
    public static Double CURRENT_VERSION;
    public static final String SIDE_MENU = "/fxml/sideMenu.fxml";
    public static final String FILE_DATA = "chcp 1251\n" +
            "java -jar C:\\bsw_spools_scan\\updater\\updater-1.0.jar\n" + "exit";
    public static final String SOURCE_HOST = "172.16.172.122";
    public static final Integer SOURCE_PORT = 22;
    public static final String SOURCE_USER  = "root";
    public static final String SOURCE_PASSWORD = "stpc-2plus";
    public static final String SOURCE_DIR = "/root/Projects/Release/bsw_spools_scan";
    public static final String LOCAL_DIR = "template\\temp";
    public static final String FOLDER_PATH = "C:\\bsw_spools_scan\\bat\\launch_updater.bat";
    public static final String START_UPDATE = "cmd /c start C:\\bsw_spools_scan\\bat\\launch_updater.bat";
}
