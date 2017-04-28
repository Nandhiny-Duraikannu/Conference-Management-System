package controllers;

import models.Conference;
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
        save(new Conference());
        return created();
    }

    /**
     * Edit conference
     */
    public Result update(Long id) {
        Conference conf = Conference.find.byId(id);

        if (conf == null) {
            return notFound();
        }

        save(conf);

        return ok();
    }

    protected void save(Conference model) {
        Http.MultipartFormData data = request().body().asMultipartFormData();
        Map<String, String[]> params = data.asFormUrlEncoded();
        Http.MultipartFormData.FilePart<File> logo = data.getFile("logo");

        if (logo != null) {
            String fileName = logo.getFilename();
            File file = logo.getFile();

            try {
                String dir = "public/uploads";
                file.renameTo(new File(dir, fileName));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            model.logo = fileName;
        } else {
            model.logo = null;
        }

        SimpleDateFormat df = new SimpleDateFormat("YYYY-mm-dd");

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

        model.save();
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
}
            
