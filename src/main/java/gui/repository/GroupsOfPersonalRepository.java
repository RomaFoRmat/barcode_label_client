package gui.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gui.application.AppProperties;
import gui.model.GroupsOfPersonal;
import gui.model.TestValue;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.List;

public class GroupsOfPersonalRepository {
    public static ObjectMapper mapper = new ObjectMapper();

    public static List<GroupsOfPersonal> findAllByIdGroup(Long idGroup) {
        String url = "http://" + AppProperties.getHost() + "/api/getGroupsOfPersonal/"  + idGroup;
        return getGroupsOfPersonal(url);
    }

    public static List<GroupsOfPersonal> getGroupsOfPersonal(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<GroupsOfPersonal>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
