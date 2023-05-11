package kristina.trifunovic.service;

import kristina.trifunovic.entity.ExamPeriodEntity;
import kristina.trifunovic.exception.EntityExistsException;
import kristina.trifunovic.exception.UnknownEntityException;
import kristina.trifunovic.repository.ExamPeriodRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ExamPeriodService implements Service<ExamPeriodEntity, Integer> {
    private final ExamPeriodRepository examPeriodRepository;

    public ExamPeriodService(ExamPeriodRepository examPeriodRepository) {
        this.examPeriodRepository = examPeriodRepository;
    }

    @Override
    public List<ExamPeriodEntity> findAll() {
        return examPeriodRepository.findAll();
    }

    @Override
    public ExamPeriodEntity save(ExamPeriodEntity examPeriod) throws EntityExistsException {
        Optional<ExamPeriodEntity> existingExamPeriod = examPeriodRepository.findById((examPeriod.getId()));
        if (existingExamPeriod.isPresent()) {
            throw new EntityExistsException("Exam period " + existingExamPeriod.get().getName() + " already exists", existingExamPeriod.get());
        }
        return examPeriodRepository.save(examPeriod);
    }

    @Override
    public ExamPeriodEntity update(ExamPeriodEntity examPeriod) throws UnknownEntityException {
        Optional<ExamPeriodEntity> existingExamPeriod = examPeriodRepository.findById(examPeriod.getId());
        if (!existingExamPeriod.isPresent()) {
            throw new UnknownEntityException("Exam period does not exist", examPeriod);
        }
        return examPeriodRepository.save(examPeriod);
    }

    @Override
    public void delete(Integer id) throws UnknownEntityException {
        Optional<ExamPeriodEntity> examPeriodToDelete = examPeriodRepository.findById(id);
        if (examPeriodToDelete.isPresent()) {
            examPeriodRepository.delete(examPeriodToDelete.get());
        } else throw new UnknownEntityException("Professor not found", null);
    }

    @Override
    public Optional<ExamPeriodEntity> findById(Integer id) {
        Optional<ExamPeriodEntity> examPeriodFromDb = examPeriodRepository.findById(id);
        if (examPeriodFromDb.isPresent()) {
            return examPeriodFromDb;
        }
        return Optional.empty();
    }

    @Override
    public Page<ExamPeriodEntity> findPage(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        return examPeriodRepository.findAll(pageable);
    }

    public Optional<ExamPeriodEntity> findActiveExamPeriod() throws EntityExistsException {
        Optional<ExamPeriodEntity> activeExamPeriod = examPeriodRepository.findActiveExamPeriod();
        if (activeExamPeriod.isPresent()) {
            return activeExamPeriod;
        } else throw new EntityExistsException("There is no active exam period", null);
    }
}
