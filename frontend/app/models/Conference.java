package models;

import lib.Api;
import lib.UserStorage;

import java.util.*;


/**
 * Conference entity
 */
public class Conference {

    private static final long serialVersionUID = 1L;

    public Long id;
    public String acronym;
    public String title;
    public String location;
    public Date deadline;
    public String status;

    /**
     * list of all conferences
     */
    // Should move to backend if not already.
    public static List<Conference> getAllConferences() {
        User user = UserStorage.getCurrentUser();
        List<Conference> conferences = new ArrayList<Conference>(Arrays.asList(
                Api.getInstance().getConferences(null)
        ));
        return null;
    }

    /**
     * conferences for which user submitted papers
     */
    // Should move to backend if not already.
    public static List<Conference> getConferencesByUser() {
        User user = UserStorage.getCurrentUser();
        List<Conference> conferences = new ArrayList<Conference>(Arrays.asList(
                Api.getInstance().getConferences(user.id)
        ));
        return conferences;
    }
}