package controllers;

import lib.UserStorage;
import lib.Api;

import models.Paper;
import models.User;
import play.mvc.Http.MultipartFormData;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import javax.inject.Inject;

import play.Logger;
import play.data.validation.ValidationError;
import play.data.*;

import forms.PaperSubmission.*;
import models.*;
import forms.PaperSubmission;
import play.mvc.WebSocket;
//import views.html.paper.PaperForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides web and api endpoints for Submitted Paper Management
 */
public class PaperController extends Controller {

    private FormFactory formFactory;

    @Inject
    public PaperController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    /**
     * PaperSubmission  page
     */
    public Result showPaperSubmissionForm() {
        return ok(views.html.paper.PaperForm.render(null, formFactory.form(PaperSubmission.class), flash()));
    }

    /**
     * PostSubmission  page
     */
    public Result postSubmission() {
        Logger.debug("post submission redirect");
        return ok(views.html.paper.postSubmission.render(formFactory.form(PaperSubmission.class), flash()));
    }

    /**
     * Creates paper
     */
    public Result create() {
        Form<PaperSubmission> form = SavePaper(null);

        if (form.hasErrors()) {
            return badRequest(views.html.paper.PaperForm.render(null, form, flash()));
        } else {
            return redirect("/postSubmission");
        }
    }

    /**
     * Update paper
     */
    public Result update(Long id) {
        Form<PaperSubmission> form = SavePaper(id);

        if (form.hasErrors()) {
            return badRequest(views.html.paper.PaperForm.render(null, form, flash()));
        } else {
            return redirect("/papers");
        }
    }

    protected Form SavePaper(Long id) {
        Form form = formFactory.form(PaperSubmission.class).bindFromRequest();

        if (!form.hasErrors()) {
            Map<String, String> data = form.data();
            data.put("user.id", UserStorage.getCurrentUser().getId().toString());
            boolean success = false;

            if (id == null) {
                success = Api.getInstance().InsertPaper(data);
            } else {
                success = Api.getInstance().UpdatePaper(id, data);
            }

            if (!success) {
                form.globalErrors().add(0,
                                        new ValidationError("Paper", "Paper not submitted"));
            }
        }

        return form;
    }

    /**
     * Retrieves Papers for My papers page
     */
    public Result getPapers() {
        String param = request().getQueryString("conf_id");
        int conf_id = 0;
        if (param != null && !param.isEmpty()) {
            conf_id = Integer.parseInt(param);
        }
        User user = UserStorage.getCurrentUser();
        if (user == null) {
            System.out.println("User not authorized");
            return redirect(routes.HomeController.index());
        } else {
            List<Paper> papers = new ArrayList<Paper>(Arrays.asList(
                    Api.getInstance().getByAuthorAndConference(user.id, conf_id)
                                                                   ));
            return ok(views.html.paper.myPapersPage.render(papers, conf_id, flash()));
        }
    }

    /**
     * upload file to database
     */
    public Result uploadPaper(Long id) {
        MultipartFormData<File> body = request().body().asMultipartFormData();
        MultipartFormData.FilePart<File> file = body.getFile("file");

        Map<String, String> data = new HashMap<String, String>();
        if (file != null) {
            try {
                boolean uploaded = Api.getInstance().uploadPaper(id,
                                                                 file.getFile(),
                                                                 getFileExtension(file.getFilename()));
                return redirect(routes.PaperController.getPapers());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            flash("error", "Missing file");
        }
        return badRequest();
    }

    /**
     *
     * download file from database
     */
    public Result downloadPaper(Long id) {
        Paper paper = Api.getInstance().getPaperById(id);
        Result r = ok(paper.fileContent);
        response().setHeader("Content-Disposition", "attachment; filename=paper" + paper.id + "." + paper.fileFormat);
        return r;
    }

    /**
     * helper function for getting file extension
     */
    private static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    /**
     * redirect to paper edit (submission) page
     */
    public Result editPaper(Long id) {
        Paper paper = Api.getInstance().getPaperById(id);
        Form paperForm = formFactory.form(PaperSubmission.class);
        paperForm = paperForm.fill(paper);
        return ok(views.html.paper.PaperForm.render(id, paperForm, flash()));
    }

    public Result authorList() {
        List<Paper> papers = new ArrayList<Paper>(Arrays.asList(
                Api.getInstance().getPapers()
        ));

        return ok(views.html.conference.authorList.render(papers, flash()));
    }
}
            
