package controllers;

import models.ResearchPaper;
import forms.ResearchTopic;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.naming.spi.DirStateFactory;

import java.util.ArrayList;
import java.util.List;
import play.Logger;

/**
 * Created by nandh on 4/27/2017.
 */
public class AdminController extends Controller {
    private FormFactory formFactory;

    @Inject
    public AdminController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result saveResearchTopic(){



        Form ResearchTopic = formFactory.form(ResearchTopic.class);
        Form submittedForm = ResearchTopic.bindFromRequest();
        ResearchTopic researchTopic = (ResearchTopic) submittedForm.get();
        Logger.debug("api admin control save");
        String research_topic = researchTopic.setTopic();
    // String research_topic = researchTopic.setTopic();
        Logger.debug("admin api"+research_topic);
        ResearchPaper rschppr =  new ResearchPaper();
        rschppr.saveResearchTopic(research_topic);

        return ok();

    }

    public Result getResearchTopic(){
        List<ResearchPaper> topics = ResearchPaper.getResearchTopic();
        return ok(Json.toJson(topics));

    }
}
