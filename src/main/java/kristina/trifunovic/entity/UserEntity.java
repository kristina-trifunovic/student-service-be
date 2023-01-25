package kristina.trifunovic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "UK_Email", columnNames = "email"))
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity implements Entity{
    @Id
    @Column
    @NotNull(message = "Username is required")
    @Size(min = 4, message = "Username must be at least 4 characters")
    protected String username;
    @Column
    @NotNull(message = "Password is required")
    protected String password;
    @Column
    @NotNull(message = "First name is required")
    @Size(min = 3, max = 30, message = "First name should be between 3 and 30 characters")
    protected String firstName;
    @Column
    @NotNull(message = "Last name is required")
    @Size(min = 3, max = 30, message = "Last name should be between 3 and 30 characters")
    protected String lastName;
    @Column
    @Email(message = "Not a valid email")
    @NotNull(message = "Email is required")
    @Size(max = 30, message = "Email should be maximum 30 characters")
    protected String email;
    @Column
    @Size(min = 3, max = 50, message = "Address should be between 3 and 50 characters")
    protected String address;

    @ManyToOne
    @JoinColumn(name = "city", foreignKey = @ForeignKey(name = "FK_User_City"))
    protected CityEntity city;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    protected List<AuthoritiesEntity> authorities;
    public UserEntity() {
    }

    public UserEntity(String username, String password, String firstName, String lastName, String email, String address, CityEntity city) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.city = city;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }

    @JsonIgnore
    public List<AuthoritiesEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<AuthoritiesEntity> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(authorities, that.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, username, password, authorities);
    }
}