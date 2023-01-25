package kristina.trifunovic.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Entity
@Table(name = "city")
public class CityEntity implements kristina.trifunovic.entity.Entity  {
	private static final long serialVersionUID = -8198446667138082944L;
	
	@Id
	@Column
	@Min(value = 10000L, message = "Postal code should be minimum value of 10000")
	@Max(value = 99999L, message = "Postal code should be maximum value of 99999")
	@NotNull(message = "Postal code is required")
	private Long postalCode;
	@Column
	@Size(min = 2, message = "Name should be minimum 2 characters")
	@Size(max = 30, message = "Name should be maximum 30 characters")
	@NotBlank(message = "Name is required")
	private String name;
	
	public CityEntity() {
	}

	public CityEntity(Long postalCode, String name) {
		this.postalCode = postalCode;
		this.name = name;
	}

	public Long getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Long postalCode) {
		this.postalCode = postalCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, postalCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CityEntity other = (CityEntity) obj;
		return Objects.equals(name, other.name) && Objects.equals(postalCode, other.postalCode);
	}

	@Override
	public String toString() {
		return "CityEntity [postalCode=" + postalCode + ", name=" + name + "]";
	}
	
	
}
