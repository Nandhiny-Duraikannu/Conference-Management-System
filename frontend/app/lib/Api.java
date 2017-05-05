package lib;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.options.Option;
import com.mashape.unirest.http.options.Options;
import com.mashape.unirest.request.body.MultipartBody;
import com.mashape.unirest.request.body.RawBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import com.mashape.unirest.request.HttpRequestWithBody;
import json.ConferenceReviewer;
import json.UserConferenceReviews;
import models.*;

import javax.xml.ws.Response;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.ByteArrayOutputStream;

import play.Logger;
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

    public ArrayList<User> getAllUsers() {
        try {
            HttpResponse<User[]> response = Unirest.get(getUrl("users")).asObject(User[].class);
            return new ArrayList<User>(Arrays.asList(response.getBody()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Returns conferences for which given user has papers to review
     *
     * @param userId
     * @return
     */
    public ArrayList<UserConferenceReviews> getConferencesWithAssignedReviewer(Long userId) {
        try {
            HttpResponse<UserConferenceReviews[]> response = Unirest.get(getUrl("conferences/reviewers/assigned/" + userId)).asObject(
                    UserConferenceReviews[].class);
            return new ArrayList<UserConferenceReviews>(Arrays.asList(response.getBody()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Returns reviewers and papers they need to review for a conference
     *
     * @param confId
     * @return
     */
    public ArrayList<ConferenceReviewer> getConferenceReviewers(Long confId) {
        try {
            HttpResponse<ConferenceReviewer[]> response = Unirest.get(getUrl("conferences/" + confId + "/reviewers")).asObject(
                    ConferenceReviewer[].class);
            return new ArrayList<ConferenceReviewer>(Arrays.asList(response.getBody()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Send notifications to those reviewers who have papers to review
     * Return emails where notifications were sent
     *
     * @param confId
     * @return
     */
    public ArrayList<String> notifyReviewers(Long confId) {
        try {
            HttpResponse<String[]> response = Unirest.post(getUrl("conferences/" + confId + "/notifications")).asObject(
                    String[].class);
            return new ArrayList<String>(Arrays.asList(response.getBody()));
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

    public boolean InsertPaper(Map<String, String> data) {
        try {
            HttpRequestWithBody req = Unirest.post(getUrl("paper")).header("content-type",
                                                                           "application/x-www-form-urlencoded");
            String query = mapToQueryString(data);
            System.out.println(query);
            req.body(query);

            HttpResponse<JsonNode> response = req.asJson();

            return response.getStatus() >= 200 && response.getStatus() < 400;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdatePaper(Long id, Map<String, String> data) {
        try {
            HttpRequestWithBody req = Unirest.post(getUrl("paper/" + id)).header("content-type",
                                                                                 "application/x-www-form-urlencoded");
            String query = mapToQueryString(data);
            System.out.println(query);
            req.body(query);

            HttpResponse<JsonNode> response = req.asJson();

            return response.getStatus() >= 200 && response.getStatus() < 400;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Paper[] getByAuthorAndConference(Long user_id, int conf_id) {
        try {
            HttpResponse<Paper[]> response = Unirest.get(getUrl("papers/" + user_id + "/conf/" + conf_id)).asObject(
                    Paper[].class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Paper getPaperById(Long id) {
        try {
            HttpResponse<Paper> response = Unirest.get(getUrl("papers/" + id)).asObject(Paper.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getAuthors(Long paper_id) {
        try {
            HttpResponse<String[]> response = Unirest.get(getUrl("papers/authors/" + paper_id)).asObject(String[].class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // TODO: Just returning all conferences right now.
    // Something was breaking here passing a null into this function.
    // Will create a duplicate for now, and refactor during the weekend. God bless!
    public Conference[] getConferencesAll(Long user_id) {
//        if ( user_id != null || user_id != 0){
//            //list of conferences for user id
//            try {
//                HttpResponse<Conference[]> response = Unirest.get(getUrl("conferences/users/" + user_id)).asObject(Conference[].class);
//                return response.getBody();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//        else{
        //all conferences

        try {
            HttpResponse<Conference[]> response = Unirest.get(getUrl("conferences")).asObject(Conference[].class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        }
    }

    public Conference[] getConferences(Long user_id) {
        if (user_id != null) {
            //list of conferences for user id
            try {
                HttpResponse<Conference[]> response = Unirest.get(getUrl("conferences/user/" + user_id)).asObject(
                        Conference[].class);
                return response.getBody();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public Conference getConferenceById(Long id) {
        try {
            HttpResponse<Conference> response = Unirest.get(getUrl("conferences/" + id)).asObject(Conference.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // TODO: Maybe combine this with getConferences()
    public Conference[] getConferencesKeyword(Long user_id, String keyword, String conf_status) {
//        if ( user_id != null || user_id != 0){
//            //list of conferences for user id
//            try {
//                HttpResponse<Conference[]> response = Unirest.get(getUrl("conferences/users/" + user_id)).asObject(Conference[].class);
//                return response.getBody();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//        else{
        //all conferences
        try {
            HttpResponse<Conference[]> response = Unirest.get(getUrl("conferencessearch?keyword=" + keyword + "&conf_status=" + conf_status)).asObject(
                    Conference[].class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        }
    }

    public Conference editOrCreateConference(Conference conf) {
        try {
            String url = "conferences";

            if (conf.id != null && conf.id > 0) {
                url += "/" + conf.id;
            }
            HttpRequestWithBody req = Unirest.post(getUrl(url)).header("content-type",
                                                                       "application/x-www-form-urlencoded");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            HashMap<String, String> params = new HashMap<>();

            params.put("title", conf.title);
            params.put("acronym", conf.acronym);
            params.put("location", conf.location);
            params.put("status", conf.status != null ? conf.status : "active");
            params.put("deadline", df.format(conf.deadline));
            params.put("submissionDateStart", df.format(conf.submissionDateStart));

            req.body(mapToQueryString(params));

            HttpResponse<Conference> response = req.asObject(Conference.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean setConferenceLogo(Long id, File file) {
        try {
            HttpResponse<String> response = Unirest.post(getUrl("conferences/" + id))
                                                   .field("logo", file)
                                                   .asString();
            return response.getStatus() >= 200 && response.getStatus() < 400;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean uploadPaper(Long paper_id, File file, String format) {
        try {
            HttpResponse<String> response = Unirest.post(getUrl("papers/upload/" + paper_id))
                                                   .field("format", format)
                                                   .field("file", file)
                                                   .asString();
            return response.getStatus() >= 200 && response.getStatus() < 400;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns review by id
     *
     * @param id
     * @return
     */
    public Review getReview(Long id) {
        try {
            HttpResponse<Review> response = Unirest.get(getUrl("reviews/" + id)).asObject(Review.class);
            return response.getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Review> getReviewsByUserAndConference(Long userId, Long confId) {
        try {
            HttpResponse<Review[]> response = Unirest.get(getUrl("reviews/user/" + userId + "/conference/" + confId)).asObject(
                    Review[].class);
            return new ArrayList<Review>(Arrays.asList(response.getBody()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean editReview(Long review_id, String content) {
        try {
            HttpRequestWithBody req = Unirest.post(getUrl("reviews/" + review_id)).header("content-type",
                                                                                          "application/x-www-form-urlencoded");
            HashMap<String, String> params = new HashMap<>();
            params.put("content", content);
            req.body(mapToQueryString(params));
            HttpResponse<JsonNode> response = req.asJson();

            return response.getStatus() >= 200 && response.getStatus() < 400;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Review[] getReviewPapersByConf(Long user_id, Long conf_id) {
        try {
            HttpResponse<Review[]> response = Unirest.get(getUrl("reviews/papers/" + user_id + "/conference/" + conf_id)).asObject(
                    Review[].class);
            return response.getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public EmailTemplate[] getConferenceTemplates(Long conf_id) {
        try {
            HttpResponse<EmailTemplate[]> response = Unirest.get(getUrl("conferences/templates/" + conf_id)).asObject(
                    EmailTemplate[].class);
            return response.getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
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
     * Get security question of a user
     */
    public String getSecurityQuestion(String username) {
        try {
            HttpResponse<String> response = Unirest.get(getUrl("resetpassword?name=" + username)).asObject(String.class);

            if (response.getStatus() == 200 || response.getStatus() == 201) {
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
     * Set new password
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

            if (response.getStatus() == 201 || response.getStatus() == 200) {
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
     * Update profile of a user
     */
    public Boolean updateProfile(Map<String, String> inputForm) {
        try {
            HttpRequestWithBody req = Unirest.post(getUrl("profile")).header("content-type",
                                                                             "application/x-www-form-urlencoded");
            req.body(mapToQueryString(inputForm));
            HttpResponse<JsonNode> response = req.asJson();

            if (response.getStatus() == 200 || response.getStatus() == 201) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public PCMember[] getPCMembersByConfId(Long conf_id) {
        try {
            HttpResponse<PCMember[]> response = Unirest.get(getUrl("conferences/pcmembers/" + conf_id)).asObject(
                    PCMember[].class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addPCMember(Long conf_id, Long id, String role) {
        try {
            HttpRequestWithBody req = Unirest.post(getUrl("conferences/pcmembers/" + conf_id)).header("content-type",
                                                                                                      "application/x-www-form-urlencoded");
            HashMap<String, String> params = new HashMap<>();
            params.put("id", id.toString());
            params.put("role", role);
            req.body(mapToQueryString(params));

            HttpResponse<String> response = req.asString();
            System.out.println(response.getBody());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveTemplate(Long conf_id, Long id, String content) {
        try {
            HttpRequestWithBody req = Unirest.post(getUrl("conferences/templates/" + conf_id + "/" + id)).header(
                    "content-type",
                    "application/x-www-form-urlencoded");
            HashMap<String, String> params = new HashMap<>();
            params.put("content", content);
            req.body(mapToQueryString(params));

            HttpResponse<String> response = req.asString();
            System.out.println(response.getBody());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public ResearchPaper[] getResearchTopic() {
        try {
            HttpResponse<ResearchPaper[]> response = Unirest.get(getUrl("getalltopics")).asObject(ResearchPaper[].class);
            Logger.debug("in api get all topics"+response.getBody());
            return response.getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Logger.debug("in api get all topicserror");
            return null;

        }
    }
    
    
    public boolean saveResearchTopic(String research_topic) {
        try {
            Logger.debug("in api researchtopic1"+research_topic);
            HttpResponse<String> response = Unirest.post(getUrl("researchTopic"))
                    .field("research_topic",research_topic)
                    .asString();
            return true;
        } catch (Exception e) {
            Logger.debug("in api researchtopic2"+research_topic);
            System.out.println(e.getMessage());
        return false;
        }
    }


    public boolean deletePCMembers(Long id) {
        try {
            HttpResponse<PCMember> response = Unirest.post(getUrl("conferences/pcmembers/delete/" + id)).asObject(
                    PCMember.class);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
