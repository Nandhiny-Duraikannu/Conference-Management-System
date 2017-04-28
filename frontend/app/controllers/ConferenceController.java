package controllers;

import models.Conference;
import models.Paper;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import lib.Api;

import javax.inject.Inject;
import java.io.File;
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


    /**
     * Displays conference create page
     */
    public Result showCreateForm() {
        return ok(views.html.conference.form.render(null, formFactory.form(Conference.class), flash()));
    }

    /**
     * Displays conference edit page
     */
    public Result showEditForm(Long id) {
        Conference conf = Api.getInstance().getConferenceById(id);
        Form confForm = formFactory.form(Conference.class);
        confForm = confForm.fill(conf);

        return ok(views.html.conference.form.render(id, confForm, flash()));
    }

    /**
     * Creates conference via web interface
     */
    public Result create() {
        Conference conf = new Conference();
        Form form = formFactory.form(Conference.class);
        form = form.bindFromRequest(request());
        conf = (Conference) form.get();

        File file = null;

        if (request().body().asMultipartFormData().getFile("logo") != null) {
            file = (File) request().body().asMultipartFormData().getFile("logo").getFile();
        };

        conf = Api.getInstance().editOrCreateConference(conf);

        System.out.println(conf.id);
        System.out.println(conf.title);
        if (file != null && conf.id != null) {
            Api.getInstance().setConferenceLogo(conf.id, file);
        }

        return redirect("/conferences");
    }

    // Edit conference
    public Result update(Long id) {
        Conference conf = new Conference();
        Form form = formFactory.form(Conference.class);
        form = form.bindFromRequest(request());
        conf = (Conference) form.get();
        conf.setId(id);

        File file = null;

        if (request().body().asMultipartFormData().getFile("logo") != null) {
            file = (File) request().body().asMultipartFormData().getFile("logo").getFile();
        };

        conf = Api.getInstance().editOrCreateConference(conf);

        if (file != null) {
            Api.getInstance().setConferenceLogo(conf.id, file);
        }

        return redirect("/conferences/admin?conf_id="+conf.id);
    }


    public Result showConferencePage() {
        User user = UserStorage.getCurrentUser();
        Conference[] conferences = Api.getInstance().getConferencesAll(user.getId());
        Conference[] conferencesUser = Api.getInstance().getConferences(user.getId());
        ArrayList<String> conferenceUsertitle = new ArrayList<String>();

        for (Conference conf : conferencesUser) {
            conferenceUsertitle.add(conf.title);
        }
        return ok(views.html.conference.conference.render(Arrays.asList(conferences), conferenceUsertitle, flash()));
    }

    public Result showConferencePageFilter(String keyword, String conf_status) {
        User user = UserStorage.getCurrentUser();
        Conference[] conferences = Api.getInstance().getConferencesKeyword(user.getId(), keyword, conf_status);
        Conference[] conferencesUser = Api.getInstance().getConferences(user.getId());
        ArrayList<String> conferenceUsertitle = new ArrayList<String>();
        for (Conference conf : conferencesUser) {
            conferenceUsertitle.add(conf.title);
        }
        return ok(views.html.conference.conference.render(Arrays.asList(conferences), conferenceUsertitle, flash()));
    }

    /**
     * Display admin page
     */
    public Result showAdminPage(Long conf_id) {
        System.out.println(conf_id);
        return ok(views.html.conference.adminPage.render(conf_id, flash()));
    }

    /**
     * Display admin page
     */
    public Result showPCMembers(Long conf_id) {
        return ok(views.html.conference.Members.render(conf_id, flash()));
    }

    /**
     * Display admin page
     */
    public Result showEmailTemplates(Long conf_id) {
        return ok(views.html.conference.EmailTemplates.render(conf_id, flash()));
    }
}

