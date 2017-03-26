package controllers;

import forms.Login;
import forms.Signup;
import lib.UserStorage;
import models.User;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import javax.inject.Inject;

/**
 * Manage a database of Users
 */
public class UserController extends Controller {

    private FormFactory formFactory;

    @Inject
    public UserController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result showLoginForm() {
        return ok(views.html.user.loginForm.render(formFactory.form(Login.class), flash()));
    }

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

    public Result showSignupForm() {
        return ok(views.html.user.signupForm.render(formFactory.form(Signup.class), flash()));
    }

    public Result signup() {
        Form signupForm = formFactory.form(Signup.class);
        Form submittedForm = signupForm.bindFromRequest("name", "password");

        if (!submittedForm.hasErrors()) {
            Signup formObj = (Signup) submittedForm.get();
            User user = new User();
            user.name = formObj.getName();
            user.password = formObj.getPassword();
            user.save();

            Logger.debug("Signed up as " + formObj.getName());
            session().put("username", formObj.getName());
            return redirect("/");
        }

        return badRequest(views.html.user.signupForm.render(submittedForm, flash()));
    }

    @Security.Authenticated(UserStorage.class)
    public Result logout() {
        session().clear();
        flash("logout", "1");
        return redirect(routes.UserController.showLoginForm());
    }
}
            
