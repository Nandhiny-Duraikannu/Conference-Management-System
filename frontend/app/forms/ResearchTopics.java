package forms;

import models.ResearchPaper;

import play.Logger;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandh on 4/27/2017.
 */
public class ResearchTopics {
    private String research_topics;
    private List all_research_topics;


    public String getResearchTopics() {
        return this.research_topics;
    }
    public String setResearchTopics() {
        return this.research_topics= research_topics;
    }
    public List<ResearchPaper> getResearchPaper() {
        Logger.debug("list form researchtopic" );
        all_research_topics = ResearchPaper.getResearchPaper();
        Logger.debug("list form researchtopic"+all_research_topics );
        return all_research_topics;
    }
}
