package controllers;

import forms.Login;
import forms.ResetPassword;
import lib.UserStorage;
import lib.EmailHelper;
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
     * Creates user via REST api
     */
    public Result create() {
        Form form = save();

        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        } else {
            User user = (User) form.get();
           // Logger.debug("Signed up as " + user.name);
            //session().put("username", user.name);
            return created(Json.toJson(user));
        }
    }

    /**
     * Creates user via REST api
     */
    public Result update() {
        Form form = save();

        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        } else {
            User user = (User) form.get();
            // Logger.debug("Signed up as " + user.name);
            //session().put("username", user.name);
            return created(Json.toJson(user));
        }
    }

    /**
     * Returns user by name
     */
    public Result getByName(String name) {
        User user = User.getByName(name);

        if (user != null) {
            /*Logger.debug("Logged in as " + formObj.getName());
            session().put("username", formObj.getName());*/
            return ok(Json.toJson(user));
        } else {
            return notFound();
        }
    }

    /**
     * Validate security question answer; if true, set password and send email
     */
    public Result resetPasswordVerifyAPI() {
        Form resetForm = formFactory.form(ResetPassword.class);
        Form submittedForm = resetForm.bindFromRequest(request());
        ResetPassword resetPassword = (ResetPassword) submittedForm.get();

        String name = resetPassword.getName();

        User thisUser = models.User.getByName(name);

        String securityQuestion = resetPassword.getSecurityQuestion();
        String securityAnswer = resetPassword.getSecurityAnswer();
        String securityAnswerTruth = thisUser.securityAnswer;

        if(!securityAnswer.equals(securityAnswerTruth) || name == null || securityQuestion == null || securityAnswer == null) {
            return badRequest();
        } else {
            // Generate random password, set it and send an email
            String newRandomPassword = Long.toHexString(Double.doubleToLongBits(Math.random()));
            Logger.debug("New Password: " + newRandomPassword);
            thisUser.setPassword(newRandomPassword);
            thisUser.update();

            EmailHelper.sendEmail(thisUser.email, "You new password for Conference Management System!", "Your new password is: " + newRandomPassword);

            return ok();
        }
    }

    /**
     * Return security question for the user
     */
    public Result resetPassword(String name) {
        String securityQuestionNumber = models.User.getByName(name).securityQuestion;
        String securityQuestionString = models.User.getSecurityQuestions().get(securityQuestionNumber);
        return ok(Json.toJson(securityQuestionString));
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
            
