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

    public Date submissionDate;

    public List<Review> reviews;

    public byte[] fileContent;

    public Conference conference;

    public Long conferenceId;

    public PaperAuthors[] authors = new PaperAuthors[7];

    public PaperAuthors[] getAuthors() {
        return authors;
    }

    public void setAuthors(PaperAuthors[] authors) {
        this.authors = authors;
    }

    /**
     * authors for specified paper id
     */
    public static ArrayList<String> getAuthorNames(Long paper_id) {
        ArrayList<String> result = new ArrayList<>();

        for (String name: Api.getInstance().getAuthors(paper_id)) {
            if (name.length() > 1) {
                result.add(name);
            }
        }

        return result;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public String getAwardCandidate() {
        return awardCandidate;
    }

    public void setAwardCandidate(String awardCandidate) {
        this.awardCandidate = awardCandidate;
    }

    public String getStudentVolunteer() {
        return studentVolunteer;
    }

    public void setStudentVolunteer(String studentVolunteer) {
        this.studentVolunteer = studentVolunteer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }
}