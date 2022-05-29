package gui.util;

import gui.application.AppProperties;
import gui.application.Main;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledTaskUtil {
    private static final Logger LOGGER = LogManager.getLogger(ScheduledTaskUtil.class);
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Alert alert = new Alert(Alert.AlertType.ERROR,"Потеряно соединение с сервером!");
    private final Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

    public void startScheduleTask() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                getResponseFromTheServer();
            } catch (UnknownHostException ex) {
                System.out.println(ex.getMessage());
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    public void getResponseFromTheServer() throws UnknownHostException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            HttpContext localContext = new BasicHttpContext();
            HttpGet httpget = new HttpGet(AppProperties.getHost());

            HttpResponse response = httpClient.execute(httpget, localContext);
            EntityUtils.consume(response.getEntity());

            System.out.println("Server connection: true | "
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        } catch (IOException e) {
            Platform.runLater(()-> {
                alert.setTitle("Ошибка");
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon/logoBMZ.png")));
                alert.getDialogPane().setGraphic(new ImageView("/icon/serverError.png"));
                alert.setHeaderText("Server connection: FAILED");
                alert.showAndWait();
            });
            LOGGER.error("Connect to {} failed: {}", AppProperties.getHost(), InetAddress.getLocalHost());
        }
    }

    public boolean preLaunchCheck() throws UnknownHostException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            HttpContext localContext = new BasicHttpContext();
            HttpGet httpget = new HttpGet(AppProperties.getHost());

            HttpResponse response = httpClient.execute(httpget, localContext);
            EntityUtils.consume(response.getEntity());

        } catch (IOException e) {
            LOGGER.error("Pre-launch to {} failed: {}", AppProperties.getHost(), InetAddress.getLocalHost());
            return false;
        }
        return true;
    }
}
