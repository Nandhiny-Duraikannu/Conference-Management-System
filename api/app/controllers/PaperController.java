package controllers;

import lib.UserStorage;
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

import play.Logger;
import play.data.validation.ValidationError;
import play.data.*;

import models.*;


import javax.inject.Inject;
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
     * Creates paper via REST api
     */
    public Result create() {
        Form formA = savePaper();
        Form formB = SaveAuthors();
        Logger.debug("in controller create");
        if (formA.hasErrors()) {
            Logger.debug("formA has errors");
            return badRequest(formA.errorsAsJson());
        } else if (formB.hasErrors()) {
            Logger.debug("formB has errors");
            return badRequest(formB.errorsAsJson());
        } else {
            Logger.debug("form has no errors");
            return created(Json.toJson((Paper) formA.get()));
        }
    }

    protected Form savePaper() {
        long user_id = UserStorage.getCurrentUser().id;

        Form PaperForm = formFactory.form(Paper.class);

        Form submittedForm = PaperForm.bindFromRequest(
                "title", "contactEmail", "user_id", "confirmEmail", "awardCandidate",
                "studentVolunteer", "paperAbstract", "topic", "conferenceID");
        Logger.debug("in controller save");
        if (!submittedForm.hasErrors()) {
            Paper paper = (Paper) submittedForm.get();

            if (!paper.contactEmail.equals(paper.confirmEmail)) {
                List errors = new ArrayList();
                errors.add(new ValidationError("confirmEmail", "Contact email and Confirm email are different"));

            } else {
                Logger.debug("in save has no errors" + paper.user.id);
                //  user_id = Paper.save(UserStorage.getCurrentUser());
                paper.save();

            }
        }

        return submittedForm;
    }

    protected Form SaveAuthors() {
        Form AuthorForm = formFactory.form(PaperAuthors.class);

        Form submittedForm = AuthorForm.bindFromRequest("author_first_name",
                                                        "user_id",
                                                        "author_last_name",
                                                        "author_affiliation",
                                                        "author_email",
                                                        "type");
        Logger.debug("in controller save author");
        if (!submittedForm.hasErrors()) {
            PaperAuthors authors = (PaperAuthors) submittedForm.get();
            Logger.debug("in save has no errors");
            authors.save();
        }

        return submittedForm;

    }

    /**
     * Retrieves Papers by Conference ID - api
     */
    public Result getPapersByConf(Long user_id, int id) {
        String param = request().getQueryString("user_id");
        List<Paper> papers = new ArrayList<Paper>();
        papers = Paper.getByAuthorAndConference(user_id, id);
        return ok(Json.toJson(papers));
    }

    /**
     * Retrieves Papers for My papers page - api
     */
    public Result getPaper(Long id) {
        Paper paper = Paper.getById(id);
        return ok(Json.toJson(paper));
    }

    /**
     * Retrieves All Papers - api
     */
    public Result getAllPapers() {
        String param = request().getQueryString("user_id");
        List<Paper> papers = new ArrayList<Paper>();
        if (param != null && !param.isEmpty()) {
            papers = Paper.getByAuthor(Long.parseLong(param));
        } else {
            papers = Paper.getAllPapers();
        }

        return ok(Json.toJson(papers));
    }

    /**
     * Find paper's authors and return them as string - api
     */
    public Result getAuthors(Long id) {
        return ok(Json.toJson(Paper.getAuthors(id)));
    }

    /**
     * upload file to database
     */
    public Result uploadPaper(Long id) {
        MultipartFormData<File> body = request().body().asMultipartFormData();
        MultipartFormData.FilePart<File> file = body.getFile("file");
        Paper paper = Paper.getById(id);
        if (file != null) {
            try {
                byte[] array = Files.readAllBytes(file.getFile().toPath());
                paper.upload(getFileExtension(file.getFilename()), file.getFile().length(), array);
                return ok();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return badRequest();
        }
        return badRequest();
    }

    /**
     * download file from database
     */
    public Result downloadPaper(Long id) {
        Paper paper = Paper.getById(id);
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
}
            
