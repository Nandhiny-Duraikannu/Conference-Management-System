package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * User entity managed by Ebean
 */
@Entity
public class EmailTemplate extends com.avaje.ebean.Model {

    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @NotNull
    @ManyToMany
    public Conference conference;

    @NotNull
    public String title;

    @NotNull
    public String content;


    /**
     * Generic query helper for entity User with id Long
     */
    public static Find<Long, EmailTemplate> find = new Find<Long, EmailTemplate>() {
    };

    public static List<EmailTemplate> getByConfId(Long id) {
        return find.where().eq("conference_id", id).findList();
    }

    public static EmailTemplate getById(Long id) {
        return find.where().eq("id", id).findUnique();
    }

    public static EmailTemplate getByName(String name) {
        return find.where().eq("name", name).findUnique();
    }

    public static EmailTemplate getByNameAndConf(String name, Long conf_id) {
        return find.where().eq("name", name).findUnique();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static Map<String, String> getTopics() {
        Map<String, String> topics = new HashMap<String, String>();
        topics.put("Template for Accepted Papers", "Template for Accepted Papers");
        topics.put("Template for Rejected Papers", "Template for Rejected Papers");
        topics.put("Template for Moved Papers", "Template for Moved Papers");
        topics.put("Template for Instructions", "Template for Instructions");
        topics.put("Template for Invitations", "Template for Invitations");
        topics.put("Reviewer Reminder Template", "Reviewer Reminder Template");
        return topics;
    }
}

