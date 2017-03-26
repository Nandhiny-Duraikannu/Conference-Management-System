package models;

import javax.persistence.*;

import com.avaje.ebean.PagedList;
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

    @Constraints.Required
    public String password;

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

    public static User getByName(String name) {
        return find.where().eq("name", name).findUnique();
    }

    public static User getByNameAndPassword(String name, String password) {
        try {
            User user = getByName(name);
            if (user == null) {
                throw new IllegalArgumentException("User with login '" + name + "' was not found");
            }

            //password = PasswordCreator.sha1Password(password, user.getSalt());

            if (!password.equals(user.password)) {
                throw new IllegalArgumentException("Password for user '" + name + "' doesn't match");
            }

            return user;
        } catch (Exception e) {
            //Logger.error("An error occurred on getting user by login and password. Login used: "+login, e);
        }
        return null;
    }
}

