package models;

import play.data.validation.Constraints;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * User entity
 */
public class EmailTemplate {
    public Long id;

    public Conference conference;

    public String title;

    public String content;

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

