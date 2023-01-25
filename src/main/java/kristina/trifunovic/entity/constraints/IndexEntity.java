package kristina.trifunovic.entity.constraints;

import kristina.trifunovic.entity.Entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class IndexEntity implements Serializable, Entity {
    @Column(name = "index_number")
    @NotBlank
    @Size(max = 4)
    private String indexNumber;
    @Column(name = "index_year")
    @NotNull
    @Min(2000)
    @Max(2100)
    private Integer indexYear;

    public IndexEntity() {}

    public IndexEntity(String indexNumber, Integer indexYear) {
        this.indexNumber = indexNumber;
        this.indexYear = indexYear;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    public Integer getIndexYear() {
        return indexYear;
    }

    public void setIndexYear(Integer indexYear) {
        this.indexYear = indexYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexEntity that = (IndexEntity) o;
        return Objects.equals(indexNumber, that.indexNumber) && Objects.equals(indexYear, that.indexYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indexNumber, indexYear);
    }

    @Override
    public String toString() {
        return "StudentId{" +
                "indexNumber='" + indexNumber + '\'' +
                ", indexYear=" + indexYear +
                '}';
    }
}
