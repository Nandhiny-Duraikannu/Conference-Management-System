package models;

import com.avaje.ebean.*;
import lib.Api;
import lib.UserStorage;
import play.data.validation.*;
import play.libs.Json;
import play.mvc.Result;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;


/**
 * Paper entity
 */
public class Paper {
    public Long id;

    public User user;

    public String title;

    public String topic;

    @Constraints.Email
    public String contactEmail;

    @Constraints.Email
    public String confirmEmail;

    public String awardCandidate;

    public String studentVolunteer;

    public String status;

    @Column(length = 5000)
    public String paperAbstract;

    public String fileFormat;

    public Long fileSize;

    public String submissionDate;

    public byte[] fileContent;

    public Conference conference;
    public List<Review> reviews;

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
        // TODO Call API
        return null;
    }

    /**
     * papers list by user_id and conference_id
     */
    public static List<Paper> getByAuthorAndConference(Long author_id, int conference_id){
        // TODO Call API
        return null;
    }

    /**
     * papers list by user_id
     */
    public static List<Paper> getByAuthor(Long user_id){
        // TODO Call API
        return null;
    }

    /**
     * papers list by user_id and conference_id
     */
    public static ArrayList<String> getAuthors(Long paper_id) {
        ArrayList<String> authors = new ArrayList<String>(Arrays.asList(
                Api.getInstance().getAuthors(paper_id)
        ));
        return authors;
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