package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import play.mvc.*;
import play.data.*;
import play.libs.Json;
import static play.data.Form.*;
import static play.data.DynamicForm.*;
import play.Logger;

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
        //User thisUser = this.updateProfile(submittedForm);
        return redirect("/");
    }
}
            
