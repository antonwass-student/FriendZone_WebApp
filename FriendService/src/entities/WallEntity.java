package entities;

import javax.persistence.*;

/**
 * Created by Anton on 2016-11-10.
 */
@Entity
@Table(name = "Wall", schema = "dbo", catalog = "community")
public class WallEntity {
    private Integer wallId;
    private UserEntity userByOwner;

    @Id
    @Column(name = "wall_id", nullable = false)
    public Integer getWallId() {
        return wallId;
    }

    public void setWallId(Integer wallId) {
        this.wallId = wallId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WallEntity that = (WallEntity) o;

        if (wallId != null ? !wallId.equals(that.wallId) : that.wallId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return wallId != null ? wallId.hashCode() : 0;
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
