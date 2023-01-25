package kristina.trifunovic.repository;

import kristina.trifunovic.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {
    @Query("SELECT s FROM SubjectEntity s " +
            "INNER JOIN s.professors p WHERE p.username = :username")
    List<SubjectEntity> findSubjectsByProfessor(@Param("username") String username);

    @Query("SELECT s FROM SubjectEntity s LEFT JOIN s.professors p WHERE p.username IS NULL")
    List<SubjectEntity> findAllSubjectsWithNoProfessor();
}
