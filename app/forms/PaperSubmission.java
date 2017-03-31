package forms;

import models.Paper;
import models.PaperAuthors;
import models.User;
import play.Logger;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;
public class PaperSubmission {

    private String title;
    private  String contactEmail;
    private  String confirmEmail;
    private String awardCandidate;
    private String studentVolunteer;
    private String paperAbstract;
    private String topic;
    private String conferenceID;

    private String author_first_name;
    private String author_last_name;
    private String author_affiliation;
    private String author_email;
    private String type;

    public List getValidation() {
    //    Paper paper = Paper.getConfirmEmailValidation(getContactEmail(), getConfirmEmail());
        Logger.debug("validate email");
        if (contactEmail != confirmEmail) {
            List errors = new ArrayList();
            errors.add(new ValidationError("confirmEmail", "Contact email and Confirm email are different"));
            return errors;
        }

        return null;
    }




    public String getTitle() {
        return this.title;

    }
    public String getContactEmail() {
        return this.contactEmail;
    }
    public String getConfirmEmail() {
        return this.confirmEmail;
    }
    public String getAwardCandidate() {
        return this.awardCandidate;
    }
    public String getVolunteer() {
        return this.studentVolunteer;
    }
    public String getAbstract() {
        return this.paperAbstract;
    }
    public String getTopic() {
        return this.topic;
    }
    public String getconferenceID() {
        return this.conferenceID;
    }

    public String getAuthorFirstName() {
        return this.author_first_name;
    }
    public String getaAthorLastName() {
        return this.author_last_name;
    }
    public String getAuthorAffiliation() {
        return this.author_affiliation;
    }
    public String getAuthorEmail() {
        return this.author_email;
    }
    public String getAuthorType() {
        return this.type;
    }

}
