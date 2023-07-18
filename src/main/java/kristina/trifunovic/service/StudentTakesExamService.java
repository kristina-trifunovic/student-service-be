package kristina.trifunovic.service;

import kristina.trifunovic.entity.ExamEntity;
import kristina.trifunovic.entity.StudentEntity;
import kristina.trifunovic.entity.StudentTakesExamEntity;
import kristina.trifunovic.exception.EntityExistsException;
import kristina.trifunovic.exception.UnknownEntityException;
import kristina.trifunovic.id.entity.StudentExamId;
import kristina.trifunovic.repository.ExamRepository;
import kristina.trifunovic.repository.StudentRepository;
import kristina.trifunovic.repository.StudentTakesExamRepository;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class StudentTakesExamService implements Service<StudentTakesExamEntity, StudentExamId> {
    private final StudentTakesExamRepository studentTakesExamRepository;
    private final ExamRepository examRepository;
    private final StudentRepository studentRepository;

    public StudentTakesExamService(StudentTakesExamRepository studentTakesExamRepository, ExamRepository examRepository, StudentRepository studentRepository) {
        this.studentTakesExamRepository = studentTakesExamRepository;
        this.examRepository = examRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentTakesExamEntity> findAll() {
        return studentTakesExamRepository.findAll();
    }

    public List<StudentTakesExamEntity> findAllByProfessor(String professorUsername) {
        return studentTakesExamRepository.findAllByProfessor(professorUsername);
    }

    public List<StudentTakesExamEntity> applyExams(StudentEntity student, List<ExamEntity> exams) {
        List<StudentTakesExamEntity> insertedStudentExams = new ArrayList<>();
        Optional<StudentEntity> studentFromDb = studentRepository.findById(student.getUsername());
        if (studentFromDb.isPresent()) {
            for (ExamEntity exam : exams) {
                studentFromDb.get().addExam(exam);
                StudentTakesExamEntity studentTakesExam = new StudentTakesExamEntity();
                studentTakesExam.setId(new StudentExamId(student.getUsername(), exam.getId()));
                studentTakesExam.setStudent(student);
                studentTakesExam.setExam(exam);
                studentTakesExam.setGrade(null);
//                insertedStudentExams.add(studentTakesExamRepository.save(studentTakesExam));
                studentRepository.save(studentFromDb.get());
            }
        }
        return insertedStudentExams;
    }

    public Integer saveGrade(ExamEntity exam, Integer grade, String student) throws UnknownEntityException {
        Optional<ExamEntity> examToUpdate = examRepository.findById(exam.getId());
        if (examToUpdate.isPresent()) {
            return studentTakesExamRepository.saveGrade(student, exam.getProfessor().getUsername(), exam.getExamPeriod().getId(), exam.getSubject().getId(), grade);
        }
        throw new UnknownEntityException("Exam does not exist", exam);
    }

    @Override
    public StudentTakesExamEntity save(StudentTakesExamEntity studentTakesExamEntity) throws EntityExistsException {
        return null;
    }

    @Override
    public StudentTakesExamEntity update(StudentTakesExamEntity studentTakesExamEntity) throws UnknownEntityException {
        return null;
    }

    @Override
    public void delete(StudentExamId studentExamId) throws UnknownEntityException {

    }

    @Override
    public Optional<StudentTakesExamEntity> findById(StudentExamId studentExamId) {
        return Optional.empty();
    }

    @Override
    public Page<StudentTakesExamEntity> findPage(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }
}
