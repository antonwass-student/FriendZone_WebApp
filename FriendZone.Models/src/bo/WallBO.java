package bo;

import java.util.Collection;

/**
 * Created by Anton on 2016-11-17.
 */
public class WallBO {
    private int id;
    private UserSmallBO owner;
    private Collection<WallPostBO> posts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserSmallBO getOwner() {
        return owner;
    }

    public void setOwner(UserSmallBO owner) {
        this.owner = owner;
    }

    public Collection<WallPostBO> getPosts() {
        return posts;
    }

    public void setPosts(Collection<WallPostBO> posts) {
        this.posts = posts;
    }
}
