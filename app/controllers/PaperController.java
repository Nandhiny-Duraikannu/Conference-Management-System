package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import forms.Login;
import javafx.print.*;
import models.Paper;
import models.PaperAuthors;
import models.User;
import org.h2.engine.*;
import play.Logger;
import play.data.validation.ValidationError;
import play.libs.Json;
import play.mvc.*;
import play.data.*;

import forms.PaperSubmission.*;
import static lib.UserStorage.getCurrentUser;
import static play.data.Form.*;
import views.*;
import models.*;
import forms.PaperSubmission;
import views.html.paper.PaperForm;
import lib.UserStorage;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Provides web and api endpoints for PaperSubmission Submission actions
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
       return ok(views.html.paper.PaperForm.render(formFactory.form(PaperSubmission.class), flash()));
    }

    /**
     * PostSubmission  page
    */
    public Result postSubmission() {
        Logger.debug("post submission redirect");
        return ok(views.html.paper.postSubmission.render(formFactory.form(PaperSubmission.class), flash()));
    }

    /**
     * Creates user via REST api
     */
    public Result create() {


        Form formA = savePaper();
        Form formB = SaveAuthors();
        Logger.debug("in controller create");
        if (formA.hasErrors() ) {
            Logger.debug("formA has errors");
            return badRequest(formA.errorsAsJson());
        }
        else if(formB.hasErrors() ){
            Logger.debug("formB has errors");
            return badRequest(formB.errorsAsJson());
        }

        else {
            Logger.debug("form has no errors");
         //  return created(Json.toJson((Paper) form.get()));
            return  redirect("/postSubmission");
        }

    }

    protected Form savePaper() {
      //  String user_id = String.valueOf(getCurrentUser());
      // User user_id = UserStorage.getCurrentUser();

        Form PaperForm = formFactory.form(Paper.class);

        Form submittedForm = PaperForm.bindFromRequest("title","contactEmail","confirmEmail","user_id","awardCandidate","studentVolunteer","paperAbstract","topic","conferenceID");
        Logger.debug("in controller save");
        if (!submittedForm.hasErrors()) {
            Paper paper = (Paper) submittedForm.get();
            if (paper.contactEmail != paper.confirmEmail ) {
                List errors = new ArrayList();
                errors.add(new ValidationError("confirmEmail", "Contact email and Confirm email are different"));

            }
            else {
                Logger.debug("in save has no errors" + paper.user_id);
                //  user_id = Paper.save(UserStorage.getCurrentUser());
                paper.save();

            }
       }

        return submittedForm;
    }

    protected Form SaveAuthors() {

        Form AuthorForm = formFactory.form(PaperAuthors.class);

            Form submittedForm = AuthorForm.bindFromRequest("author_first_name", "user_id", "author_last_name", "author_affiliation", "author_email", "type");
            Logger.debug("in controller save author");
            if (!submittedForm.hasErrors()) {
                PaperAuthors authors = (PaperAuthors) submittedForm.get();
                Logger.debug("in save has no errors");
                authors.save();
            }

            return submittedForm;

    }
/*
    public List getValidation() {
        //    Paper paper = Paper.getConfirmEmailValidation(getContactEmail(), getConfirmEmail());
        Logger.debug("validate email");
        if (paper. != confirmEmail) {
            List errors = new ArrayList();
            errors.add(new ValidationError("confirmEmail", "Contact email and Confirm email are different"));
            return errors;
        }

        return null;
    }*/

}
