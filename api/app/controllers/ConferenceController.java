package controllers;

import models.Conference;
import models.Review;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides web and api endpoints for conference actions
 */
public class ConferenceController extends Controller {

    private FormFactory formFactory;

    @Inject
    public ConferenceController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result getById(Long id) {
        Conference conf = Conference.find.byId(id);
        return ok(Json.toJson(conf));
    }

       
    public Result getAllConferences() {
        List<Conference> conferences = new ArrayList<Conference>();
        return ok(Json.toJson(conferences));
    }

    /**
     * Returns conferences for which given user has papers to review
     *
     * @param userId
     * @return
     */
    public Result getWithAssignedReviewer(Long userId) {
        return ok(Json.toJson(Conference.getUserConferenceReviews(userId)));

    }
}
            
