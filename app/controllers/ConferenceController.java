package controllers;


import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Result;

import javax.inject.Inject;

import models.*;
import java.util.ArrayList;
import java.util.List;

import static play.mvc.Controller.flash;
import static play.mvc.Results.ok;

public class ConferenceController {

    private FormFactory formFactory;

    @Inject
    public ConferenceController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

   public Result getAllConferences() {
       List<Conference> conferences = new ArrayList<Conference>();
       return ok(Json.toJson(conferences));
    }

    /* display conference list page*/
    public Result showConferencePage() {
        List<Conference> conferences = new ArrayList<Conference>();
        return ok(views.html.conference.render(conferences,flash()));
    }

}
