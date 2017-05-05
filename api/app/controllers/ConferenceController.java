package controllers;

import json.ConferenceReviewer;
import lib.EmailHelper;
import models.*;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static play.data.Form.form;

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
     * Create conference
     */
    public Result create() {
        Conference conf = save(new Conference());
        return created(Json.toJson(conf));
    }

    /**
     * Edit conference
     */
    public Result update(Long id) {
        Conference conf = Conference.find.byId(id);

        if (conf == null) {
            return notFound();
        }

        conf = save(conf);

        return ok(Json.toJson(conf));
    }

    protected Conference save(Conference model) {
        Http.MultipartFormData data = request().body().asMultipartFormData();
        Map<String, String[]> params = null;
        Http.MultipartFormData.FilePart<File> logo = null;

        if (data != null) {
            logo = data.getFile("logo");
            params = data.asFormUrlEncoded();
        } else {
            params = request().body().asFormUrlEncoded();
        }

        if (logo != null) {
            String fileName = logo.getFilename();
            File file = logo.getFile();

            if (file != null) {
                try {
                    String dir = "public/uploads";
                    file.renameTo(new File(dir, fileName));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                model.logo = fileName;
            }
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        if (params != null && params.size() > 0) {
            try {
                model.acronym = params.get("acronym")[0];
                model.deadline = df.parse(params.get("deadline")[0]);
                model.submissionDateStart = df.parse(params.get("submissionDateStart")[0]);
                model.location = params.get("acronym")[0];
                model.title = params.get("title")[0];
                model.status = params.get("status")[0];
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        model.save();

        EmailTemplate.addDefaultToConference(model);

        return model;
    }

    public Result getById(Long id) {
        Conference conf = Conference.find.byId(id);
        return ok(Json.toJson(conf));
    }

    public Result getAllConferences() {
        List<Conference> conf = Conference.getAllConferences();
        return ok(Json.toJson(conf));
    }

    // TODO: Make this work with getAllConferences();
    public Result getAllConferencesByKeyword(String keyword, String searchbox) {
        List<Conference> conf = Conference.getAllConferencesByKeyword(keyword, searchbox);
        return ok(Json.toJson(conf));
    }

    public Result getConferencesByUser(Long user_id) {
        List<Conference> conf = Conference.getConferencesByUser(user_id);
        return ok(Json.toJson(conf));
    }

    // Returns reviewers and papers they need to review for a conference
    public Result getConferenceReviewers(Long confId) {
        Conference conf = Conference.find.byId(confId);
        return ok(Json.toJson(conf.getReviewers()));
    }

    // Send notifications to those reviewers who have papers to review
    public Result notifyReviewers(Long confId) {
        Conference conf = Conference.find.byId(confId);
        List<ConferenceReviewer> reviewers = conf.getReviewers();
        ArrayList<String> emails = new ArrayList<>();

        for (ConferenceReviewer r: reviewers) {
            if (r.notReviewedPapers.size() > 0) {
                emails.add(r.reviewerEmail);
            }
        }

        EmailTemplate template = EmailTemplate.getByNameAndConf("Reviewer Reminder Template", confId);

        if (emails.size() > 0) {
            EmailHelper.sendTemplateToEmails(emails, template);
        }

        return ok(Json.toJson(emails));
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

    public Result getPCMembers(Long conf_id) {
        return ok(Json.toJson(PCMember.getByConfId(conf_id)));
    }

    public Result addPCMember(Long conf_id) {
        PCMember member = new PCMember();
        Http.MultipartFormData data = request().body().asMultipartFormData();
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        System.out.println(params);
        member.conference = new Conference();
        member.conference.id = conf_id;
        member.user = new User();
        member.user.id =  Long.parseLong(params.get("id")[0]);
        member.role = params.get("role")[0];
        if(member.conference.id!=null && member.user.id !=null && member.role!= null){
            member.save();
            return created();
        } else {
            return badRequest();
        }
    }

    public Result deletePCMember(Long id) {
        PCMember member = PCMember.find.byId(id);

        if (member == null) {
            return notFound();
        }

        member.delete();

        return ok();
    }

    public Result getEmailTemplates(Long conf_id) {
        return ok(Json.toJson(EmailTemplate.getByConfId(conf_id)));
    }

    public Result addEmailTemplate() {
        Form signupForm = formFactory.form(Review.class);
        Form submittedForm = signupForm.bindFromRequest();

        if (!submittedForm.hasErrors()) {
            EmailTemplate template = (EmailTemplate) submittedForm.get();
            template.save();
            return created();
        } else {
            return badRequest(submittedForm.errorsAsJson());
        }
    }

    public Result updateEmailTemplate(Long conf_id,Long id) {

        EmailTemplate template = EmailTemplate.find.byId(id);

        if (template == null) {
            return notFound();
        }

        template.setContent(request().body().asFormUrlEncoded().get("content")[0]);
        template.update();

        return ok();
    }

    public Result addReviewQuestion(Long conf_id) {
        ReviewQuestion reviewQuestion = new ReviewQuestion();
        Http.MultipartFormData data = request().body().asMultipartFormData();
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        reviewQuestion.conference = new Conference();
        reviewQuestion.conference.id = conf_id;
        reviewQuestion.question = params.get("question")[0];
        reviewQuestion.is_public = params.get("is_public")[0];
        reviewQuestion.choice1 = params.get("choice1")[0];
        reviewQuestion.position1 = params.get("position1")[0];
        reviewQuestion.choice2 = params.get("choice2")[0];
        reviewQuestion.position2 = params.get("position2")[0];
        reviewQuestion.choice3 = params.get("choice3")[0];
        reviewQuestion.position3 = params.get("position3")[0];
        reviewQuestion.choice4 = params.get("choice4")[0];
        reviewQuestion.position4 = params.get("position4")[0];

        reviewQuestion.save();
        return created();
    }

    public Result updateReviewQuestion(Long id) {

        ReviewQuestion reviewQuestion = ReviewQuestion.find.byId(id);
        Http.MultipartFormData data = request().body().asMultipartFormData();
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        reviewQuestion.setQuestion(params.get("question")[0]);
        reviewQuestion.setIs_public(params.get("is_public")[0]);
        reviewQuestion.setPosition1(params.get("position1")[0]);
        reviewQuestion.setChoice1(params.get("choice1")[0]);
        reviewQuestion.setPosition2(params.get("position2")[0]);
        reviewQuestion.setChoice2(params.get("choice2")[0]);
        reviewQuestion.setPosition3(params.get("position3")[0]);
        reviewQuestion.setChoice3(params.get("choice3")[0]);
        reviewQuestion.setPosition4(params.get("position4")[0]);
        reviewQuestion.setChoice4(params.get("choice4")[0]);


        reviewQuestion.update();

        return ok();
    }

    public Result deleteReviewQuestion(Long id) {
        ReviewQuestion reviewQuestion = ReviewQuestion.find.byId(id);

        reviewQuestion.delete();

        return ok();
    }

    public Result getReviewQuestion(Long conf_id) {
        return ok(Json.toJson(ReviewQuestion.getByConfId(conf_id)));
    }
}
            
