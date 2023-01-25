package kristina.trifunovic.entity;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "title")
public class TitleEntity implements kristina.trifunovic.entity.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id is required")
    private Integer id;
    @Column
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 30, message = "Title should be between 3 and 30 characters")
    private String professorTitle;

    public TitleEntity() {}

    public TitleEntity(Integer id, String professorTitle) {
        this.id = id;
        this.professorTitle = professorTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfessorTitle() {
        return professorTitle;
    }

    public void setProfessorTitle(String professorTitle) {
        this.professorTitle = professorTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TitleEntity that = (TitleEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(professorTitle, that.professorTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, professorTitle);
    }

    @Override
    public String toString() {
        return "TitleEntity{" +
                "id=" + id +
                ", professorTitle='" + professorTitle + '\'' +
                '}';
    }
}

