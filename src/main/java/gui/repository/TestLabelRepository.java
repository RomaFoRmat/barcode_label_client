package gui.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import gui.application.AppProperties;
import gui.model.TestLabel;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class TestLabelRepository {

    public static ObjectMapper mapper = new ObjectMapper();

    public static final String SPOOLS_ENDPOINT = "http://" + AppProperties.getHost() + "/api/label/spool";

    public static List<TestLabel> getAllSpools() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(SPOOLS_ENDPOINT);
            mapper.registerModule(new JavaTimeModule());
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<TestLabel>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<TestLabel> findByNumberSpool(String numberSpool) {
        String url = "http://" + AppProperties.getHost() + "/api/label/spool/" + numberSpool;
        return getTestLabel(url);
    }

    public static List<TestLabel> getTestLabel(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            mapper.registerModule(new JavaTimeModule());//для нужного формата даты из JSON'a
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<TestLabel>>() {
                            }));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
