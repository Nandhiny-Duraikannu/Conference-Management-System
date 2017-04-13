package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import json.UserConferenceReviews;

import javax.persistence.Entity;
import javax.persistence.Id;
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

    public String status;

    @JsonManagedReference
    @OneToMany
    public List<Paper> papers;

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

            if (!result.containsKey(conf.id)) {
                UserConferenceReviews item = new UserConferenceReviews();
                item.conferenceId = conf.id;
                item.conferenceTitle = conf.title;
                item.assignedPapersNumber = 0;
                item.reviewedPapersNumber = 0;

                result.put(conf.id, item);
            }

            UserConferenceReviews item = result.get(conf.id);

            if (review.isReviewed()) {
                item.reviewedPapersNumber++;
            }

            item.assignedPapersNumber++;
        }

        return new ArrayList<>(result.values());
    }
}