package gui.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gui.application.AppProperties;
import gui.model.MainGroup;
import gui.model.dto.MainGroupRequestDTO;
import gui.model.dto.MainGroupResponseDTO;
import gui.util.LocalDateAdapterUtil;
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

public class MainGroupRepository {

    public static final String MAIN_ENDPOINT = "http://" + AppProperties.getHost() + "/api/getAllByConversion11690/idMainGroup";
    public static ObjectMapper mapper = new ObjectMapper();
    public static final String MAIN_ID_ENDPOINT = "http://" + AppProperties.getHost() + "/api/getAllIdGroup";

    public static MainGroupResponseDTO addIdMain(MainGroupRequestDTO mainGroupRequestDTO) {
        String url = "http://" + AppProperties.getHost() + "/api/addIdGroup";
        return getResponseEntity(url, mainGroupRequestDTO);
    }

    public static List<MainGroup> findByIdGroup(Long idGroup) {
        String url = "http://" + AppProperties.getHost() + "/api/getIdGroupByConversion11690/" + idGroup;
        return getMainGroup(url);
    }

    public static List<MainGroup> findAllByIdConversion() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(MAIN_ENDPOINT);
            mapper.registerModule(new JavaTimeModule());
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<MainGroup>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<MainGroup> getAllIdGroup() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(MAIN_ID_ENDPOINT);
            mapper.registerModule(new JavaTimeModule());
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<MainGroup>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static MainGroupResponseDTO getResponseEntity(String url, MainGroupRequestDTO mainGroupRequestDTO) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapterUtil())
                    .create();
            mapper.registerModule(new JavaTimeModule());
//            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            httpPost.setEntity(new StringEntity(gson.toJson(mainGroupRequestDTO), StandardCharsets.UTF_8));
            return client.execute(httpPost, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), MainGroupResponseDTO.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<MainGroup> getMainGroup(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<MainGroup>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}

