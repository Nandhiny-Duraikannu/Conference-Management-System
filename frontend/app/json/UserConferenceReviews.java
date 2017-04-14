package json;

/**
 * Conferences where user is assigned to review papers
 */
public class UserConferenceReviews {
    public Long conferenceId;
    public String conferenceTitle;
    /**
     * How many papers need to be reviewed
     */
    public int assignedPapersNumber;
    /**
     * How many papers are already reviewed
     */
    public int reviewedPapersNumber;

    /**
     * How many papers need to be reviewed
     *
     * @return
     */
    public int getPapersLeft() {
        return assignedPapersNumber - reviewedPapersNumber;
    }
}
