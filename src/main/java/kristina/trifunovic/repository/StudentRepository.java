package kristina.trifunovic.repository;

import kristina.trifunovic.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, String> {
    Page<StudentEntity> findByFirstNameStartingWithIgnoreCaseAndLastNameStartingWithIgnoreCaseAndEmailStartingWithIgnoreCaseAndCityNameStartingWithIgnoreCase(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("city") String city,
            Pageable pageable);

//    @Query("SELECT s FROM StudentEntity s " +
//            "WHERE s.firstName LIKE CONCAT(:firstName, '%') " +
//            "AND s.lastName LIKE CONCAT(:lastName, '%') " +
//            "AND s.email LIKE CONCAT(:email, '%') " +
//            "AND s.city.name LIKE CONCAT(:city, '%')")
//    List<StudentEntity> findStudentsByFilter(@Param("firstName") String firstName,
//                                             @Param("lastName") String lastName,
//                                             @Param("email") String email,
//                                             @Param("city") String city);
}
