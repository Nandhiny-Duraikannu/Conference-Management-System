package json;

import java.util.ArrayList;

/**
 * Reviewer and papers he/she need to review for a conference
 */
public class ConferenceReviewer {
    class Paper {
        public Long id;
        public String title;
    }

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

    public void addNotReviewedPaper(Long id, String title) {
        Paper p = new Paper();
        p.id = id;
        p.title = title;

        this.notReviewedPapers.add(p);
    }

    public void addReviewedPaper(Long id, String title) {
        Paper p = new Paper();
        p.id = id;
        p.title = title;

        this.reviewedPapers.add(p);
    }
}
