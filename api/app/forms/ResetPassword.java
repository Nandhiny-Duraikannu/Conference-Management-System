package forms;

import models.User;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Login form
 */
public class ResetPassword {
    private String name;
    private String securityQuestion;
    private String securityAnswer;

    public String getName() {
        return this.name;
    }

    public String getSecurityQuestion() {
        return this.securityQuestion;
    }

    public String getSecurityAnswer() {
        return this.securityAnswer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecurityQuestion(String securityQuestion) { this.securityQuestion = securityQuestion; }

    public void setSecurityAnswer(String securityAnswer) { this.securityAnswer = securityAnswer; }

}

