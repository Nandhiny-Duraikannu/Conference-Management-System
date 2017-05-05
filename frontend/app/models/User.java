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

import lib.Api;

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

    public String role;//user, admin, chair, reviewer

    @Column(length = 5000)
    public String comments;

    public static User getByName(String name) {
        return Api.getInstance().getUserByName(name);
    }

    public static List<User> getAllUsers() {
        return Api.getInstance().getAllUsers();
    }

    public static User getByNameAndPassword(String name, String password) {
        User user = Api.getInstance().getUserByName(name);

        if (user == null) {
            System.out.println("User " + name + " not found");
            return null;
        }

        if (!hashPassword(password).equals(user.password)) {
            System.out.println("password invalid " + hashPassword(password) + " " + user.password);
            return null;
        }

        return user;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isAdmin() {
        return role.equals("admin");
    }
}

