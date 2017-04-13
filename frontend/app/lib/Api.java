package lib;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import models.Conference;
import models.Paper;
import models.PaperAuthors;
import models.User;
import org.apache.http.client.utils.URLEncodedUtils;

import javax.xml.ws.Response;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Conference system API calls
 */
public class Api {
    protected String baseUrl = "http://localhost:9000/api";

    protected static Api instance;

    public Api() {
        // makes Unirest library able to convert JSON to objects
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }

        return instance;
    }

    /**
     * Returns user by name from API
     *
     * @param name
     * @return
     */
    public User getUserByName(String name) {
        try {
            HttpResponse<User> response = Unirest.get(getUrl("users/" + name)).asObject(User.class);
            return response.getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Creates new user in API
     */
    public boolean createUser(Map<String, String> data) {
        try {
            HttpRequestWithBody req = Unirest.post(getUrl("users")).header("content-type",
                                                                           "application/x-www-form-urlencoded");
            req.body(mapToQueryString(data));
            HttpResponse<JsonNode> response = req.asJson();

            return response.getStatus() >= 200 && response.getStatus() < 400;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Paper[] getByAuthorAndConference(Long user_id, int conf_id){
        try {
            HttpResponse<Paper[]> response = Unirest.get(getUrl("papers/" + user_id + "/conf/" + conf_id)).asObject(Paper[].class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getAuthors(Long paper_id){
        try {
            HttpResponse<String[]> response = Unirest.get(getUrl("papers/authors/" + paper_id )).asObject(String[].class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Conference[] getConferences(Long user_id){
        if ( user_id != null || user_id != 0){
            //list of conferences for user id
            try {
                HttpResponse<Conference[]> response = Unirest.get(getUrl("conferences/users/" + user_id)).asObject(Conference[].class);
                return response.getBody();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        else{
            //all conferences
            try {
                HttpResponse<Conference[]> response = Unirest.get(getUrl("conferences")).asObject(Conference[].class);
                return response.getBody();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public boolean uploadPaper(Map<String, String> data) {
        try {
            HttpRequestWithBody req = Unirest.post(getUrl("/papers/upload")).header("content-type",
                    "application/x-www-form-urlencoded");
            req.body(mapToQueryString(data));
            HttpResponse<JsonNode> response = req.asJson();

            return response.getStatus() >= 200 && response.getStatus() < 400;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected String getUrl(String url) {
        return baseUrl + "/" + url;
    }

    /**
     * Converts map of strings to a string "key1=value1&key2=value2..."
     *
     * @param queryString
     * @return
     */
    protected String mapToQueryString(Map<String, String> queryString) {
        StringBuilder sb = new StringBuilder();

        try {
            for (Map.Entry<String, String> e : queryString.entrySet()) {
                if (sb.length() > 0) {
                    sb.append('&');
                }
                sb.append(URLEncoder.encode(e.getKey(), "UTF-8")).append('=').append(URLEncoder.encode(e.getValue(),
                                                                                                       "UTF-8"));
            }
        } catch (Exception e) {

        }

        return sb.toString();
    }
}