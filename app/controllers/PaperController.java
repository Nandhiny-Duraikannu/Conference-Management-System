package controllers;

import lib.UserStorage;
import models.Conference;
import models.Paper;
import models.User;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
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
     * Retrieves Papers for My papers page Html
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

    public Result uploadPaper(Long id) {

        return ok("redirect to file upload form");
    }

    public Result editPaper(Long id) {
        return ok("redirect to paper submission form");
    }
}
            
