package gui.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gui.application.AppProperties;
import gui.model.AccessPersonal;
import gui.model.GroupsOfPersonal;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.List;

public class AccessPersonalRepository {

    public static ObjectMapper mapper = new ObjectMapper();

    public static List<AccessPersonal> findAllByAccessPersonalPrimaryKeyPersonalsIdPersonal(Long idPersonal) {
        String url = "http://" + AppProperties.getHost() + "/getAccessPersonal/" + idPersonal;
        return getAccessPersonal(url);
    }

    public static List<AccessPersonal> getAccessPersonal(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<AccessPersonal>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
