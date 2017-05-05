package forms;

import controllers.AdminController;
import models.ResearchPaper;
import java.util.List;
import play.Logger;
/**
 * Created by nandh on 4/28/2017.
 */
public class ResearchTopic {

    private String research_topic;
    public int num;
    public String setTopic() {

        Logger.debug("in forms api");
        research_topic = "newtopic1";

        return this.research_topic=research_topic;

    }
}
