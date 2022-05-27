package gui.application;

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
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.nio.channels.FileLock;
import java.util.*;

import static gui.model.Constants.*;


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

        boolean check = Sftp.check(SOURCE_HOST, SOURCE_PORT, SOURCE_USER, SOURCE_PASSWORD, SOURCE_DIR);
        setProperties();

//        if (ping(AppProperties.getIp()))
//            System.out.println("Сервер доступен");
//        else
//            TextFieldUtil.alertError("Нет связи с сервером!");

//        new Thread (() -> {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()){

            HttpContext localContext = new BasicHttpContext();
            HttpGet httpget = new HttpGet(AppProperties.getHost());

            HttpResponse response = httpClient.execute(httpget, localContext);
            EntityUtils.consume(response.getEntity());

        } catch (ConnectException e) {
            TextFieldUtil.alertError("Нет связи с сервером!");
            System.out.println(e.getMessage());
        }


//        }).start();


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

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginDialog.fxml"));
        primaryStage.setTitle("Вход в SPOOLS SCAN");
        primaryStage.setScene(new Scene(root, 500, 304));
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
        primaryStage.setResizable(false);
//        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();

        FileUtil.folderTempFiles(LOCAL_DIR);
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
            AppProperties.setIp(property.getProperty("pack.ipAddress"));
            CURRENT_VERSION = Double.valueOf(AppProperties.getVersion());
        } catch (IOException io) {
            io.printStackTrace();
        }
    }



    private static boolean ping(String host) {
        try {
            InetAddress address = InetAddress.getByName(host);
            return address.isReachable(3000);
        } catch (IOException exc){
            return false;
        }
    }



}
