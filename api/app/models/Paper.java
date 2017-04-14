package models;

import com.avaje.ebean.*;
import com.avaje.ebean.config.JsonConfig;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    public String confirmEmail;

    @NotNull
    public String awardCandidate;

    @NotNull
    public String studentVolunteer;

    //new -> uploaded -> submitted -> (others will be like reviewed, approved, TBD)
    @Column(columnDefinition = "VARCHAR(255) default 'new'")
    public String status;

    @NotNull
    @Column(length = 5000)
    public String paperAbstract;

    public String fileFormat;

    public Long fileSize;

    public Date submissionDate;

    @Lob
    public byte[] fileContent;

    @JsonManagedReference
    @OneToMany
    public List<Review> reviews;

    @ManyToOne
    public Conference conference;


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

    /**
     * papers list by user_id and conference_id
     */
    public static List<Paper> getByAuthorAndConference(Long author_id, int conference_id) {
        ExpressionList<Paper> query = Paper.find.select("*")
                                                .where().eq("user_id", author_id);
        if (conference_id != 0) {
            query = query.eq("conference.id", conference_id);
        }
        List<Paper> papers = query.findList();
        return papers;
    }

    /**
     * papers list by user_id
     */
    public static List<Paper> getByAuthor(Long user_id) {
        return find.select("*")
                   .where().eq("user_id", user_id)
                   .findList();
    }

    /**
     * papers list by user_id and conference_id
     */
    public static ArrayList<String> getAuthors(Long paper_id) {
        List<PaperAuthors> items = PaperAuthors.
                find.select("*")
                    .where().eq("paper_id", paper_id)
                    .findList();
        ArrayList<String> authors = new ArrayList<String>();
        for (int i = 0; i < items.size(); i++) {
            authors.add(items.get(i).author_first_name + " " + items.get(i).author_last_name);
        }
        return authors;
    }

    /**
     * all papers
     */
    public static List<Paper> getAllPapers() {
        return find.findList();
    }

    /**
     * get paper by title - not used
     */
    public static List<Paper> getByTitle(String title) {
        return find.where().eq("title", title).findList();
    }

    /**
     * get paper by paper_id
     */
    public static Paper getById(Long id) {
        return find.where().eq("id", id).findUnique();
    }

    /**
     * upload file to database
     */
    public void upload(String format, Long size, byte[] content) {
        this.fileContent = content;
        this.fileSize = size;
        this.fileFormat = format;
        this.status = "uploaded";
        Date date = new Date();
        this.submissionDate = new Date();
        this.save();
    }

    /**
     * list of values for award_candidate fields
     */
    public static List<String> getIsAwardCandidate() {
        List<String> isAwardCandidate = new ArrayList<String>();
        isAwardCandidate.add("Yes");
        isAwardCandidate.add("No");
        return isAwardCandidate;
    }

    /**
     * list of values for student_volunteer fields
     */
    public static List<String> getIsStudentVolunteer() {
        List<String> isStudentVolunteer = new ArrayList<String>();
        isStudentVolunteer.add("Yes");
        isStudentVolunteer.add("No");
        isStudentVolunteer.add("To be decided");
        return isStudentVolunteer;
    }

    public static Map<String, String> getTopics() {
        Map<String, String> topics = new HashMap<String, String>();
        topics.put("1", "Legacy systems migration and modernization");
        topics.put("2", "Service innovation");
        topics.put("3", "Service monitoring and adaptive management");
        topics.put("4", "Architectures for multi-host container deployments");
        topics.put("5", "Services for Big Data");
        return topics;
    }
}