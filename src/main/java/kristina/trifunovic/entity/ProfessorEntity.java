package kristina.trifunovic.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "professor")
public class ProfessorEntity extends UserEntity {
    @Column
    @Size(min = 9, max = 15, message = "Phone should be between 9 and 15 characters")
    @Pattern(regexp = "\\d+", message = "Phone should be only numbers")
    private String phone;

    @Column
    @NotNull(message = "Reelection date is required")
    @Type(type="date")
    @JsonFormat(pattern="dd.MM.yyyy")
    private Date reelectionDate;
    @OneToOne
    @JoinColumn(name = "title", foreignKey = @ForeignKey(name = "FK_Professor_Title"))
    @NotNull(message = "Title is required")
    private TitleEntity title;

    @ManyToMany()
    @JoinTable(
            name = "professor_teaches",
            foreignKey = @ForeignKey(name = "FK_ProfessorTeaches_Professor"),
            joinColumns = @JoinColumn(name = "professor", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "subject", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ProfessorTeaches_Subject"))
    )
    private Set<SubjectEntity> subjects;

    @OneToMany(mappedBy = "professor")
    @JsonIgnore
    private Set<ExamEntity> exams;

    public ProfessorEntity() {}

    public ProfessorEntity(String username, String password, String firstName, String lastName, String address, String email, CityEntity city, String phone, Date reelectionDate, TitleEntity title) {
        super(username, password, firstName, lastName, email, address, city);
        this.phone = phone;
        this.reelectionDate = reelectionDate;
        this.title = title;
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @Override
    public void setAddress(String address) {
        super.setAddress(address);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public CityEntity getCity() {
        return super.getCity();
    }

    @Override
    public void setCity(CityEntity city) {
        super.setCity(city);
    }

    public Set<SubjectEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SubjectEntity> subjects) {
        this.subjects = subjects;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getReelectionDate() {
        return reelectionDate;
    }

    public void setReelectionDate(Date reelectionDate) {
        this.reelectionDate = reelectionDate;
    }

    public TitleEntity getTitle() {
        return title;
    }

    public void setTitle(TitleEntity title) {
        this.title = title;
    }

    public void addSubject(SubjectEntity subject) {
        subjects.add(subject);
    }

    @Override
    public String toString() {
        return "ProfessorEntity{" +
                "phone='" + phone + '\'' +
                ", reelectionDate=" + reelectionDate +
                ", title=" + title +
                ", subjects=" + subjects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProfessorEntity professor = (ProfessorEntity) o;
        return Objects.equals(phone, professor.phone) && Objects.equals(reelectionDate, professor.reelectionDate) && Objects.equals(title, professor.title) && Objects.equals(subjects, professor.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phone, reelectionDate, title, subjects);
    }
}
