package gui.application;

import gui.util.*;
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

import static gui.model.Constants.*;


public class Main extends Application {
    ScheduledTaskUtil serverConnectionTask = new ScheduledTaskUtil();   //планировщик задач

    @Override
    public void start(Stage primaryStage) throws Exception {

        final File file = new File("application.lock");   //файл для блокировки нежелательного запуска app
        final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");     //чтение/запись файла
        final FileLock fileLock = randomAccessFile.getChannel().tryLock();  //получаем канал файла и вызываем tryLock()

        if (fileLock == null) {
            //tryLock() – возвращает FileLock или null, если блокировка не может быть получена
            TextFieldUtil.alertWarning("ПРИЛОЖЕНИЕ УЖЕ ЗАПУЩЕНО!!!");
            stop();
        }

        boolean check = SftpUtil.check(SOURCE_HOST, SOURCE_PORT, SOURCE_USER, SOURCE_PASSWORD, SOURCE_DIR); //SFTP Connect
        setProperties();

        /**
         *Запуск updater_client.jar:
         */
        if (check) {
            if (MAX_VERSION > CURRENT_VERSION) {
                System.out.println("Start Updater");
                TextFieldUtil.alertInformation("Найдена новая версия программы: " + MAX_VERSION +
                        ". Установлена: " + CURRENT_VERSION + "\nПриложение будет обновлено и перезапущено!");
                FileUtil.folderTempFiles("C:\\bsw_spools_scan\\bat\\");
                FileUtil.createFile(new File(FOLDER_PATH), FILE_DATA);
                Runtime.getRuntime().exec(START_UPDATE);
                stop();
            }
        }

        if (serverConnectionTask.preLaunchCheck()) {
            serverConnectionTask.startScheduleTask();  //запуск задачи на проверку связи с сервером c заданным временем
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginDialog.fxml"));
            primaryStage.setTitle("Вход в SPOOLS SCAN");
            primaryStage.setScene(new Scene(root, 500, 304));
            primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
            primaryStage.setResizable(false);
//            primaryStage.initStyle(StageStyle.UTILITY);
            primaryStage.show();

            FileUtil.folderTempFiles(LOCAL_DIR);
        } else {
            TextFieldUtil.alertError("Нет соединения с сервером!\nЗапуск программы невозможен. Работа будет завершена.");
        }
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("ru", "RU"));
//        Locale locale = new Locale("ru", "RU");
//        ResourceBundle.getBundle("com/sun/javafx/scene/control/skin/resources/controls",locale);
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
            AppProperties.setIp(property.getProperty("pack.ipAddress"));
            CURRENT_VERSION = Double.valueOf(AppProperties.getVersion());
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }
}
