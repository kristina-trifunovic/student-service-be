package kristina.trifunovic.service;

import kristina.trifunovic.entity.CityEntity;
import kristina.trifunovic.entity.StudentEntity;
import kristina.trifunovic.entity.constraints.IndexEntity;
import kristina.trifunovic.exception.EntityExistsException;
import kristina.trifunovic.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StudentServiceTests {
    @Autowired
    private StudentService studentService;
    @MockBean
    private StudentRepository studentRepository;

    @Test
    public void saveStudent_successTest() throws EntityExistsException {
        StudentEntity student = new StudentEntity(
                new IndexEntity("0000", 2019),
                "test1",
                "test",
                "Test_FirstName_1",
                "Test_LastName_1",
                "testEmail@gmail.com",
                "Test Address",
                new CityEntity(10000L, "Test_City_1"),
                1);
        when(studentRepository.save(student)).thenReturn(student);
        when(studentRepository.findById(student.getUsername())).thenReturn(Optional.empty());
        StudentEntity savedStudent = studentService.save(student);
        assertNotNull(savedStudent);
        assertEquals(student, savedStudent);
    }

    @Test
    public void saveStudent_studentAlreadyExistsFailureTest() throws EntityExistsException {
        StudentEntity student = new StudentEntity(
                new IndexEntity("0000", 2019),
                "test1",
                "test",
                "Test_FirstName_1",
                "Test_LastName_1",
                "testEmail@gmail.com",
                "Test Address",
                new CityEntity(10000L, "Test_City_1"),
                1);
        when(studentRepository.findById(student.getUsername())).thenReturn(Optional.of(student));
        assertThrows(EntityExistsException.class, () -> studentService.save(student));
    }
}
