package models;

import com.avaje.ebean.*;
import play.data.validation.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;


/**
 * Paper entity managed by Ebean
 */
@Entity
public class Paper extends com.avaje.ebean.Model {

    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @NotNull
    @ManyToOne
    public User user;

    @NotNull
    public String title;

    @NotNull
    public String topic;

    @NotNull
    @Constraints.Email
    public String contactEmail;

    @NotNull
    public String awardCandidate;

    @NotNull
    public String studentVolunteer;

    public String status;

    @NotNull
    @Column(length = 5000)
    public String paperAbstract;

    @ManyToOne
    public Conference conference;

    public String fileFormat;

    public String fileSize;

    public String submissionDate;

    /**
     * Generic query helper for entity Paper with id Long
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
        return find.where()
                   .ilike("title", "%" + filter + "%")
                   .orderBy(sortBy + " " + order)
                   .findPagedList(page, pageSize);
    }

    public static List<Paper> getByAuthorAndConference(Long author_id, int conference_id){
        ExpressionList<Paper> query = Paper.find.select("*")
                                           .where().eq("user_id", author_id);
        if (conference_id !=0){
            query = query.eq("conference.id", conference_id);
        }
        List<Paper> papers = query.findList();
        return papers;
    }

    public static List<Paper> getByAuthor(Long user_id){
        return find.select("*")
                .where().eq("user_id", user_id)
                .findList();
    }

    public static ArrayList<String> getAuthors(Long paper_id){
        List<PaperAuthors> items = PaperAuthors.
                find.select("*")
                .where().eq("paper_id", paper_id)
                .findList();
        ArrayList<String> authors = new ArrayList<String>();
        for(int i=0; i<items.size(); i++)
        {
            authors.add(items.get(i).author_first_name + " " + items.get(i).author_last_name);
        }
        return authors;
    }

    public static List<Paper> getAllPapers(){
        return find.findList();
    }

    public static List<Paper> getByTitle(String title) {
        return find.where().eq("title", title).findList();
    }

    public static Paper getById(Long id) {
        return find.where().eq("id", id).findUnique();
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