package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import json.ConferenceReviewer;
import json.PaperConferenceReviews;
import json.UserConferenceReviews;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.*;


/**
 * Conference entity managed by Ebean
 */
@Entity
public class Conference extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @NotNull
    public String acronym;

    public String title;

    public String location;

    public Date deadline;

    public Date submissionDateStart;

    public String logo;

    public String status;

    /**
     * Generic query helper for entity Conference with id Long
     */
    public static Find<Long, Conference> find = new Find<Long, Conference>() {
    };

    /**
     * list of all conferences
     */
    public static List<Conference> getAllConferences() {
        List<Conference> items = Conference.
                find.select("*")
                    .findList();
        return items;
    }

    /**
     * list of all conferences by keyword
     * TODO: Make this work with getAllConferences()
     */
    public static List<Conference> getAllConferencesByKeyword(String keyword, String conf_status) {
        if (conf_status.equals("all")) {
            conf_status = "%%";
        } else {
            conf_status = "%" + conf_status + "%";
        }
        if (keyword.equals("")) {
            keyword = "%%";
        } else {
            keyword = "%" + keyword + "%";
        }
        List<Conference> items = Conference.find.where().ilike("title", keyword).ilike("status",
                                                                                       conf_status).findList();
        return items;
    }

    /**
     * conferences for which user submitted papers
     */
    public static List<Conference> getConferencesByUser(Long userId) {
        List<Paper> items = Paper.
                find.select("*")
                    .where().eq("user_id", userId)
                    .findList();
        Set<Conference> conf = new HashSet<Conference>();
        for (int i = 0; i < items.size(); i++) {
            conf.add(items.get(i).conference);
        }
        return new ArrayList<>(conf);
    }

    /**
     * conferences for which user submitted papers
     */
    public static List<UserConferenceReviews> getUserConferenceReviews(Long userId) {
        HashMap<Long, UserConferenceReviews> result = new HashMap<>();

        List<Review> userReviews = Review.
                find.select("*")
                    .where().eq("user_id", userId)
                    .findList();

        for (int i = 0; i < userReviews.size(); i++) {
            Review review = userReviews.get(i);
            Conference conf = review.paper.conference;
            UserConferenceReviews item;

            if (!result.containsKey(conf.id)) {
                item = new UserConferenceReviews();
                item.conferenceId = conf.id;
                item.conferenceTitle = conf.title;
                item.assignedPapersNumber = 0;
                item.reviewedPapersNumber = 0;

                result.put(conf.id, item);
            } else {
                item = result.get(conf.id);
            }

            if (review.isReviewed()) {
                item.reviewedPapersNumber++;
            }

            item.assignedPapersNumber++;
        }

        return new ArrayList<>(result.values());
    }

    /**
     * Returns reviewers and papers they need to review for a conference
     */
    @JsonIgnore
    public List<ConferenceReviewer> getReviewers() {
        HashMap<Long, ConferenceReviewer> result = new HashMap<>();

        List<Review> reviews = Review.
                find.select("*")
                    .where().eq("paper.conference.id", this.id)
                    .findList();

        for (int i = 0; i < reviews.size(); i++) {
            Review review = reviews.get(i);
            Conference conf = review.paper.conference;
            User reviewer = User.find.byId(review.user.id);
            ConferenceReviewer item;

            if (!result.containsKey(reviewer.id)) {
                item = new ConferenceReviewer();

                item.reviewerId = reviewer.id;
                item.reviewerEmail = reviewer.email;
                item.reviewerName = reviewer.name;

                result.put(reviewer.id, item);
            } else {
                item = result.get(reviewer.id);
            }

            if (review.isReviewed()) {
                item.addReviewedPaper(review.paper.id, review.paper.title);
            } else {
                item.addNotReviewedPaper(review.paper.id, review.paper.title);
            }
        }

        return new ArrayList<>(result.values());
    }

    public static List<PaperConferenceReviews> getPapersConferenceReviews(Long confId, String status) {
        HashMap<Long, PaperConferenceReviews> result = new HashMap<>();
        List<Review> reviews;
        System.out.println(status);
        if (status.equals("all")){
            System.out.println("1");
            reviews = Review.
            find.select("*")
            .where().eq("paper.conference.id", confId)
            .findList();
        }
        else{
            reviews = Review.
                    find.select("*")
                    .where().eq("paper.conference.id", confId)
                    .eq("paper.status", status)
                    .findList();
        }

        for (int i = 0; i < reviews.size(); i++) {
            Review review = reviews.get(i);
            Conference conf = review.paper.conference;
            PaperConferenceReviews item;

            if (!result.containsKey(conf.id)) {
                item = new PaperConferenceReviews();
                item.conferenceId = conf.id;
                item.paperId = review.paper.id;
                item.paperTitle = review.paper.title;
                item.authors = new ArrayList<String>();
                item.reviewers = new ArrayList<String>();
                ArrayList<String> authors = Paper.getAuthors(review.paper.id);
                for(int n=0; n<authors.size(); n++){
                    item.authors.add(authors.get(n));
                }
                ArrayList<String> reviewers = Paper.getReviewers(review.paper.id);
                for(int n=0; n<reviewers.size(); n++){
                    item.reviewers.add(reviewers.get(n));
                }
                item.status = review.paper.status;

                result.put(conf.id, item);
            } else {
                item = result.get(conf.id);
            }
        }

        return new ArrayList<>(result.values());
    }
}