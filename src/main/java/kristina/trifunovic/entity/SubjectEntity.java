package kristina.trifunovic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kristina.trifunovic.enums.Semester;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "subject")
public class SubjectEntity implements kristina.trifunovic.entity.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id is required")
    private Integer id;
    @Column
    @NotNull(message = "Name is required")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    private String name;
    @Column
    @Size(max = 200, message = "Description should be maximum 200 characters")
    private String description;
    @Column
    @Max(value = 9, message = "No of ESP should be maximum 9")
    @NotNull(message = "No of ESP is required")
    private Integer noOfEsp;
    @Column
    @NotNull(message = "Year of study is required")
    @Max(value = 9, message = "Year of study should be maximum 9")
    private Integer yearOfStudy;
    @Column
    @NotNull(message = "Semester is required")
    @Enumerated(EnumType.STRING)
    private Semester semester;

    @JsonIgnore
    @ManyToMany(mappedBy = "subjects")
    private Set<ProfessorEntity> professors;

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    private Set<ExamEntity> exams;

    @Column
    private String literature;

    public SubjectEntity() {}

    public SubjectEntity(Integer id, String name, String description, Integer noOfEsp, Integer yearOfStudy, Semester semester) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.noOfEsp = noOfEsp;
        this.yearOfStudy = yearOfStudy;
        this.semester = semester;
    }

    public Set<ProfessorEntity> getProfessors() {
        return professors;
    }

    public void setProfessors(Set<ProfessorEntity> professors) {
        this.professors = professors;
    }

    public Set<ExamEntity> getExams() {
        return exams;
    }

    public void setExams(Set<ExamEntity> exams) {
        this.exams = exams;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNoOfEsp() {
        return noOfEsp;
    }

    public void setNoOfEsp(Integer noOfEsp) {
        this.noOfEsp = noOfEsp;
    }

    public Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public String getLiterature() {
        return literature;
    }

    public void setLiterature(String literature) {
        this.literature = literature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectEntity that = (SubjectEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(noOfEsp, that.noOfEsp) && Objects.equals(yearOfStudy, that.yearOfStudy) && semester == that.semester;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, noOfEsp, yearOfStudy, semester);
    }

    @Override
    public String toString() {
        return "SubjectEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", noOfEsp=" + noOfEsp +
                ", yearOfStudy=" + yearOfStudy +
                ", semester=" + semester +
                '}';
    }
}
