package gui.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import gui.application.AppProperties;
import gui.model.Code;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CodeRepository {

    public static final String CODE_ENDPOINT = AppProperties.getHost() + "/api/getAllCodes";
    public static ObjectMapper mapper = new ObjectMapper();

    public static List<Code> findAllByConversionIdConversion() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(CODE_ENDPOINT);
            mapper.registerModule(new JavaTimeModule());
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<Code>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static Code findByIdKod(Long code) {
        String url = AppProperties.getHost() + "/api/codeDTO/" + code;
        return getIdKod(url);
    }

    private static Code getIdKod(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            mapper.registerModule(new JavaTimeModule());//для нужного формата даты из JSON'a
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<Code>() {
                            }));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
