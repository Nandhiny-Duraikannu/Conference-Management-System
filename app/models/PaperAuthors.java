package models;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

import com.avaje.ebean.PagedList;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;


/**
 * PaperSubmission-Authors relationship entity managed by Ebean
 */
@Entity
public class PaperAuthors extends com.avaje.ebean.Model {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    public Paper paper;

    @ManyToOne
    public String  user_id;

    @Constraints.Required
    public String type;

    public String author_first_name;

    public String author_last_name;

    public String author_affiliation;

    public String author_email;

    /**
     * Generic query helper for entity User with id Long
     */
    public static Find<Long, PaperAuthors> find = new Find<Long, PaperAuthors>() {
    };

    public static Map<String, String> getType() {
        Map<String, String> type = new HashMap<String, String>();
        type.put("1", "Main");
        type.put("2", "Other");

        return type;
    }

}