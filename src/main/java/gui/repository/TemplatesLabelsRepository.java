package gui.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gui.application.AppProperties;
import gui.model.TemplatesLabels;
import gui.model.dto.TemplateLabelDTO;
import gui.service.LocalDateAdapterUtil;
import org.apache.http.client.methods.HttpDelete;
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

public class TemplatesLabelsRepository {
    public static final String TEMPLATES_ENDPOINT = "http://" + AppProperties.getHost() + "/api/templates/all";
    public static ObjectMapper mapper = new ObjectMapper();

    public static TemplatesLabels deleteByIdTemplate(Long idTemplate){
        String url = "http://" + AppProperties.getHost() + "/api/templates/" + idTemplate;
        return delete(url);
    }

    public static TemplatesLabels delete(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpDelete httpDelete = new HttpDelete(url);
            return client.execute(httpDelete, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), TemplatesLabels.class));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<TemplatesLabels> findByIdCode(Long idCode) {
        String url = "http://" + AppProperties.getHost() + "/api/templates/" + idCode;
        return getTemplate(url);
    }

    public static TemplatesLabels saveAndFlush(TemplatesLabels templatesLabels) {
        String url = "http://" + AppProperties.getHost() + "/api/templates";
        return getResponseEntity(url, templatesLabels);
    }


    public static List<TemplateLabelDTO> getAllTemplates(String templatesEndpoint) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(TEMPLATES_ENDPOINT);
            mapper.registerModule(new JavaTimeModule());
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<TemplateLabelDTO>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    public static List<TemplatesLabels> getTemplate(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            mapper.registerModule(new JavaTimeModule());//для нужного формата даты из JSON'a
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<TemplatesLabels>>() {
                            }));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static TemplatesLabels getResponseEntity(String url, TemplatesLabels templatesLabels) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapterUtil())
                    .create();
            mapper.registerModule(new JavaTimeModule());
            httpPost.setEntity(new StringEntity(gson.toJson(templatesLabels), StandardCharsets.UTF_8));
            return client.execute(httpPost, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(), TemplatesLabels.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
