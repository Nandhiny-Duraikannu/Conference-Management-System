package json;

import com.avaje.ebeaninternal.server.lib.util.Str;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Conferences where user is assigned to review papers
 */
public class PaperConferenceReviews {
    public Long conferenceId;
    public Long paperId;
    public String paperTitle;
    public ArrayList<String> authors;
    public ArrayList<String> reviewers;
    public String status;
}
