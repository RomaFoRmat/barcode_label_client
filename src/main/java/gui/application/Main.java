package gui.application;


import gui.model.Constants;
import gui.util.FileUtil;
import gui.util.Sftp;
import gui.util.TextFieldUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Main extends Application {
    String sourceHost     = "172.16.172.122";
    Integer sourcePort    = 22;
    String sourceUser     = "root";
    String sourcePassword = "stpc-2plus";
    String sourceDir = "/root/Projects/Release/bsw_spools_scan";
    String localDir = "template\\temp";

    @Override
    public void start(Stage primaryStage) throws Exception {
        final File file = new File("application.lock");
        final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        final FileLock fileLock = randomAccessFile.getChannel().tryLock();

        if (fileLock == null) {
            TextFieldUtil.alertWarning("ПРИЛОЖЕНИЕ УЖЕ ЗАПУЩЕНО!!!");
            stop();
        }

        Sftp.Connection.check(sourceHost, sourcePort, sourceUser, sourcePassword,sourceDir);
        setProperties();

        if (Constants.MAX_VERSION > Constants.CURRENT_VERSION) {
            System.out.println("Start Updater");
            TextFieldUtil.alertInformation("Найдена новая версия программы: " + Constants.MAX_VERSION +
                    ". Установлена: " + Constants.CURRENT_VERSION + "\nПриложение будет обновлено и перезапущено!");
            FileUtil.createFile(new File("run_updater.bat"),Constants.FILE_DATA);
            Runtime.getRuntime().exec("cmd /c start run_updater.bat");
            stop();
        }

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginDialog.fxml"));
        primaryStage.setTitle("Вход в SPOOLS SCAN");
        primaryStage.setScene(new Scene(root, 500, 304));
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        primaryStage.setResizable(false);
//        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();

        FileUtil.folderTempFiles(localDir);
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("ru", "RU"));
//        Locale.setDefault(new Locale("ru"));
//        Locale locale = new Locale("ru", "RU");
//        ResourceBundle bundle = ResourceBundle.getBundle("controls",locale);
        launch(args);

    }

    private void setProperties() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties property = new Properties();
            // set the properties value
            property.load(inputStream);
            String host = property.getProperty("pack.host");
            if (host.isEmpty()) throw new IllegalArgumentException("Set host address in application.properties");
            AppProperties.setHost(property.getProperty("pack.host", ""));
            AppProperties.setVersion(property.getProperty("pack.version", "unknown"));
            Constants.CURRENT_VERSION = Double.valueOf(AppProperties.getVersion());
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
