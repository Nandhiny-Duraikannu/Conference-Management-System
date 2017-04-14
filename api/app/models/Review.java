package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Paper review entity managed by Ebean
 */
@Entity
public class Review extends Model {
    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @Constraints.Required
    @JsonBackReference
    @NotNull
    @ManyToOne
    public User user;

    @Constraints.Required
    @JsonBackReference
    @NotNull
    @ManyToOne
    public Paper paper;

    @Column(length = 10000)
    public String content;

    /**
     * Generic query helper for entity Review with id Long
     */
    public static Find<Long, Review> find = new Find<Long, Review>() {
    };

    /**
     * get review by id
     */
    public static Review getById(Long id) {
        return find.where().eq("id", id).findUnique();
    }

    /**
     * Get reviews by reviewer id
     *
     * @param userId
     * @return
     */
    public static List<Review> getByUser(Long userId) {
        return find.where().eq("user.id", userId).findList();
    }

    /**
     * Get reviews by reviewer id for conference id
     *
     * @param user_id
     * @param conf_id
     * @return
     */
    public static List<Paper> getPaperReviewsByUserAndConf(Long user_id, Long conf_id) {
        List<Review> items = find.where().eq("user.id", user_id).findList();
        List<Paper> papers = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            Paper paper = Paper.find.where().eq("id", items.get(i).getPaperId()).findUnique();
            if (paper.conference.id != conf_id)
            {
                items.remove(i);
                papers.add(paper);
            }
        }
        return papers;
    }

    public static List<Review> getReviewsByUserAndConf(Long user_id, Long conf_id) {
        return find.where().eq("user.id", user_id).eq("paper.conference.id", conf_id).findList();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    /**
     * Whether the review content was added
     *
     * @return
     */
    @JsonIgnore
    public boolean isReviewed() {
        return this.content != null && this.content.length() > 0;
    }

    public String getPaperTitle() {
        return paper.title;
    }

    public Long getPaperSize() {
        return paper.fileSize;
    }

    public String getPaperFormat() {
        return paper.fileFormat;
    }

    public Long getPaperId() {
        return paper.id;
    }

    public void setContent(String content) {
        if (content == null || content.length() == 0) {
            this.content = null;
        } else {
            this.content = content;
        }
    }
}