package gui.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import gui.application.AppProperties;
import gui.model.Personals;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class PersonalsRepository {

    public static ObjectMapper mapper = new ObjectMapper();

    public static Personals findByPassword(String password) {
        String url = "http://" + AppProperties.getHost() + "/api/personals/" + password;
        return getUser(url);
    }

    public static Personals getUser(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), Personals.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
