package controllers;

import lib.EmailHelper;
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
        Form<Paper> form = SavePaper(new Paper());

        if (form.hasErrors()) {

            return badRequest(form.errorsAsJson());
        } else {
            return created(Json.toJson((Paper) form.get()));
        }
    }

    /**
     * Creates paper via REST api
     */
    public Result update(Long id) {
        Paper paper = Paper.find.byId(id);
        Form<Paper> form = SavePaper(paper);
        System.out.println("update");
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        } else {
            return created(Json.toJson((Paper) form.get()));
        }
    }

    protected Form<Paper> SavePaper(Paper p) {
        Form<Paper> PaperForm = formFactory.form(Paper.class);
        Form submittedForm = PaperForm.bindFromRequest();

        if (!submittedForm.hasErrors()) {
            Paper paper = (Paper) submittedForm.get();

            if (p != null) {
                paper.id = p.id;
            }

            if (paper.conference.id != null) {
                System.out.println("paper save");
                if (paper.id != null) {
                    paper.update();

                    // TODO remove all authors from paper before saving new authors
                    //PaperAuthors.find.where().eq("paper.id", paper.id).delete();

                } else {
                    paper.save();
                }


                for (PaperAuthors a : paper.authors) {
                    a.paper = paper;
                    a.save();
                }
            }
        } else {
            System.out.println("errors at paper save");
            System.out.println(submittedForm.errorsAsJson().toString());
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
        String format = body.asFormUrlEncoded().get("format")[0];
        Paper paper = Paper.getById(id);
        if (file != null) {
            try {
                byte[] array = Files.readAllBytes(file.getFile().toPath());
                paper.upload(format, file.getFile().length(), array);
                User user = User.find.byId(paper.user.id);
                EmailHelper.sendEmail(user.email,
                                      "Successfull paper upload",
                                      user.name + ", congratulations! Your paper was uploaded. " +
                                      "You can download it <a href='http://localhost:9001/papers/download?id=" +
                                      paper.id + "'>here</a>");

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

    public Result getReviewers(Long id) {
        return ok(Json.toJson(Paper.getReviewers(id)));
    }
}
            
