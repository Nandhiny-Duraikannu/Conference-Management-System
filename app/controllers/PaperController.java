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
import java.util.*;

import javax.inject.Inject;

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
     * Retrieves Papers for My papers page - web
     */
    public Result getPapers() {
        String param = request().getQueryString("conf_id");
        int conf_id = 0;
        if (param != null && !param.isEmpty()){
            conf_id = Integer.parseInt(param);
        }
        User user = UserStorage.getCurrentUser();
        if (user == null){
            System.out.println("User not authorized");
            return redirect(routes.HomeController.index());
        }
        else {
            List<Paper> papers = Paper.getByAuthorAndConference(user.id, conf_id);
            return ok(views.html.paper.myPapersPage.render(papers, conf_id, flash()));
        }
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
        if (param != null && !param.isEmpty()){
            papers = Paper.getByAuthor(Long.parseLong(param));
        }else{
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
     * upload file to database - web
     */
    public Result uploadPaper(Long id) {
        MultipartFormData<File> body = request().body().asMultipartFormData();
        MultipartFormData.FilePart<File> file = body.getFile("file");
        Paper paper = Paper.getById(id);
        if (file != null) {
            try {
                byte[] array = Files.readAllBytes(file.getFile().toPath());
                paper.upload(getFileExtension(file.getFilename()), file.getFile().length(), array);
                return redirect(routes.PaperController.getPapers());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            flash("error", "Missing file");
        }
        return badRequest();
    }

    /**
     * download file from database - web
     */
    public Result downloadPaper(Long id){
        Paper paper = Paper.getById(id);
        Result r = ok(paper.fileContent);
        response().setHeader("Content-Disposition", "attachment; filename=paper"+paper.id+"."+paper.fileFormat);
        return r;
    }

    /**
     * helper function for getting file extension
     */
    private static String getFileExtension(String fileName) {
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    /**
     * redirect to paper edit (submission) page - web
     * IMPLEMENT
     */
    public Result editPaper(Long id) {
        return ok("redirect to paper submission form");
    }
}
            
