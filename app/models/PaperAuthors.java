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
 * Paper-Authors relationship entity managed by Ebean
 */
@Entity
public class PaperAuthors extends com.avaje.ebean.Model {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    public Paper paper;

    @ManyToOne
    public User  author;

    @Constraints.Required
    public String type;

    /**
     * Generic query helper for entity User with id Long
     */
    public static Find<Long, PaperAuthors> find = new Find<Long, PaperAuthors>() {
    };

}