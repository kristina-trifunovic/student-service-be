package kristina.trifunovic.controller;

import kristina.trifunovic.entity.ExamEntity;
import kristina.trifunovic.entity.StudentTakesExamEntity;
import kristina.trifunovic.exception.UnknownEntityException;
import kristina.trifunovic.service.StudentTakesExamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("student-takes-exam")
public class StudentTakesExamRestController {
    private final StudentTakesExamService studentTakesExamService;

    public StudentTakesExamRestController(StudentTakesExamService studentTakesExamService) {
        this.studentTakesExamService = studentTakesExamService;
    }

    @GetMapping()
    public List<StudentTakesExamEntity> findAll() {
        return studentTakesExamService.findAll();
    }

    @GetMapping("{professorUsername}")
    public List<StudentTakesExamEntity> findAllByProfessor(@PathVariable("professorUsername") String professorUsername) {
        return studentTakesExamService.findAllByProfessor(professorUsername);
    }

    @PutMapping("{grade}/{student}")
    public ResponseEntity<Object> saveGrade(@RequestBody ExamEntity exam, @PathVariable("grade") Integer grade, @PathVariable("student") String studentUsername) {
        try {
            Integer updatedRows = studentTakesExamService.saveGrade(exam, grade, studentUsername);
            return ResponseEntity.ok().body("Updated " + updatedRows + " row(s)");
        } catch (UnknownEntityException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
