package entities;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Anton on 2016-11-15.
 */
@Entity
@Table(name = "Wall", schema = "dbo", catalog = "community")
public class WallEntity {
    private int wallId;
    private UserEntity userByOwner;
    private Collection<WallPostEntity> wallPostsByWallId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wall_id", nullable = false)
    public int getWallId() {
        return wallId;
    }

    public void setWallId(int wallId) {
        this.wallId = wallId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WallEntity that = (WallEntity) o;

        if (wallId != that.wallId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return wallId;
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
