package gui.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import gui.application.AppProperties;
import gui.model.BarcodeLabel;
import gui.model.TableSpools;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class BarcodeLabelRepository {

    public static ObjectMapper mapper = new ObjectMapper();

    public static List<BarcodeLabel> findByNumberSpool(String numberSpool) {
        String url = AppProperties.getHost() + "/api/spool/" + numberSpool;
        return getBarcodeLabel(url);
    }
    public static List<BarcodeLabel> findByNumberSpoolBetween(LocalDateTime dateStart,
                                                              LocalDateTime dateEnd, String numberSpool) {
//        String url = AppProperties.getHost() + "/api/spool-between/" + amountDays + "/"+ numberSpool;
        String url = AppProperties.getHost() + "/api/spool-between/" + dateStart + "/" + dateEnd + "/"+ numberSpool;
        return getBarcodeLabelBetween(url);
    }

    public static List<BarcodeLabel> getBarcodeLabel(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            mapper.registerModule(new JavaTimeModule());//для нужного формата даты из JSON'a
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<BarcodeLabel>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<BarcodeLabel> getBarcodeLabelBetween(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            mapper.registerModule(new JavaTimeModule());//для нужного формата даты из JSON'a
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<BarcodeLabel>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
