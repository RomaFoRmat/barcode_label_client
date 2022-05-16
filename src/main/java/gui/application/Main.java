package gui.application;


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
import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final File file = new File("application.lock");
        final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        final FileLock fileLock = randomAccessFile.getChannel().tryLock();

        if (fileLock == null) {
            TextFieldUtil.alertWarning("ПРИЛОЖЕНИЕ УЖЕ ЗАПУЩЕНО!!!");
            stop();
        }

//        Sftp.Connection.check(sourceHost, sourcePort, sourceUser, sourcePassword, sourceFile);

        setProperties();

//        File currentDir = new File("d:/Soft/app");
//        displayAllVersionsInDirectory(currentDir);

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginDialog.fxml"));
        primaryStage.setTitle("Вход в SPOOLS SCAN");
        primaryStage.setScene(new Scene(root, 500, 304));
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        primaryStage.setResizable(false);
//        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
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
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private static void displayAllVersionsInDirectory(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String[] versions = file.getName().split("-");
                String res = versions[versions.length - 1];
                System.out.println(res.replaceAll("\\.jar",""));
            }
        }
    }
}
