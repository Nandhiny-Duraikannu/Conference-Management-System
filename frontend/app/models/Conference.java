package models;

import lib.Api;
import lib.UserStorage;

import java.util.*;


/**
 * Conference entity
 */
public class Conference {

    private static final long serialVersionUID = 1L;

    public Long id;
    public String acronym;
    public String title;
    public String location;
    public Date deadline;
    public String status;
    public Date submissionDateStart;
    public String logo;

    /**
     * list of all conferences
     */
    // Should move to backend if not already.
    public static List<Conference> getAllConferences() {
        User user = UserStorage.getCurrentUser();
        List<Conference> conferences = new ArrayList<Conference>(Arrays.asList(
                Api.getInstance().getConferences(null)
        ));
        return null;
    }

    /**
     * conferences for which user submitted papers
     */
    // Should move to backend if not already.
    public static List<Conference> getConferencesByUser() {
        User user = UserStorage.getCurrentUser();
        System.out.println(user.id);
        List<Conference> conferences = new ArrayList<Conference>(Arrays.asList(
                Api.getInstance().getConferences(user.id)
        ));
        return conferences;
    }

    public String getLogoUrl() {
        return "http://localhost:9000/uploads/" + logo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSubmissionDateStart() {
        return submissionDateStart;
    }

    public void setSubmissionDateStart(Date submissionDateStart) {
        this.submissionDateStart = submissionDateStart;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}