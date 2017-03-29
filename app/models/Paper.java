package models;

import com.avaje.ebean.PagedList;
import play.Logger;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Paper entity managed by Ebean
 */
@Entity
public class Paper extends com.avaje.ebean.Model {

    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @Constraints.Required
    public String title;

    @Constraints.Required
    public String topic;

    @Constraints.Required
    @Constraints.Email
    public String contactEmail;

    @Constraints.Required
    public String awardCandidate;

    @Constraints.Required
    public String studentVolunteer;

    public String status;

    @Constraints.Required
    @Column(length = 5000)
    public String paperAbstract;

    @Constraints.Required
    public String conferenceID;

    public String fileFormat;

    public String fileSize;

    public String submissionDate;

    /**
     * Generic query helper for entity User with id Long
     */
    public static Find<Long, Paper> find = new Find<Long, Paper>() {
    };

    /**
     * Return a paged list of papers
     *
     * @param page     page to display
     * @param pageSize number of papers per page
     * @param sortBy   property used for sorting
     * @param order    sort order (either or asc or desc)
     * @param filter   filter applied on the name column
     */
    public static PagedList<Paper> page(int page, int pageSize, String sortBy, String order, String filter) {
        return
                find.where()
                        .ilike("title", "%" + filter + "%")
                        .orderBy(sortBy + " " + order)
                        .findPagedList(page, pageSize);
    }

    public static List<Paper> getByTitle(String title) {
        return find.where().eq("title", title).findList();
    }

    public static List<String> getIsAwardCandidate() {
        List<String> isAwardCandidate = new ArrayList<String>();
        isAwardCandidate.add("Yes");
        isAwardCandidate.add("No");
        return isAwardCandidate;
    }

    public static List<String> getIsStudentVolunteer() {
        List<String> isStudentVolunteer = new ArrayList<String>();
        isStudentVolunteer.add("Yes");
        isStudentVolunteer.add("No");
        isStudentVolunteer.add("To be decided");
        return isStudentVolunteer;
    }
}