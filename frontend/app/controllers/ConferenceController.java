package controllers;

import models.Conference;
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

       public Result getAllConferences() {
        List<Conference> conferences = new ArrayList<Conference>();
        return ok(Json.toJson(conferences));
    }
    public Result showConferencePage() {
        List<Conference> conferences = new ArrayList<Conference>();
        return ok(views.html.conference.conference.render(conferences,flash()));
    }


}

