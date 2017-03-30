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
import javax.validation.Constraint;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Paper-Authors relationship entity managed by Ebean
 */
@Entity
public class PaperAuthors extends com.avaje.ebean.Model {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public PaperAuthorsPK pk;

    @ManyToOne
    private Paper paper;

    @ManyToOne
    private User author;

    @NotNull
    public String type;

    public PaperAuthors() {
    }

    /*public PaperAuthorsPK getPrimaryKey() {
        return this.pk;
    }

    public void setPrimaryKey(PaperAuthorsPK pk) {
        this.pk = pk;
    }
*/
    public static Find<Long, PaperAuthors> find = new Find<Long, PaperAuthors>() {
    };
}


