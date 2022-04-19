package gui.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gui.application.AppProperties;
import gui.model.Limit;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.List;

public class LimitRepository {
    public static ObjectMapper mapper = new ObjectMapper();

    public static List<Limit> findLimitByLimitUniqueKey(Long idCode, Long idTestHead){
        String url = AppProperties.getHost() + "/api/show-visible/" + idCode + idTestHead;
        return getStatusField(url);
    }

    public static List<Limit> findByLimitUniqueKeyIdCode(Long idCode){
        String url = AppProperties.getHost() + "/api/show-experience/" + idCode;
        return getFields(url);
    }

    private static List<Limit> getFields(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<Limit>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Limit> getStatusField(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<Limit>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
