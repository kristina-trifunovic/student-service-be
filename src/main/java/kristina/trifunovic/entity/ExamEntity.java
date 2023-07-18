package kristina.trifunovic.entity;

import com.fasterxml.jackson.annotation.*;
import kristina.trifunovic.id.entity.ExamEntityId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "exam")
public class ExamEntity implements kristina.trifunovic.entity.Entity {
    @EmbeddedId
    private ExamEntityId id;

//    @ManyToMany(mappedBy = "exams")
//    private Set<StudentEntity> students;

    @ManyToOne
    @MapsId("professorUsername")
    @JoinColumn(name = "professor", foreignKey = @ForeignKey(name = "FK_Exam_Professor"))
    @NotNull(message = "Professor is required")
    private ProfessorEntity professor;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject", foreignKey = @ForeignKey(name = "FK_Exam_Subject"))
    @NotNull(message = "Subject is required")
    private SubjectEntity subject;

    @ManyToOne
    @MapsId("examPeriodId")
    @JoinColumn(name = "exam_period", foreignKey = @ForeignKey(name = "FK_Exam_ExamPeriod"))
    @NotNull(message = "Exam period is required")
    private ExamPeriodEntity examPeriod;

    @Column
    @NotNull
    @Type(type="date")
    @JsonFormat(pattern="dd.MM.yyyy")
    @NotNull(message = "Exam date is required")
    private Date examDate;

    @OneToMany(
            mappedBy = "exam",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<StudentTakesExamEntity> students = new ArrayList<>();

//    @Column
//    @Min(value = 5, message = "Grade should be minimum 5")
//    @Max(value = 10, message = "Grade should be maximum 10")
//    private Integer grade;

    public ExamEntity() { }

    public ExamEntity(ExamEntityId id, ProfessorEntity professor, SubjectEntity subject, ExamPeriodEntity examPeriod, Date examDate) {
        this.id = id;
        this.professor = professor;
        this.subject = subject;
        this.examPeriod = examPeriod;
        this.examDate = examDate;
//        this.grade = grade;
    }

    public ExamEntityId getId() {
        return id;
    }

    public void setId(ExamEntityId id) {
        this.id = id;
    }

    public ProfessorEntity getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorEntity professor) {
        this.professor = professor;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }

    public ExamPeriodEntity getExamPeriod() {
        return examPeriod;
    }

    public void setExamPeriod(ExamPeriodEntity examPeriod) {
        this.examPeriod = examPeriod;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

//    public Integer getGrade() {
//        return grade;
//    }
//
//    public void setGrade(Integer grade) {
//        this.grade = grade;
//    }

    public void setStudents(List<StudentTakesExamEntity> students) {
        this.students = students;
    }

    public List<StudentTakesExamEntity> getStudents() {
        return students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamEntity that = (ExamEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(professor, that.professor) && Objects.equals(subject, that.subject) && Objects.equals(examPeriod, that.examPeriod) && Objects.equals(examDate, that.examDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, professor, subject, examPeriod, examDate);
    }

    @Override
    public String toString() {
        return "ExamEntity{" +
                "id=" + id +
                ", professor=" + professor +
                ", subject=" + subject +
                ", examPeriod=" + examPeriod +
                ", examDate=" + examDate +
//                ", grade=" + grade +
                '}';
    }
}
