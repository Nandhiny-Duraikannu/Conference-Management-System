package controllers;

import forms.Login;
import lib.UserStorage;
import models.User;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;

import javax.inject.Inject;

/**
 * Provides web and api endpoints for user actions
 */
public class UserController extends Controller {

    private FormFactory formFactory;

    @Inject
    public UserController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    /**
     * Displays login page
     */
    public Result showLoginForm() {
        return ok(views.html.user.loginForm.render(formFactory.form(Login.class), flash()));
    }

    /**
     * Creates user via REST api
     */
    public Result create() {
        Form form = save();

        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        } else {
            return created(Json.toJson((User) form.get()));
        }
    }

    /**
     * Creates user via web interface
     */
    public Result signup() {
        Form submittedForm = save();

        if (!submittedForm.hasErrors()) {
            User user = (User) submittedForm.get();
            Logger.debug("Signed up as " + user.name);
            session().put("username", user.name);
            return redirect("/");
        }

        return badRequest(views.html.user.signupForm.render(submittedForm, flash()));
    }

    /**
     * Logs user in
     */
    public Result login() {
        Form loginForm = formFactory.form(Login.class);
        Form submittedForm = loginForm.bindFromRequest("name", "password");

        if (!submittedForm.hasErrors()) {
            Login formObj = (Login) submittedForm.get();
            Logger.debug("Logged in as " + formObj.getName());
            session().put("username", formObj.getName());
            return redirect("/");
        }

        return badRequest(views.html.user.loginForm.render(submittedForm, flash()));
    }

    /**
     * Displays signup page
     */
    public Result showSignupForm() {
        return ok(views.html.user.signupForm.render(formFactory.form(User.class), flash()));
    }

    /**
     * logs user out
     */
    @Security.Authenticated(UserStorage.class)
    public Result logout() {
        session().clear();
        return redirect(routes.UserController.showLoginForm());
    }

    protected Form save() {
        Form signupForm = formFactory.form(User.class);
        Form submittedForm = signupForm.bindFromRequest();

        if (!submittedForm.hasErrors()) {
            User user = (User) submittedForm.get();
            user.save();
        }

        return submittedForm;
    }
}
            
