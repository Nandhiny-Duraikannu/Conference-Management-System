package controllers;

import forms.Login;
import forms.ResetPassword;
import lib.Api;
import lib.UserStorage;
import models.User;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.ValidationError;
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
     * Validate security question answer; if true, set password and send email
     */
    public boolean resetPasswordVerify(ResetPassword resetPassword) {
        String name = resetPassword.getName();

        User thisUser = models.User.getByName(name);

        String securityQuestion = resetPassword.getSecurityQuestion();
        String securityAnswer = resetPassword.getSecurityAnswer();
        String securityAnswerTruth = thisUser.securityAnswer;

        boolean result;


        if (!securityAnswer.equals(securityAnswerTruth) || name == null || securityQuestion == null || securityAnswer == null) {
            result = false;
        } else {
            // Generate random password, set it and send an email
            String newRandomPassword = Long.toHexString(Double.doubleToLongBits(Math.random()));
            Logger.debug("New Password: " + newRandomPassword);
            // TODO Call API
            //thisUser.setPassword(newRandomPassword);
            //thisUser.update();

            result = true;
        }

        return result;
    }

    /**
     * Validate security question answer; if true, set password and send email - Web
     */
    public Result resetPasswordVerifyWeb() {
        Form resetForm = formFactory.form(ResetPassword.class);
        Form submittedForm = resetForm.bindFromRequest(request());
        ResetPassword resetPassword = (ResetPassword) submittedForm.get();

        // TODO Call API
        boolean result = this.resetPasswordVerify(resetPassword);

        if (result) {
            return redirect("/login");
        } else {
            return redirect("/resetpassword/new");
        }
    }

    /**
     * Display the security question and get answer
     */
    public String resetPassword(String name) {
        String securityQuestionNumber = models.User.getByName(name).securityQuestion;
        String securityQuestionString = models.User.getSecurityQuestions().get(securityQuestionNumber);
        return securityQuestionString;
    }

    /**
     * Display the security question and get answer - Web
     */
    public Result resetPasswordWeb(String name) {
        String securityQuestionString = this.resetPassword(name);
        return ok(views.html.user.resetPasswordSecurityForm.render(formFactory.form(User.class),
                                                                   flash(),
                                                                   name,
                                                                   securityQuestionString));
    }

    /**
     * Display the security question and get answer - API
     */
    public Result resetPasswordAPI(String name) {
        String securityQuestionString = this.resetPassword(name);
        return ok(Json.toJson(securityQuestionString));
    }

    /**
     * Display the Reset password form
     */
    public Result resetPasswordNew() {
        return ok(views.html.user.resetPasswordForm.render(formFactory.form(User.class), flash()));
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
            boolean created = Api.getInstance().createUser(submittedForm.data());

            if (!created) {
                submittedForm.globalErrors().add(0,
                                                 new ValidationError("name", "Could not create user. Try again later"));
            }
        }

        return submittedForm;
    }
}
            
