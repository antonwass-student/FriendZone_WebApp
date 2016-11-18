package bo;

import java.util.Collection;

/**
 * Created by Anton on 2016-11-18.
 */
public class StreamGetResponseBO {
    //list of posts
    //date of LAST/OLDEST post
    //number of posts?
    private Collection<WallPostBO> posts;

    public StreamGetResponseBO(String arg){}
    public StreamGetResponseBO(){}

    public Collection<WallPostBO> getPosts() {
        return posts;
    }

    public void setPosts(Collection<WallPostBO> posts) {
        this.posts = posts;
    }
}
