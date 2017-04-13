package controllers;

import models.Review;
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
    public Result getConferencesWithMyReviews() {

    }

    public Result getById(Long id) {
        Review review = Review.getById(id);
        return ok(Json.toJson(review));
    }

    public Result getByUser(long userId) {
        return ok(Json.toJson(Review.getByUser(userId)));
    }
}
            
