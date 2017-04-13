package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

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
}