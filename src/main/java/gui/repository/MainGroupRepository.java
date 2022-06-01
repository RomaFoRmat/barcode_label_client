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
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class MainGroupRepository {

    public static final String MAIN_ENDPOINT = AppProperties.getHost() + "/api/getAllByConversion11690/idMainGroup";
    public static ObjectMapper mapper = new ObjectMapper();
    public static final String MAIN_ID_WEEK = AppProperties.getHost() + "/api/getAllIdGroup-forTheWeek";
    public static final String MAIN_ID_ENDPOINT = AppProperties.getHost() + "/api/getAllIdGroup-forTheMonth";
    public static final String MAIN_ID_MONTHS = AppProperties.getHost() + "/api/getAllIdGroup-forSixMonths";
    public static final String MAIN_ID_YEAR = AppProperties.getHost() + "/api/getAllIdGroup-forTheYear";

    public static MainGroupResponseDTO addIdMain(MainGroupRequestDTO mainGroupRequestDTO) {
        String url = AppProperties.getHost() + "/api/addIdGroup";
        return getResponseEntity(url, mainGroupRequestDTO);
    }

    public static List<MainGroup> findByIdGroup(Long idGroup) {
        String url = AppProperties.getHost() + "/api/getIdGroupByConversion11690/" + idGroup;
        return getMainGroup(url);
    }
    public static List<MainGroup> findAllByDateCreateBetween(LocalDateTime dateStart, LocalDateTime dateEnd) {
        String url = AppProperties.getHost() + "/api/getAllIdGroupBetween/" + dateStart + "/" + dateEnd;
        return getAllIdGroupBetween(url);
    }

    private static List<MainGroup> getAllIdGroupBetween(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
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

    public static List<MainGroup> getAllIdGroupWeek() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(MAIN_ID_WEEK);
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

    public static List<MainGroup> getAllIdGroupMonth() {
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

    public static List<MainGroup> getAllIdGroupSixMonths() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(MAIN_ID_MONTHS);
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

    public static List<MainGroup> getAllIdGroupYear() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(MAIN_ID_YEAR);
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

