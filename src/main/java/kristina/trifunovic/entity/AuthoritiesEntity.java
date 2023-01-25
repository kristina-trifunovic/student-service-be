package kristina.trifunovic.entity;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "authorities")
public class AuthoritiesEntity implements kristina.trifunovic.entity.Entity {
    @Id
    @NotNull(message = "User is required")
    @ManyToOne
    @JoinColumn(name = "username", foreignKey = @ForeignKey(name = "FK_User_Authorities"))
    private UserEntity user;

    @Id
    @NotBlank(message = "Authority is required")
    private String authority;

    public AuthoritiesEntity(UserEntity username, String authority) {
        this.user = username;
        this.authority = authority;
    }

    public AuthoritiesEntity() {
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity username) {
        this.user = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "AuthoritiesEntity{" +
                "username=" + user +
                ", authority='" + authority + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthoritiesEntity that = (AuthoritiesEntity) o;
        return Objects.equals(user, that.user) && Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, authority);
    }
}
