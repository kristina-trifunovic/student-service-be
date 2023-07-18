package kristina.trifunovic.entity;

import kristina.trifunovic.id.entity.StudentExamId;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Table(name = "student_takes_exam")
public class StudentTakesExamEntity implements kristina.trifunovic.entity.Entity {
    @EmbeddedId
    private StudentExamId id = new StudentExamId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("student")
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("examId")
    private ExamEntity exam;

    @Min(value = 5, message = "Grade should be minimum 5")
    @Max(value = 10, message = "Grade should be maximum 10")
    protected Integer grade;

    public StudentTakesExamEntity() {
    }

    public StudentTakesExamEntity(StudentExamId id, StudentEntity student, ExamEntity exam, Integer grade) {
        this.id = id;
        this.student = student;
        this.exam = exam;
        this.grade = grade;
    }

    public StudentTakesExamEntity(StudentEntity student, ExamEntity exam) {
        this.student = student;
        this.exam = exam;
    }

    public StudentExamId getId() {
        return id;
    }

    public void setId(StudentExamId id) {
        this.id = id;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public ExamEntity getExam() {
        return exam;
    }

    public void setExam(ExamEntity exam) {
        this.exam = exam;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentTakesExamEntity that = (StudentTakesExamEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(student, that.student) && Objects.equals(exam, that.exam) && Objects.equals(grade, that.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, student, exam, grade);
    }

    @Override
    public String toString() {
        return "StudentTakesExamEntity{" +
                "id=" + id +
                ", student=" + student +
                ", exam=" + exam +
                ", grade=" + grade +
                '}';
    }
}
