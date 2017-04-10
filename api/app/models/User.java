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
 * User entity managed by Ebean
 */
@Entity
public class User extends com.avaje.ebean.Model {

    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @Constraints.Required
    @Column(unique = true)
    public String name;

    @Constraints.Required
    public String password;

    @Constraints.Required
    public String securityQuestion;

    @Constraints.Required
    public String securityAnswer;

    @Constraints.Required
    @Constraints.Email
    @Column(unique = true)
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

    public static User getByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }

    public static User getByNameAndPassword(String name, String password) {
        try {
            User user = getByName(name);

            if (user == null) {
                throw new IllegalArgumentException("User with login '" + name + "' was not found");
            }

            if (!hashPassword(password).equals(user.password)) {
                throw new IllegalArgumentException("Password for user '" + name + "' doesn't match");
            }

            return user;
        } catch (Exception e) {
            Logger.error("An error occurred on getting user by login and password. Login used: " + name, e);
        }

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

    public List<ValidationError> validate() {
        List errors = new ArrayList();

        if (User.getByName(name) != null) {
            errors.add(new ValidationError("name", "This user already exists"));
        } else if (User.getByEmail(email) != null) {
            errors.add(new ValidationError("email", "This user already exists"));
        }

        return errors;
    }

    /**
     * Hashes password before storing it
     */
    public void setPassword(String password) {
        this.password = password == null || password.equals("") ? null : hashPassword(password);
    }

    public String getPassword() {
        return this.password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResearchAreas() {
        return researchAreas;
    }

    public void setResearchAreas(String researchAreas) {
        this.researchAreas = researchAreas;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

