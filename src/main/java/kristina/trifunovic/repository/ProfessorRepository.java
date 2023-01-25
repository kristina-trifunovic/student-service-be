package kristina.trifunovic.repository;

import kristina.trifunovic.entity.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorEntity, String> {
    @Query("SELECT p FROM ProfessorEntity p " +
            "INNER JOIN p.subjects s WHERE s.id = :id")
    List<ProfessorEntity> findProfessorsBySubject(@Param("id") Integer id);
}
