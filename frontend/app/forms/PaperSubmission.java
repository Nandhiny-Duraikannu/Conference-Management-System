package forms;

import models.Paper;
import models.PaperAuthors;
import models.User;
import play.Logger;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class PaperSubmission extends Paper {
    public List validate() {
        if (this.contactEmail != null && this.confirmEmail != null && !this.contactEmail.equals(this.confirmEmail)) {
            List errors = new ArrayList();
            errors.add(new ValidationError("confirmEmail", "Emails do not match"));
            return errors;
        }

        return null;
    }
}