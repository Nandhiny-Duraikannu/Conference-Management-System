package forms;

import models.Conference;

import java.util.ArrayList;
import java.util.List;

import play.data.FormFactory;
import play.libs.Json;
import javax.inject.Inject;

import models.*;
import play.mvc.Result;

import static play.mvc.Controller.flash;
import static play.mvc.Results.ok;


/**
 * Conference List
 */

public class ConferenceList {
    private List<Conference> conferences;

    public List<Conference> getConferences() {
        conferences = Conference.getAllConferences();
        return conferences;
    }
    /* display conference list page*/
//    public Result showConferencePage() {
//        List<Conference> conferences = new ArrayList<Conference>();
//        return ok(views.html.conference.conference.render(conferences,flash()));
//    }
}
