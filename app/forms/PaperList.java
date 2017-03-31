package forms;

import models.Conference;
import models.Paper;
import models.User;

import java.util.List;

/**
 * Paper List
 */
public class PaperList {
    private List<Paper> papers;

    public List<Paper> getPapers(Long author_id, int conference_id) {
        papers = Paper.getByAuthorAndConference(author_id, conference_id);
        return papers;
    }

}

