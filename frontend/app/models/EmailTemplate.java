package models;

import play.data.validation.Constraints;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public static List<String> templatesList() {
        ArrayList<String> templates = new ArrayList<>();
        templates.add("Template for Accepted Papers");
        templates.add("Template for Rejected Papers");
        templates.add("Template for Moved Papers");
        templates.add("Template for Instructions");
        templates.add("Template for Invitations");
        templates.add("Reviewer Reminder Template");
        return templates;
    }
}

