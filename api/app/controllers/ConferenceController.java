package controllers;

import models.Conference;
import models.Review;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
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
        List<Conference> conf = Conference.getAllConferences();
        return ok(Json.toJson(conf));
    }

    public Result getConferencesByUser(Long user_id) {
        List<Conference> conf = Conference.getConferencesByUser(user_id);
        return ok(Json.toJson(conf));
    }
}
            
