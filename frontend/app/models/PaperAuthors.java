package models;

import java.util.*;

public class PaperAuthors {

    public Long id;

    public Paper paper;

    public String type;

    public String author_first_name;

    public String author_last_name;

    public String author_affiliation;

    public String author_email;

    public static Map<String, String> getType() {
        Map<String, String> type = new HashMap<String, String>();
        type.put("1", "Main");
        type.put("2", "Other");

        return type;
    }
}


