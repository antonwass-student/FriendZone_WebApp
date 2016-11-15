package entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Arrays;

/**
 * Created by Anton on 2016-11-15.
 */
@Entity
@Table(name = "Profile", schema = "dbo", catalog = "community")
public class ProfileEntity {
    private int profileId;
    private Date birthday;
    private String description;
    private byte[] picture;
    private UserEntity userByOwner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", nullable = false)
    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    @Basic
    @Column(name = "birthday", nullable = true)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 1024)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "picture", nullable = true)
    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfileEntity that = (ProfileEntity) o;

        if (profileId != that.profileId) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (!Arrays.equals(picture, that.picture)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = profileId;
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "user_id", nullable = false)
    public UserEntity getUserByOwner() {
        return userByOwner;
    }

    public void setUserByOwner(UserEntity userByOwner) {
        this.userByOwner = userByOwner;
    }
}
