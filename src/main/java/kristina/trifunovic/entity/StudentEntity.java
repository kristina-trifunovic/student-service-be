package kristina.trifunovic.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import kristina.trifunovic.entity.constraints.IndexEntity;

import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "student", uniqueConstraints = { @UniqueConstraint(name = "UK_Index", columnNames = { "index_number", "index_year" }) })
//@Table(name = "student")
public class StudentEntity extends UserEntity {
	@Embedded
	@NotNull(message = "Index is required")
	private IndexEntity index;
	@Column
	@NotNull(message = "Current year of study is required")
	@Min(value = 1, message = "Current year of study can not be less than 1")
	@Max(value = 9, message = "Current year of study can not be more than 9")
	private Integer currentYearOfStudy;

//	@JsonManagedReference
	@ManyToMany
	@JoinTable(
			name = "student_takes_exams",
			joinColumns = @JoinColumn(name = "student", referencedColumnName = "username"),
			foreignKey = @ForeignKey(name = "FK_StudentTakesExam_Student"),
			inverseJoinColumns = {
					@JoinColumn(name = "professor", referencedColumnName = "professor", foreignKey = @ForeignKey(name = "FK_StudentTakesExam_Professor")),
					@JoinColumn(name = "subject", referencedColumnName = "subject", foreignKey = @ForeignKey(name = "FK_StudentTakesExam_Subject")),
					@JoinColumn(name = "exam_period", referencedColumnName = "exam_period", foreignKey = @ForeignKey(name = "FK_StudentTakesExam_ExamPeriod"))
			}
	)
	private Set<ExamEntity> exams;
	
	public StudentEntity() {
	}

	public StudentEntity(IndexEntity index, String username, String password, String firstName, String lastName, String email, String address, CityEntity city, Integer currentYearOfStudy) {
		super(firstName, lastName, username, password, email, address, city);
		this.index = index;
		this.currentYearOfStudy = currentYearOfStudy;
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

	public IndexEntity getIndex() {
		return index;
	}

	public void setStudentId(IndexEntity index) {
		this.index = index;
	}

	public Integer getCurrentYearOfStudy() {
		return currentYearOfStudy;
	}

	public void setCurrentYearOfStudy(Integer currentYearOfStudy) {
		this.currentYearOfStudy = currentYearOfStudy;
	}
	
	public void addExam(ExamEntity exam) {
		exams.add(exam);
	}

	public void setExams(Set<ExamEntity> exams) {
		this.exams = exams;
	}

	public Set<ExamEntity> getExams() {
		return exams;
	}

	@Override
	public String toString() {
		return "StudentEntity{" +
				"index=" + index +
				", currentYearOfStudy=" + currentYearOfStudy +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		StudentEntity that = (StudentEntity) o;
		return Objects.equals(index, that.index) && Objects.equals(currentYearOfStudy, that.currentYearOfStudy);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), index, currentYearOfStudy);
	}
}
