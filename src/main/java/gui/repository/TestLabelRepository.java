package gui.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gui.application.AppProperties;
import gui.model.TestLabel;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.List;


public class TestLabelRepository {

    public static ObjectMapper mapper = new ObjectMapper();

    public static List<TestLabel> findByNumberSpool(String numberSpool) {
        String url = "http://" + AppProperties.getHost() + "/api/label/spool/api/label/spool/" + numberSpool;
        return getTestLabel(url);

    }

    public static List<TestLabel> getTestLabel(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), new TypeReference<List<TestLabel>>() {
                    }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
