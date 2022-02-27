package gui.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import gui.application.AppProperties;
import gui.model.BarcodeLabel;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.List;

public class BarcodeLabelRepository {

    public static ObjectMapper mapper = new ObjectMapper();

    public static List<BarcodeLabel> findByNumberSpool(String numberSpool) {
        String url = "http://" + AppProperties.getHost() + "/api/spool/" + numberSpool;
        return getBarcodeLabel(url);
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
}
