package models;

import java.util.*;

/**
 * Paper-Authors relationship entity managed by Ebean
 */
public class PaperAuthors {

    public static Map<String, String> getType() {
        Map<String, String> type = new HashMap<String, String>();
        type.put("1", "Main");
        type.put("2", "Other");

        return type;
    }
}


