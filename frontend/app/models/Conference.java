package models;

import java.util.*;


/**
 * Conference entity
 */
public class Conference {
    public Long id;
    public String acronym;
    public String title;
    public String location;
    public Date deadline;

    /**
     * list of all conferences
     */
    public static List<Conference> getAllConferences() {
        return null;
        /*List<Conference> items = Conference.
                find.select("*")
                .findList();
        return items;*/
    }

    /**
     * conferences for which user submitted papers
     */
    public static List<Conference> getConferencesByUser(Long userId) {
        return null;
    }
}