package kristina.trifunovic.service;

import kristina.trifunovic.entity.ExamEntity;
import kristina.trifunovic.exception.EntityExistsException;
import kristina.trifunovic.exception.UnknownEntityException;
import kristina.trifunovic.id.entity.ExamEntityId;
import kristina.trifunovic.repository.ExamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamService implements kristina.trifunovic.service.Service<ExamEntity, ExamEntityId> {
    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public List<ExamEntity> findAll() {
        return examRepository.findAll();
    }

    @Override
    public ExamEntity save(ExamEntity exam) throws EntityExistsException {
        Optional<ExamEntity> examFromDb = examRepository.findById(exam.getId());
        if (examFromDb.isPresent()) {
            throw new EntityExistsException("Exam with id " + exam.getId() + " already exists", exam);
        }
        return examRepository.save(exam);
    }

    @Override
    public ExamEntity update(ExamEntity exam) throws UnknownEntityException {
        Optional<ExamEntity> examToUpdate = examRepository.findById(exam.getId());
        if (examToUpdate.isPresent()) {
            return examRepository.save(exam);
        }
        throw new UnknownEntityException("Exam does not exist", exam);

    }

    @Override
    public void delete(ExamEntityId examId) throws UnknownEntityException {

    }

    @Override
    public Optional<ExamEntity> findById(ExamEntityId examId) {
        Optional<ExamEntity> examFromDb = examRepository.findById(examId);
        if (examFromDb.isPresent()) {
            return examFromDb;
        }
        return Optional.empty();
    }

    @Override
    public Page<ExamEntity> findPage(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        return examRepository.findAll(pageable);
    }

    public List<ExamEntity> findStudentsAppliedExams(String username) {
        return examRepository.findStudentsAppliedExams(username);
    }

    public List<ExamEntity> findAllExamsForStudentToApplyTo(String username) {
        return examRepository.findAllExamsForStudentToApplyTo(username);
    }
}
