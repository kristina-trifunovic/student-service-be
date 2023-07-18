package kristina.trifunovic.repository;

import kristina.trifunovic.entity.ExamEntity;
import kristina.trifunovic.id.entity.ExamEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, ExamEntityId> {
    @Query("SELECT e FROM ExamEntity e INNER JOIN e.students s WHERE s.student.username = :username")
    List<ExamEntity> findStudentsAppliedExams(@Param("username") String username);

    @Query("SELECT e FROM ExamEntity e LEFT JOIN FETCH e.students es LEFT JOIN FETCH es.student WHERE es.student.username != :username OR es.student.username IS NULL")
    List<ExamEntity> findAllExamsForStudentToApplyTo(@Param("username") String username);

    @Query("SELECT e FROM ExamEntity e WHERE e.professor.username = :professorUsername")
    List<ExamEntity> findProfessorsExams(@Param("professorUsername") String professorUsername);
}
