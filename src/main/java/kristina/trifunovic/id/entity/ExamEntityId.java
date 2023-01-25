package kristina.trifunovic.id.entity;

import kristina.trifunovic.entity.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ExamEntityId implements Serializable, Entity {
    @Column
    private String professorUsername;
    @Column
    @NotNull
    private Integer subjectId;
    @Column
    @NotNull
    private Integer examPeriodId;

    public ExamEntityId() { }

    public ExamEntityId(String professorUsername, Integer subjectId, Integer examPeriodId) {
        this.professorUsername = professorUsername;
        this.subjectId = subjectId;
        this.examPeriodId = examPeriodId;
    }

    public String getProfessorUsername() {
        return professorUsername;
    }

    public void setProfessorUsername(String professorUsername) {
        this.professorUsername = professorUsername;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getExamPeriodId() {
        return examPeriodId;
    }

    public void setExamPeriodId(Integer examPeriodId) {
        this.examPeriodId = examPeriodId;
    }

    @Override
    public String toString() {
        return "ExamId{" +
                ", professorUsername='" + professorUsername + '\'' +
                ", subjectId=" + subjectId +
                ", examPeriodId=" + examPeriodId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamEntityId examId = (ExamEntityId) o;
        return Objects.equals(professorUsername, examId.professorUsername) && Objects.equals(subjectId, examId.subjectId) && Objects.equals(examPeriodId, examId.examPeriodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(professorUsername, subjectId, examPeriodId);
    }
}
