package controllers;

import json.ConferenceReviewer;
import json.PaperConferenceReviews;
import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import lib.Api;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

import lib.UserStorage;

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
        }
        ;

        conf = Api.getInstance().editOrCreateConference(conf);

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
            Http.MultipartFormData.FilePart<Object> fp = request().body().asMultipartFormData().getFile("logo");

            if (fp.getContentType().contains("image")) {
                file = (File) fp.getFile();
            }
        }
        ;

        conf = Api.getInstance().editOrCreateConference(conf);

        if (file != null) {
            System.out.println("uploading file");
            Api.getInstance().setConferenceLogo(conf.id, file);
        }

        return redirect("/conferences/admin?conf_id=" + conf.id);
    }


    public Result showConferencePage() {
        User user = UserStorage.getCurrentUser();
        Conference[] conferences = Api.getInstance().getConferencesAll(user.getId());
        Conference[] conferencesUser = Api.getInstance().getConferences(user.getId());
        ArrayList<String> conferenceUsertitle = new ArrayList<String>();

        for (Conference conf : conferencesUser) {
            conferenceUsertitle.add(conf.title);
        }

        ArrayList<String> conferenceChair = new ArrayList<String>();
        ArrayList<String> conferenceReview = new ArrayList<String>();
        ArrayList<PCMember> members = Api.getInstance().getUserRoles(user.getId());
        if (members != null) {
            for (PCMember member : members) {
                if(member.role.equals("reviewer")){
                    conferenceReview.add(member.conference.title);
                }else if(member.role.equals("chair")){
                    conferenceChair.add(member.conference.title);
                }
            }
        }

        return ok(views.html.conference.conference.render(Arrays.asList(conferences), conferenceUsertitle, conferenceReview, conferenceChair, flash()));
    }

    public Result showConferencePageFilter(String keyword, String conf_status) {
        User user = UserStorage.getCurrentUser();
        Conference[] conferences = Api.getInstance().getConferencesKeyword(user.getId(), keyword, conf_status);
        Conference[] conferencesUser = Api.getInstance().getConferences(user.getId());
        ArrayList<String> conferenceUsertitle = new ArrayList<String>();
        for (Conference conf : conferencesUser) {
            conferenceUsertitle.add(conf.title);
        }

        ArrayList<String> conferenceChair = new ArrayList<String>();
        ArrayList<String> conferenceReview = new ArrayList<String>();
        ArrayList<PCMember> members = Api.getInstance().getUserRoles(user.getId());
        if (members != null) {
            for (PCMember member : members) {
                if(member.role == "reviewer"){
                    conferenceReview.add(member.conference.title);
                }else if(member.role == "chair"){
                    conferenceChair.add(member.conference.title);
                }
            }
        }

        return ok(views.html.conference.conference.render(Arrays.asList(conferences), conferenceUsertitle, conferenceReview, conferenceChair, flash()));

    }

    /**
     * Display admin page
     */
    public Result showAdminPage(Long conf_id) {
        return ok(views.html.conference.adminPage.render(conf_id, flash()));
    }

    /**
     * Display PC chair page
     */
    public Result showChairPage(Long conf_id) {
        return ok(views.html.conference.chairPage.render(conf_id, flash()));
    }

    /**
     * Display PC chair page
     */
    public Result getPapersReviewInfo(Long conf_id, String statusFilter) {
        System.out.println(statusFilter);
        ArrayList<PaperConferenceReviews> papers = Api.getInstance().getConferencesPapersStatus(conf_id, statusFilter);
        System.out.println(papers.size());
        return ok(views.html.conference.papersStatus.render(statusFilter, papers, conf_id, flash()));
    }

    // Submit paper status form
    public Result savePaperStatus(Long id, String status) {
        boolean result = Api.getInstance().setPaperStatus(id, status);
        return ok(Json.toJson(result));
    }



    /**
     * Display PC members
     */
    public Result showPCMembers(Long conf_id) {
        ArrayList<PCMember> members = new ArrayList<PCMember>(Arrays.asList(
                Api.getInstance().getPCMembersByConfId(conf_id)
        ));
        System.out.println(members);
        Conference conf = Api.getInstance().getConferenceById(conf_id);
        System.out.println(conf);
        return ok(views.html.conference.Members.render(conf, members, flash()));
    }

    /**
     * Display conference review state
     */
    public Result reviewState(Long conf_id) {
        ArrayList<ConferenceReviewer> reviewers = Api.getInstance().getConferenceReviewers(conf_id);
        return ok(views.html.conference.reviewState.render(conf_id, reviewers, flash()));
    }

    public Result sendNotifications(Long conf_id) {
        ArrayList<String> emails = Api.getInstance().notifyReviewers(conf_id);

        return ok(Json.toJson(emails));
    }

    /**
     * Delete PC member
     */
    public Result addPCMember(Long conf_id) {
        Api.getInstance().addPCMember(conf_id,
                                      Long.parseLong(request().body().asFormUrlEncoded().get("user_id")[0]),
                                      request().body().asFormUrlEncoded().get("role")[0]
                                     );
        return redirect("/conferences/pcmembers?conf_id=" + conf_id);
    }

    /**
     * Delete PC member
     */
    public Result deletePCMember(Long user_id) {
        Boolean deleted = Api.getInstance().deletePCMembers(user_id);
        return redirect("/conferences/pcmembers?conf_id=" + request().queryString().get("conf_id")[0]);
    }

    /**
     * Display admin page
     */
    public Result showEmailTemplates(Long conf_id) {
        List<EmailTemplate> templates = Arrays.asList(Api.getInstance().getConferenceTemplates(conf_id));

        return ok(views.html.conference.EmailTemplates.render(conf_id, templates, flash()));
    }

    public Result saveTemplate(Long conf_id) {
        Api.getInstance().saveTemplate(conf_id,
                                       Long.parseLong(request().body().asFormUrlEncoded().get("template_id")[0]),
                                       request().body().asFormUrlEncoded().get("content")[0]
                                      );
        return redirect("/conferences/templates?conf_id=" + conf_id);
    }

    public Result showReviewQuestion(Long conf_id) {
        List<ReviewQuestion> reviewQuestionList = Arrays.asList(Api.getInstance().getReviewQuestion(conf_id));
        return ok(views.html.conference.reviewQuestion.render(reviewQuestionList, conf_id, flash()));
    }

    public Result deleteReviewQuestion(Long id) {
        Boolean deleted = Api.getInstance().deleteReviewQuestion(id);
        return redirect("/conferences");
    }

    /**
     * Displays conference create page
     */
    public Result showReviewQuestionForm(Long conf_id) {
        Form reviewForm = formFactory.form(ReviewQuestion.class);
        return ok(views.html.conference.reviewQuestionForm.render(conf_id, reviewForm, flash()));
    }

    public Result createReviewQuestion(Long conf_id) {
        ReviewQuestion reviewQuestion = new ReviewQuestion();
        Form form = formFactory.form(ReviewQuestion.class);
        form = form.bindFromRequest(request());
        reviewQuestion = (ReviewQuestion) form.get();

        Api.getInstance().createReviewQuestion(conf_id, reviewQuestion);
        return redirect("/conferences");
    }
}