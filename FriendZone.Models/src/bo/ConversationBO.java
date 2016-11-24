package bo;

import java.util.Collection;

/**
 * Created by Anton on 2016-11-23.
 */
public class ConversationBO {
    private Collection<UserSmallBO> members;
    private String title;

    public ConversationBO(String arg){}
    public ConversationBO(){}

    public Collection<UserSmallBO> getMembers() {
        return members;
    }

    public void setMembers(Collection<UserSmallBO> members) {
        this.members = members;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
