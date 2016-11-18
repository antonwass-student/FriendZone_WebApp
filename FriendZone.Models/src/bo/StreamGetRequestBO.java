package bo;

import java.util.Date;

/**
 * Created by Anton on 2016-11-18.
 */
public class StreamGetRequestBO {
    private String userSessionId;
    private Date postsFromDate;
    private int numOfPosts;

    public StreamGetRequestBO(String arg){}
    public StreamGetRequestBO(){}

    public String getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }

    public Date getPostsFromDate() {
        return postsFromDate;
    }

    public void setPostsFromDate(Date postsFromDate) {
        this.postsFromDate = postsFromDate;
    }

    public int getNumOfPosts() {
        return numOfPosts;
    }

    public void setNumOfPosts(int numOfPosts) {
        this.numOfPosts = numOfPosts;
    }
}
