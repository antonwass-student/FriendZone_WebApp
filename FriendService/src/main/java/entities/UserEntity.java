package main.java.entities;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Anton on 2016-11-15.
 */
@Entity
@Table(name = "Usr", schema = "dbo", catalog = "community")
public class UserEntity {
    private int userId;
    private String name;
    private String email;
    private String password;
    private Collection<FriendRequestEntity> friendRequestsByUserId;
    private Collection<FriendRequestEntity> friendRequestsByUserId_0;
    private Collection<FriendshipEntity> friendshipsByUserId;
    private Collection<FriendshipEntity> friendshipsByUserId_0;
    private Collection<ProfileEntity> profilesByUserId;
    private Collection<WallEntity> wallsByUserId;
    private Collection<WallPostEntity> wallPostsByUserId;

    public UserEntity(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 128)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 512)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (userId != that.userId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userBySender")
    public Collection<FriendRequestEntity> getFriendRequestsByUserId() {
        return friendRequestsByUserId;
    }

    public void setFriendRequestsByUserId(Collection<FriendRequestEntity> friendRequestsByUserId) {
        this.friendRequestsByUserId = friendRequestsByUserId;
    }

    @OneToMany(mappedBy = "userByReceiver")
    public Collection<FriendRequestEntity> getFriendRequestsByUserId_0() {
        return friendRequestsByUserId_0;
    }

    public void setFriendRequestsByUserId_0(Collection<FriendRequestEntity> friendRequestsByUserId_0) {
        this.friendRequestsByUserId_0 = friendRequestsByUserId_0;
    }

    @OneToMany(mappedBy = "userByInviter")
    public Collection<FriendshipEntity> getFriendshipsByUserId() {
        return friendshipsByUserId;
    }

    public void setFriendshipsByUserId(Collection<FriendshipEntity> friendshipsByUserId) {
        this.friendshipsByUserId = friendshipsByUserId;
    }

    @OneToMany(mappedBy = "userByReceiver")
    public Collection<FriendshipEntity> getFriendshipsByUserId_0() {
        return friendshipsByUserId_0;
    }

    public void setFriendshipsByUserId_0(Collection<FriendshipEntity> friendshipsByUserId_0) {
        this.friendshipsByUserId_0 = friendshipsByUserId_0;
    }

    @OneToMany(mappedBy = "userByOwner")
    public Collection<ProfileEntity> getProfilesByUserId() {
        return profilesByUserId;
    }

    public void setProfilesByUserId(Collection<ProfileEntity> profilesByUserId) {
        this.profilesByUserId = profilesByUserId;
    }

    @OneToMany(mappedBy = "userByOwner")
    public Collection<WallEntity> getWallsByUserId() {
        return wallsByUserId;
    }

    public void setWallsByUserId(Collection<WallEntity> wallsByUserId) {
        this.wallsByUserId = wallsByUserId;
    }

    @OneToMany(mappedBy = "userByAuthor")
    public Collection<WallPostEntity> getWallPostsByUserId() {
        return wallPostsByUserId;
    }

    public void setWallPostsByUserId(Collection<WallPostEntity> wallPostsByUserId) {
        this.wallPostsByUserId = wallPostsByUserId;
    }
}
