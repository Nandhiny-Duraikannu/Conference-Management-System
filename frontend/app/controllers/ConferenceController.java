package controllers;

import models.Conference;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import lib.Api;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

import lib.UserStorage;
import models.User;

/**
 * Provides web and api endpoints for conference actions
 */
public class ConferenceController extends Controller {

    private FormFactory formFactory;

    @Inject
    public ConferenceController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

      
    public Result showConferencePage() {
        User user = UserStorage.getCurrentUser();
        Conference[] conferences = Api.getInstance().getConferences(user.getId());
        Conference[] conferencesUser = Api.getInstance().getConferencesByUser(user.getId());
        ArrayList<String> conferenceUsertitle = new ArrayList<String>();
        
        for(Conference conf: conferencesUser) {
            conferenceUsertitle.add(conf.title);
        }
        return ok(views.html.conference.conference.render(Arrays.asList(conferences), conferenceUsertitle,flash()));
    }

    public Result showConferencePageFilter(String keyword, String conf_status) {
        User user = UserStorage.getCurrentUser();
        Conference[] conferences = Api.getInstance().getConferencesKeyword(user.getId(), keyword, conf_status);
        Conference[] conferencesUser = Api.getInstance().getConferencesByUser(user.getId());
        ArrayList<String> conferenceUsertitle = new ArrayList<String>();
        for(Conference conf: conferencesUser) {
            conferenceUsertitle.add(conf.title);
        }
        return ok(views.html.conference.conference.render(Arrays.asList(conferences), conferenceUsertitle,flash()));
    }
}

