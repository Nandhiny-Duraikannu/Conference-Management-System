package models;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import com.avaje.ebean.PagedList;
import play.Logger;
import play.data.validation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * User entity
 */
public class User {
    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String password;

    @Constraints.Required
    public String securityQuestion;

    @Constraints.Required
    public String securityAnswer;

    @Constraints.Required
    @Constraints.Email
    public String email;

    public String title;

    public String researchAreas;

    public String firstName;

    public String lastName;

    public String position;

    public String affiliation;

    public String phone;

    public String fax;

    public String address;

    public String city;

    public String country;

    public String zip;

    @Column(length = 5000)
    public String comments;

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
        return null;
    }

    public static User getByName(String name) {
        return null;
    }

    public static User getByEmail(String email) {
        return null;
    }

    public static User getByNameAndPassword(String name, String password) {
        return null;
    }


    public static Map<String, String> getSecurityQuestions() {
        Map<String, String> questions = new HashMap<String, String>();
        questions.put("1", "My favorite computer science paper");
        questions.put("2", "How I fell in love with web services");
        questions.put("3", "My favorite Jia class");
        return questions;
    }

    public static List<String> getTitles() {
        List<String> titles = new ArrayList<String>();
        titles.add("Mr");
        titles.add("Ms");
        titles.add("Dr");
        return titles;
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return (new HexBinaryAdapter()).marshal(md5.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            return password;
        }
    }

    /**
     * Hashes password before storing it
     */
    public void setPassword(String password) {
        this.password = password == null  || password.equals("") ? null : hashPassword(password);
    }

    public void setEmail(String email) { this.email = email; }
    public void setResearchAreas(String researchAreas) { this.researchAreas = researchAreas; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setTitle(String title) { this.title = title; }
    public void setPosition(String position) { this.position = position; }
    public void setAffiliation(String affiliation) { this.affiliation = affiliation; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setFax(String fax) { this.fax = fax; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setCountry(String country) { this.country = country; }
    public void setZip(String zip) { this.zip = zip; }
    public void setComments(String comments) { this.comments = comments; }

}

