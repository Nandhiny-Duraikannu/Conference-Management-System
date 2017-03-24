package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import models.*;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

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
     * This result directly redirect to application home.
     */
    public Result GO_HOME = Results.redirect(
        routes.HomeController.list(0, "name", "asc", "")
    );
    
    /**
     * Handle default path requests, redirect to Users list
     */
    public Result index() {
        return GO_HOME;
    }

    /**
     * Display the paginated list of Users.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on User names
     */
    public Result list(int page, String sortBy, String order, String filter) {
        return ok(
            views.html.list.render(
                User.page(page, 10, sortBy, order, filter),
                sortBy, order, filter
            )
        );
    }
    
    /**
     * Display the 'edit form' of a existing User.
     *
     * @param id Id of the User to edit
     */
    public Result edit(Long id) {
        Form<User> UserForm = formFactory.form(User.class).fill(
            User.find.byId(id)
        );
        return ok(
            views.html.editForm.render(id, UserForm)
        );
    }
    
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the User to edit
     */
    public Result update(Long id) throws PersistenceException {
        Form<User> UserForm = formFactory.form(User.class).bindFromRequest();
        if(UserForm.hasErrors()) {
            return badRequest(views.html.editForm.render(id, UserForm));
        }

        Transaction txn = Ebean.beginTransaction();
        try {
            User savedUser = User.find.byId(id);
            if (savedUser != null) {
                User newUserData = UserForm.get();
                savedUser.name = newUserData.name;

                savedUser.update();
                flash("success", "User " + UserForm.get().name + " has been updated");
                txn.commit();
            }
        } finally {
            txn.end();
        }

        return GO_HOME;
    }
    
    /**
     * Display the 'new User form'.
     */
    public Result create() {
        Form<User> userForm = formFactory.form(User.class);
        return ok(
                views.html.createForm.render(userForm)
        );
    }
    
    /**
     * Handle the 'new User form' submission 
     */
    public Result save() {
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();
        if(userForm.hasErrors()) {
            return badRequest(views.html.createForm.render(userForm));
        }
        userForm.get().save();
        flash("success", "User " + userForm.get().name + " has been created");
        return GO_HOME;
    }
    
    /**
     * Handle User deletion
     */
    public Result delete(Long id) {
        User.find.ref(id).delete();
        flash("success", "User has been deleted");
        return GO_HOME;
    }
}
            
