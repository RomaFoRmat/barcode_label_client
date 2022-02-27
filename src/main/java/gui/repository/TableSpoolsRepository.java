package gui.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import gui.application.AppProperties;
import gui.model.TableSpools;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


public class TableSpoolsRepository {

    public static ObjectMapper mapper = new ObjectMapper();

    public static final String SPOOLS_ENDPOINT = "http://" + AppProperties.getHost() + "/api/label/spool";
    public static final String SPOOLS_ENDPOINT_lAST_DAY = "http://" + AppProperties.getHost() + "/api/allSpool/forTheLastDay";

    public static List<TableSpools> getAllSpools() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(SPOOLS_ENDPOINT);
            mapper.registerModule(new JavaTimeModule());
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<TableSpools>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<TableSpools> getAllSpoolsForTheLastDay() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(SPOOLS_ENDPOINT_lAST_DAY);
            mapper.registerModule(new JavaTimeModule());
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<TableSpools>>() {
                            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<TableSpools> getAllSpoolsBetween(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            mapper.registerModule(new JavaTimeModule());//для нужного формата даты из JSON'a
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<TableSpools>>() {
                            }));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<TableSpools> findAllByDateCreateBetween(LocalDateTime dateCreateStart, LocalDateTime dateCreateEnd) {
        String url = "http://" + AppProperties.getHost() + "/api/allSpool/" + dateCreateStart + "/" + dateCreateEnd;
        return getAllSpoolsBetween(url);
    }


    public static List<TableSpools> findByNumberSpool(String numberSpool) {
        String url = "http://" + AppProperties.getHost() + "/api/label/spool/" + numberSpool;
        return getTestLabel(url);
    }

    public static List<TableSpools> getFirstValues(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            mapper.registerModule(new JavaTimeModule());//для нужного формата даты из JSON'a
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<TableSpools>>() {
                            }));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<TableSpools> findFirstValuesByRowNum(String rowNum) {
        String url = "http://" + AppProperties.getHost() + "/api/allSpool/" + rowNum;
        return getFirstValues(url);
    }

    public static List<TableSpools> getTestLabel(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            mapper.registerModule(new JavaTimeModule());//для нужного формата даты из JSON'a
            return client.execute(request, httpResponse ->
                    mapper.readValue(httpResponse.getEntity().getContent(),
                            new TypeReference<List<TableSpools>>() {
                            }));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
