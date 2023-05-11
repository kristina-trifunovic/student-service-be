package kristina.trifunovic.service;

import kristina.trifunovic.entity.StudentEntity;
import kristina.trifunovic.exception.EntityExistsException;
import kristina.trifunovic.exception.NotFoundException;
import kristina.trifunovic.exception.UnknownEntityException;
import kristina.trifunovic.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class StudentService implements Service<StudentEntity, String> {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<StudentEntity> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public StudentEntity save(StudentEntity student) throws EntityExistsException {
        Optional<StudentEntity> existingStudent = studentRepository.findById(student.getUsername());
        if (existingStudent.isPresent()) {
            throw new EntityExistsException("Student " + student.getFirstName() + " " + student.getLastName() + " already exists", student);
        }
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        return studentRepository.save(student);
    }

    @Override
    public StudentEntity update(StudentEntity student) throws UnknownEntityException {
        Optional<StudentEntity> existingStudent = studentRepository.findById(student.getUsername());
        if (!existingStudent.isPresent()) {
            throw new UnknownEntityException("Student does not exist", student);
        }
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        return studentRepository.save(student);
    }

    @Override
    public void delete(String username) throws UnknownEntityException {
        Optional<StudentEntity> studentToDelete = studentRepository.findById(username);
        if (studentToDelete.isPresent()) {
            studentRepository.delete(studentToDelete.get());
        } else throw new UnknownEntityException("Student not found", studentToDelete);
    }

    @Override
    public Optional<StudentEntity> findById(String username) {
        Optional<StudentEntity> studentFromDb = studentRepository.findById(username);
        if (studentFromDb.isPresent()) {
            return studentFromDb;
        }
        return Optional.empty();
    }

    @Override
    public Page<StudentEntity> findPage(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        return studentRepository.findAll(pageable);
    }

    public Page<StudentEntity> findFilteredPage(String firstName, String lastName, String email, String city, Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        return studentRepository.findByFirstNameStartingWithIgnoreCaseAndLastNameStartingWithIgnoreCaseAndEmailStartingWithIgnoreCaseAndCityNameStartingWithIgnoreCase(firstName, lastName, email, city, pageable);
    }

//    public List<StudentEntity> findStudentsByFilter(String firstName, String lastName, String email, String city) throws NotFoundException {
//        List<StudentEntity> filteredStudents = studentRepository.findStudentsByFilter(firstName, lastName, email, city);
//        if (!filteredStudents.isPresent()) throw new NotFoundException("Not found any student with criteria");
//        else return filteredStudents;
//    }

}
