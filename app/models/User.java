package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.PagedList;
import play.db.ebean.*;
import play.data.validation.*;


/**
 * User entity managed by Ebean
 */
@Entity
public class User extends com.avaje.ebean.Model {

    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    /**
     * Generic query helper for entity User with id Long
     */
    public static Find<Long, User> find = new Find<Long, User>() {
    };

    /**
     * Return a paged list of user
     *
     * @param page     Page to display
     * @param pageSize Number of users per page
     * @param sortBy   user property used for sorting
     * @param order    Sort order (either or asc or desc)
     * @param filter   Filter applied on the name column
     */
    public static PagedList<User> page(int page, int pageSize, String sortBy, String order, String filter) {
        return
                find.where()
                    .ilike("name", "%" + filter + "%")
                    .orderBy(sortBy + " " + order)
                    .findPagedList(page, pageSize);
    }
}

