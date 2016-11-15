package models;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Anton on 2016-11-15.
 */
@Entity
@Table(name = "Wall", schema = "dbo", catalog = "community")
public class WallEntity {
    private Integer wallId;
    private Integer owner;
    private UserEntity userByOwner;
    private Collection<WallPostEntity> wallPostsByWallId;

    @Id
    @Column(name = "wall_id", nullable = false)
    public Integer getWallId() {
        return wallId;
    }

    public void setWallId(Integer wallId) {
        this.wallId = wallId;
    }

    @Basic
    @Column(name = "owner", nullable = false)
    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WallEntity that = (WallEntity) o;

        if (wallId != null ? !wallId.equals(that.wallId) : that.wallId != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wallId != null ? wallId.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
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

    @OneToMany(mappedBy = "wallByWall")
    public Collection<WallPostEntity> getWallPostsByWallId() {
        return wallPostsByWallId;
    }

    public void setWallPostsByWallId(Collection<WallPostEntity> wallPostsByWallId) {
        this.wallPostsByWallId = wallPostsByWallId;
    }
}
