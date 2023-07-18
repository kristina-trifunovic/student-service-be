package kristina.trifunovic.id.entity;

import kristina.trifunovic.entity.Entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StudentExamId implements Serializable {
    @Column
    private String student;

    @Column(name = "exam_id")
    private ExamEntityId examId = new ExamEntityId();

    public StudentExamId() {
    }

    public StudentExamId(String student, ExamEntityId examId) {
        this.student = student;
        this.examId = examId;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public ExamEntityId getExamId() {
        return examId;
    }

    public void setExamId(ExamEntityId examId) {
        this.examId = examId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentExamId that = (StudentExamId) o;
        return Objects.equals(student, that.student) && Objects.equals(examId, that.examId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, examId);
    }
}
