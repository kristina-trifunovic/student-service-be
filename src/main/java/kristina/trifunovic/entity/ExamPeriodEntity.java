package kristina.trifunovic.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "exam_period", uniqueConstraints = @UniqueConstraint(name = "UK_Active", columnNames = "active"))
public class ExamPeriodEntity implements kristina.trifunovic.entity.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @NotNull(message = "Id is required")
    private Integer id;
    @Column
    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Name should be at least 3 characters")
    @Size(max = 30, message = "Name should be maximum 30 characters")
    private String name;
    @Column
    @NotNull(message = "Start date is required")
    @Type(type="date")
    @JsonFormat(pattern="dd.MM.yyyy")
    private Date startDate;
    @Column
    @NotNull(message = "End date is required")
    @Type(type="date")
    @JsonFormat(pattern="dd.MM.yyyy")
    private Date endDate;
    @Column()
    @Max(value = 1, message = "Active can only be value 1 or empty")
    private Integer active;

    @OneToMany(mappedBy = "examPeriod")
    @JsonIgnore
    private List<ExamEntity> exams;

    public ExamPeriodEntity() {}

    public ExamPeriodEntity(Integer id, String name, Date startDate, Date endDate, Integer isActive) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = isActive;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamPeriodEntity that = (ExamPeriodEntity) o;
        return active == that.active && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startDate, endDate, active);
    }

    @Override
    public String toString() {
        return "ExamPeriodEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", active=" + active +
                '}';
    }
}
