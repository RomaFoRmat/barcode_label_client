package gui.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gui.application.AppProperties;
import gui.model.ForeignGroup;
import gui.model.dto.ForeignGroupRequestDTO;
import gui.model.dto.ForeignGroupResponseDTO;
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

public class ForeignGroupRepository {

    public static final String FOREIGN_ENDPOINT = AppProperties.getHost() + "/api/getAllByIdForeignGroup";
    public static ObjectMapper mapper = new ObjectMapper();

    public static ForeignGroupResponseDTO addIdForeign(ForeignGroupRequestDTO foreignGroupRequestDTO) {
        String url = AppProperties.getHost() + "/api/create/foreignGroup";
        return getResponseEntity(url, foreignGroupRequestDTO);
    }

    public static List<ForeignGroup> findByMainGroupIdConversionAndIdForeignGroup(Long idForeignGroup) {
        String url = AppProperties.getHost() + "/api/getByIdForeignGroup/" + idForeignGroup;
        return getForeignGroup(url);
    }

    public static ForeignGroupResponseDTO getResponseEntity(String url, ForeignGroupRequestDTO foreignGroupRequestDTO) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapterUtil())
                    .create();
            mapper.registerModule(new JavaTimeModule());
            httpPost.setEntity(new StringEntity(gson.toJson(foreignGroupRequestDTO), StandardCharsets.UTF_8));
            return client.execute(httpPost, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), ForeignGroupResponseDTO.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<ForeignGroup> getForeignGroup(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<ForeignGroup>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<ForeignGroup> findAllByMainGroupIdConversionAndIdForeignGroup() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(FOREIGN_ENDPOINT);
            mapper.registerModule(new JavaTimeModule());
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<ForeignGroup>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


}
