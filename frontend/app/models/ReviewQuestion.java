package models;

import lib.Api;
import models.Conference;
import lib.UserStorage;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Paper review
 */

public class ReviewQuestion {
    private static final long serialVersionUID = 1L;

    public Long id;

    public Conference conference;

    public String question;

    public String is_public;

    public String choice1;

    public String position1;

    public String choice2;

    public String position2;

    public String choice3;

    public String position3;

    public String choice4;

    public String position4;

    public void setQuestion(String question) { this.question = question; }
    public void setIs_public(String is_public) { this.is_public = is_public; }
    public void setPosition1(String position1) { this.position1 = position1; }
    public void setChoice1(String choice1) { this.choice1 = choice1; }
    public void setPosition2(String position2) { this.position2 = position2; }
    public void setChoice2(String choice2) { this.choice2 = choice2; }
    public void setPosition3(String position3) { this.position3 = position3; }
    public void setChoice3(String choice3) { this.choice3 = choice3; }
    public void setPosition4(String position4) { this.position4 = position4; }
    public void setChoice4(String choice4) { this.choice4 = choice4; }

}
