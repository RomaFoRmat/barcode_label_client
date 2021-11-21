package gui.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gui.application.AppProperties;
import gui.model.MainGroup;
import gui.model.MainValue;
import gui.service.LocalDateAdapterUtil;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

public class MainValueRepository {

    public static ObjectMapper mapper = new ObjectMapper();

    public static MainValue saveAndFlush(MainValue mainValue) {
        String url = "http://" + AppProperties.getHost() + "/api/createMainValues";
        return getResponseEntity(url, mainValue);
    }

    public static List<MainValue> findByMainValuePrimaryKeyIdHead(Long idHead) {
        String url = "http://" + AppProperties.getHost() + "/api/allMainValues/" + idHead;
        return getMainValue(url);
    }

    public static List<String> findFirstByMainValuePrimaryKeyIdHeadOrderByNumberValueDesc() {
        String url = "http://" + AppProperties.getHost() + "/api/lastProtocol";
        return getLastProtocol(url);
    }

    public static List<String> getLastProtocol(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), new TypeReference<List<String>>() {
                    }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static MainValue getResponseEntity(String url, MainValue mainValue) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapterUtil())
                    .create();
            mapper.registerModule(new JavaTimeModule());
            httpPost.setEntity(new StringEntity(gson.toJson(mainValue), StandardCharsets.UTF_8));
            return client.execute(httpPost, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), MainValue.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<MainValue> getMainValue(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<MainValue>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
