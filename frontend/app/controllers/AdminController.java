package controllers;

import models.ResearchPaper;
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
import play.Logger;

import lib.UserStorage;


public class AdminController extends Controller {

    private FormFactory formFactory;

    @Inject
    public AdminController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result saveResearchTopics() {
       /* User user = UserStorage.getCurrentUser();
        Conference[] conferences = Api.getInstance().getConferencesAll(user.getId());
        Conference[] conferencesUser = Api.getInstance().getConferences(user.getId());
        ArrayList<String> conferenceUsertitle = new ArrayList<String>();

        for(Conference conf: conferencesUser) {
            conferenceUsertitle.add(conf.title);
        }*/
   //  return ok(views.admin.researchTopics.render());
        Form submittedForm = save();

        if (!submittedForm.hasErrors()) {
            ResearchPaper researchPaper = (ResearchPaper) submittedForm.get();
            Logger.debug("paper name" + researchPaper.research_topic);


        }
        ResearchPaper[] rschpaper = Api.getInstance().getResearchTopic();
        Logger.debug("rschpaper" + rschpaper.toString());
       // ArrayList<String> rschpapers = new ArrayList<String>();

      //  return badRequest(views.html.paper.researchTopics.render(submittedForm, flash()));
        return ok(views.html.admin.researchTopics.render(Arrays.asList(rschpaper),formFactory.form(ResearchPaper.class), flash()));
      // return ok(views.html.user.admin.render());
    }

    public Result showResearchTopics() {
        ResearchPaper[] rschpaper=  Api.getInstance().getResearchTopic();
      //  ArrayList<String> rschpapers = new ArrayList<String>();
        Logger.debug("rschpaper" + rschpaper.toString());
        return ok(views.html.admin.researchTopics.render(Arrays.asList(rschpaper), formFactory.form(ResearchPaper.class), flash()));
    }

    protected Form save() {
        Form ResearchTopics = formFactory.form(ResearchPaper.class);

        Form submittedForm = ResearchTopics.bindFromRequest();
        ResearchPaper researchPaper = (ResearchPaper) submittedForm.get();
        Api.getInstance().saveResearchTopic(researchPaper.research_topic);

       // ResearchTopics.data().put("research_topic", research_topic);

        Logger.debug("in admincontroller save");


        return submittedForm;
    }
}

