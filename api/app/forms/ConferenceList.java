package forms;

import models.Conference;
import java.util.List;


/**
 * Conference List
 */

public class ConferenceList {
    private List<Conference> conferences;

    public List<Conference> getConferences() {
        conferences = Conference.getAllConferences();
        return conferences;
    }
}
