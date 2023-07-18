package kristina.trifunovic.repository;

import kristina.trifunovic.entity.StudentTakesExamEntity;
import kristina.trifunovic.id.entity.StudentExamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StudentTakesExamRepository extends JpaRepository<StudentTakesExamEntity, StudentExamId> {
    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("UPDATE StudentTakesExamEntity se SET se.grade = :grade WHERE " +
            "se.student.username = :studentUsername AND " +
            "se.exam.id.professorUsername = :professorUsername AND " +
            "se.exam.id.examPeriodId = :examPeriodId AND " +
            "se.exam.id.subjectId = :subjectId")
    Integer saveGrade(@Param("studentUsername") String studentUsername,
                      @Param("professorUsername") String professorUsername,
                      @Param("examPeriodId") Integer examPeriodId,
                      @Param("subjectId") Integer subjectId,
                      @Param("grade") Integer grade);

    @Query("SELECT se FROM StudentTakesExamEntity se WHERE se.exam.id.professorUsername = :professorUsername")
    List<StudentTakesExamEntity> findAllByProfessor(@Param("professorUsername") String professorUsername);
}
