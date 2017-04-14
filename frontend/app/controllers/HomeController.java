package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import play.mvc.*;
import play.data.*;
import play.libs.Json;
import static play.data.Form.*;
import static play.data.DynamicForm.*;
import play.Logger;
import java.util.*;
import lib.Api;

import models.*;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

/**
 * Main controller
 */
public class HomeController  extends Controller {

    private FormFactory formFactory;

    @Inject
    public HomeController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }
    
    /**
     * Main page
     */
    public Result index() {
        return ok(views.html.index.render());
    }

    /**
     * Show the profile of the user
     */
    public Result showProfile() {
        return ok(views.html.user.profileForm.render(formFactory.form(User.class), flash()));
    }

    /**
     * Update the profile of the user - Web
     */
    public Result updateProfileWeb() {
        DynamicForm submittedForm = form().bindFromRequest();

        // TODO: There should be a better way to do this.
        // https://github.com/playframework/playframework/issues/1519
        Map<String, String> profileData = new HashMap<String, String>();
        profileData.put("name", submittedForm.get("name"));
        profileData.put("email", submittedForm.get("email"));
        profileData.put("researchAreas", submittedForm.get("researchAreas"));
        profileData.put("firstName", submittedForm.get("firstName"));
        profileData.put("lastName", submittedForm.get("lastName"));
        profileData.put("title", submittedForm.get("title"));
        profileData.put("position", submittedForm.get("position"));
        profileData.put("affiliation", submittedForm.get("affiliation"));
        profileData.put("fax", submittedForm.get("fax"));
        profileData.put("phone", submittedForm.get("phone"));
        profileData.put("address", submittedForm.get("address"));
        profileData.put("city", submittedForm.get("city"));
        profileData.put("country", submittedForm.get("country"));
        profileData.put("zip", submittedForm.get("zip"));
        profileData.put("comments", submittedForm.get("comments"));

        Api.getInstance().updateProfile(profileData);
        return redirect("/");
    }
}
            
