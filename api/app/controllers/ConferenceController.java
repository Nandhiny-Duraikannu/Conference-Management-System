package controllers;

import models.Conference;
import models.EmailTemplate;
import models.PCMember;
import models.Review;
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

    public Result addPCMember() {
        Form signupForm = formFactory.form(PCMember.class);
        Form submittedForm = signupForm.bindFromRequest();

        if (!submittedForm.hasErrors()) {
            PCMember pcMember = (PCMember) submittedForm.get();
            pcMember.save();
            return created();
        } else {
            return badRequest(submittedForm.errorsAsJson());
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

    public Result updateEmailTemplate(Long id) {

        EmailTemplate template = EmailTemplate.find.byId(id);

        if (template == null) {
            return notFound();
        }

        template.setContent(request().body().asFormUrlEncoded().get("content")[0]);
        template.update();

        return ok();
    }
}
            
