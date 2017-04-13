package controllers;

import json.UserConferenceReviews;
import lib.Api;
import lib.UserStorage;
import models.Review;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Provides web and api endpoints for user actions
 */
public class ReviewController extends Controller {

    private FormFactory formFactory;

    @Inject
    public ReviewController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }


    // Displays a page with conferences for which current user has assigned papers
    public Result myConferencesWithReviews() {
        ArrayList<UserConferenceReviews> conferences = Api.getInstance().getConferencesWithAssignedReviewer(
                UserStorage.getCurrentUser().getId());

        return ok(views.html.review.myConferencesWithReviews.render(conferences, flash()));
    }

    // Displays a page with papers for conference that were assigned for review to current user
    public Result myConferenceReviews(Long confId) {
        ArrayList<Review> reviews = Api.getInstance().getReviewsByUserAndConference(
                UserStorage.getCurrentUser().getId(), confId);

        return ok(views.html.review.myConferenceReviews.render(reviews, flash()));
    }

    /**
     * Displays review edit form
     */
    public Result showEditForm(Long id) {
        Review review = Api.getInstance().getReview(id);
        Form reviewForm = formFactory.form(Review.class);
        reviewForm.data().put("content", review.content);
        return ok(views.html.review.reviewForm.render(review, reviewForm, flash()));
    }

    /**
     * Submit review form
     */
    public Result edit(Long reviewId) {
        Api.getInstance().editReview(
                reviewId,
                request().body().asFormUrlEncoded().get("content")[0]);
        return redirect("/reviews");
    }
}
            
