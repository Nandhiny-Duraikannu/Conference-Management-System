package models;

import com.avaje.ebean.*;
import com.avaje.ebean.config.JsonConfig;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import play.data.validation.*;
import play.Logger;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;



/**
 * Created by nandh on 4/27/2017.
 */
@Entity
public class ResearchPaper extends com.avaje.ebean.Model {

    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @NotNull
    public String research_topic;

    public void saveResearchTopic(String research_topic) {
        Logger.debug("models researchtopic");
        this.research_topic = research_topic;
        this.save();
    }
    public static List<ResearchPaper> getResearchTopic() {
        List<ResearchPaper> items = ResearchPaper.
                find.select("*")
                .findList();
        return items;
    }
    public static Find<String, ResearchPaper> find = new Find<String, ResearchPaper>() {
    };


}
