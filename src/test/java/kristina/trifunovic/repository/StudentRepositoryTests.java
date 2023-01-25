package kristina.trifunovic.repository;

import kristina.trifunovic.entity.CityEntity;
import kristina.trifunovic.entity.StudentEntity;
import kristina.trifunovic.entity.constraints.IndexEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class StudentRepositoryTests {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CityRepository cityRepository;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
        cityRepository.deleteAll();
    }

    @Test
    public void saveTest() {
        CityEntity city = cityRepository.save(new CityEntity(10000L, "Test_City_1"));
        StudentEntity student = new StudentEntity(
                new IndexEntity("0000", 2019),
                "test1",
                "test",
                "Test_FirstName_1",
                "Test_LastName_1",
                "testEmail@gmail.com",
                "Test Address",
                city,
                1);
        StudentEntity savedStudent = studentRepository.save(student);
        assertNotNull(savedStudent);
        assertEquals(student, savedStudent);
    }
}
