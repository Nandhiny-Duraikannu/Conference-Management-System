package controllers;

import forms.ResetPassword;
import lib.EmailHelper;
import models.Review;
import models.User;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

/**
 * Provides web and api endpoints for user actions
 */
public class ReviewController extends Controller {

    private FormFactory formFactory;

    @Inject
    public ReviewController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    /**
     * Creates user via REST api
     */
    public Result create() {
        Form signupForm = formFactory.form(Review.class);
        Form submittedForm = signupForm.bindFromRequest();

        if (!submittedForm.hasErrors()) {
            Review review = (Review) submittedForm.get();
            review.save();
            return created(Json.toJson(review));
        } else {
            return badRequest(submittedForm.errorsAsJson());
        }
    }

    /*public Result getByAllUserAndConference(long userId, long conferenceId) {

    }*/
}
            
