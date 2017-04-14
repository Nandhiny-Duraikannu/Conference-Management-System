package lib;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import models.User;
import org.apache.http.client.utils.URLEncodedUtils;

import javax.xml.ws.Response;
import java.io.IOException;
import java.net.URLEncoder;
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

    /**
     *  Get security question of a user
     */
    public String getSecurityQuestion(String username) {
        try {
            HttpResponse<String> response = Unirest.get(getUrl("resetpassword?name=" + username)).asObject(String.class);

            if(response.getStatus() == 200 || response.getStatus() == 201) {
                return response.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  Set new password
     */
    public Boolean setNewPassword(String username, String securityAnswer) {
        try {
            User thisUser = this.getInstance().getUserByName(username);
            String securityQuestion = thisUser.getSecurityQuestion();

            HttpResponse<JsonNode> response = Unirest.post(getUrl("resetpassword"))
                    .field("name", username)
                    .field("securityQuestion", securityQuestion)
                    .field("securityAnswer", securityAnswer)
                    .asJson();

            if(response.getStatus() == 201 || response.getStatus() == 200) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  Update profile of a user
     */
    public Boolean updateProfile(Map<String, String> inputForm) {
        try {
            HttpRequestWithBody req = Unirest.post(getUrl("profile")).header("content-type", "application/x-www-form-urlencoded");
            req.body(mapToQueryString(inputForm));
            HttpResponse<JsonNode> response = req.asJson();

            if(response.getStatus() == 200 || response.getStatus() == 201) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}