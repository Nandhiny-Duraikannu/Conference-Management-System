package json;

import java.util.ArrayList;

/**
 * Reviewer and papers he/she need to review for a conference
 */
public class ConferenceReviewer {

    public String reviewerName;
    public String reviewerEmail;
    public Long reviewerId;
    /**
     * Papers that need to be reviewed
     */
    public ArrayList<Paper> notReviewedPapers = new ArrayList<Paper>();
    /**
     * Papers already reviewed
     */
    public ArrayList<Paper> reviewedPapers = new ArrayList<Paper>();
}
