package kristina.trifunovic.repository;

import kristina.trifunovic.entity.ExamPeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamPeriodRepository extends JpaRepository<ExamPeriodEntity, Integer> {
    @Query("SELECT ep FROM ExamPeriodEntity ep WHERE ep.active = 1")
    Optional<ExamPeriodEntity> findActiveExamPeriod();
}
