package bo;

import java.util.Collection;

/**
 * Created by Anton on 2016-11-24.
 */
public class ConversationListBO {

    private Collection<ConversationBO> convos;

    public ConversationListBO(){}
    public ConversationListBO(String arg){}


    public Collection<ConversationBO> getConvos() {
        return convos;
    }

    public void setConvos(Collection<ConversationBO> convos) {
        this.convos = convos;
    }
}
