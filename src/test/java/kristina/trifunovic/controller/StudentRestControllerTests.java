package kristina.trifunovic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kristina.trifunovic.entity.CityEntity;
import kristina.trifunovic.entity.StudentEntity;
import kristina.trifunovic.entity.constraints.IndexEntity;
import kristina.trifunovic.repository.UserRepository;
import kristina.trifunovic.service.ExamService;
import kristina.trifunovic.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ComponentScan({"kristina.trifunovic.service.security", "kristina.trifunovic.repository"})
@WebMvcTest(StudentRestController.class)
public class StudentRestControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ExamService examService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "test_user", password = "test_user", authorities = "ROLE_ADMIN")
    public void saveStudent_successTest() throws Exception {
        StudentEntity student = new StudentEntity(
                new IndexEntity("0100", 2019),
                "test1",
                "test1",
                "TestFirstName",
                "TestLastName",
                "testEmail@gmail.com",
                "Test Address",
                new CityEntity(10000L, "Test_City"),
                2);
        when(studentService.save(student)).thenReturn(student);
        this.mockMvc
                .perform(post("/students").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.username", equalTo(student.getUsername())));
    }
}
