package controllers;

import forms.ResetPassword;
import lib.EmailHelper;
import models.Paper;
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
     * Create review
     */
    public Result create() {
        Form signupForm = formFactory.form(Review.class);
        Form submittedForm = signupForm.bindFromRequest();

        if (!submittedForm.hasErrors()) {
            Review review = (Review) submittedForm.get();
            review.save();
            return created();
        } else {
            return badRequest(submittedForm.errorsAsJson());
        }
    }

    /**
     * Edit review
     */
    public Result update(Long id) {
        Review review = Review.find.byId(id);

        if (review == null) {
            return notFound();
        }

        review.setContent(request().body().asFormUrlEncoded().get("content")[0]);
        review.update();

        return ok();
    }

    public Result getById(Long id) {
        Review review = Review.getById(id);
        return ok(Json.toJson(review));
    }

    public Result getByUser(long userId) {
        return ok(Json.toJson(Review.getByUser(userId)));
    }

    public Result getByUserAndConference(long userId, long confId) {
        return ok(Json.toJson(Review.getByUserAndConference(userId, confId)));
    }
}
            
