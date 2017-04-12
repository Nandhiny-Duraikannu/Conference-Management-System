package controllers;

import play.mvc.*;
import play.data.*;
import play.libs.Json;
import static play.data.Form.*;

import models.*;

import javax.inject.Inject;

/**
 * Manage a database of Users
 */
public class HomeController  extends Controller {

    private FormFactory formFactory;

    @Inject
    public HomeController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    /**
     * Update the profile of the user
     */
    public User updateProfile(DynamicForm submittedForm) {
        String name = submittedForm.get("name");
        String email = submittedForm.get("email");
        String researchAreas = submittedForm.get("researchAreas");
        String firstName = submittedForm.get("firstName");
        String lastName = submittedForm.get("lastName");
        String title = submittedForm.get("title");
        String position = submittedForm.get("position");
        String affiliation = submittedForm.get("affiliation");
        String phone = submittedForm.get("phone");
        String fax = submittedForm.get("fax");
        String address = submittedForm.get("address");
        String city = submittedForm.get("city");
        String country = submittedForm.get("country");
        String zip = submittedForm.get("zip");
        String comments = submittedForm.get("comments");

        User thisUser = models.User.getByName(name);

        thisUser.setEmail(email);
        thisUser.setResearchAreas(researchAreas);
        thisUser.setFirstName(firstName);
        thisUser.setLastName(lastName);
        thisUser.setTitle(title);
        thisUser.setPosition(position);
        thisUser.setAffiliation(affiliation);
        thisUser.setPhone(phone);
        thisUser.setFax(fax);
        thisUser.setAddress(address);
        thisUser.setCity(city);
        thisUser.setCountry(country);
        thisUser.setZip(zip);
        thisUser.setComments(comments);

        thisUser.update();

        return thisUser;
    }

    /**
     * Update the profile of the user - API
     */
    public Result updateProfileAPI() {
        DynamicForm submittedForm = form().bindFromRequest();
        User thisUser = this.updateProfile(submittedForm);
        return ok(Json.toJson(thisUser));
    }

    public Result helloWorld() {
        return ok(Json.toJson("Hi! This is your conference management API"));
    }
}
            
