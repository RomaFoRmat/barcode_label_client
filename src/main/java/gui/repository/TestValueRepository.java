package gui.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gui.application.AppProperties;
import gui.model.TestValue;
import gui.service.LocalDateAdapterUtil;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class TestValueRepository {

    public static final String TEST_VALUE_ENDPOINT = "http://" + AppProperties.getHost() + "/api/allTestValuesByIdConversion11690";
    public static ObjectMapper mapper = new ObjectMapper();

    public static TestValue saveAndFlush(TestValue testValue) {
        String url = "http://" + AppProperties.getHost() + "/api/createTestValues";
        return getResponseEntity(url, testValue);
    }

    public static List<TestValue> findByIdConversionAndIdTestHead(Long idTestHead) {
        String url = "http://" + AppProperties.getHost() + "/api/allTestValuesByIdConversion11690/" + idTestHead;
        return getTestValue(url);
    }

    public static TestValue getResponseEntity(String url, TestValue testValue) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapterUtil())
                    .create();
            mapper.registerModule(new JavaTimeModule());
            httpPost.setEntity(new StringEntity(gson.toJson(testValue), StandardCharsets.UTF_8));
            return client.execute(httpPost, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), TestValue.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<TestValue> getTestValue(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<TestValue>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<TestValue> findAllByIdConversion() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(TEST_VALUE_ENDPOINT);
            mapper.registerModule(new JavaTimeModule());
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<TestValue>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
