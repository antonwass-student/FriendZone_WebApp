package entities;

import javax.persistence.*;

/**
 * Created by Anton on 2016-11-23.
 */
@Entity
@Table(name = "Usr", schema = "dbo", catalog = "community")
public class UsrEntity {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String sessionId;

    @Id
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

    @Basic
    @Column(name = "session_id", nullable = true, length = 128)
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsrEntity usrEntity = (UsrEntity) o;

        if (userId != usrEntity.userId) return false;
        if (name != null ? !name.equals(usrEntity.name) : usrEntity.name != null) return false;
        if (email != null ? !email.equals(usrEntity.email) : usrEntity.email != null) return false;
        if (password != null ? !password.equals(usrEntity.password) : usrEntity.password != null) return false;
        if (sessionId != null ? !sessionId.equals(usrEntity.sessionId) : usrEntity.sessionId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        return result;
    }
}
