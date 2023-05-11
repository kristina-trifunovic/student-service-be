package kristina.trifunovic.controller;

import kristina.trifunovic.entity.ExamEntity;
import kristina.trifunovic.entity.StudentEntity;
import kristina.trifunovic.exception.EntityExistsException;
import kristina.trifunovic.exception.UnknownEntityException;
import kristina.trifunovic.id.entity.ExamEntityId;
import kristina.trifunovic.service.ExamService;
import kristina.trifunovic.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("exams")
public class ExamRestController {
    private final ExamService examService;
    private final StudentService studentService;

    public ExamRestController(ExamService examService, StudentService studentService) {
        this.examService = examService;
        this.studentService = studentService;
    }

    @PostMapping()
    public @ResponseBody ResponseEntity<Object> save(@RequestBody ExamEntity exam) {
        try {
            ExamEntity savedExam = examService.save(exam);
            return ResponseEntity.status(HttpStatus.OK).body(savedExam);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("{professorUsername}/{subjectId}/{examPeriodId}")
    public @ResponseBody ResponseEntity<Object> findById(@Valid @PathVariable String professorUsername,
                                                         @Valid @PathVariable Integer subjectId,
                                                         @Valid @PathVariable Integer examPeriodId) {
        ExamEntityId id = new ExamEntityId(professorUsername, subjectId, examPeriodId);
        Optional<ExamEntity> examFromDb = examService.findById(id);
        if (!examFromDb.isPresent()) {
            return ResponseEntity.badRequest().body("Exam not found");
        }
        return ResponseEntity.ok().body(examFromDb.get());
    }

    @GetMapping()
    public List<ExamEntity> findAll() {
        return examService.findAll();
    }

    @GetMapping("page")
    public ResponseEntity<Page<ExamEntity>> findPage(@RequestParam(defaultValue = "0") Integer pageNo,
                                                          @RequestParam(defaultValue = "5") Integer pageSize,
                                                          @RequestParam(defaultValue = "examDate") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String sortOrder) {
        return new ResponseEntity<>(examService.findPage(pageNo, pageSize, sortBy, sortOrder), new HttpHeaders(),
                HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody ExamEntity exam) {
        try {
            ExamEntity updatedExam = examService.update(exam);
            return ResponseEntity.ok().body("Updated " + updatedExam);
        } catch (UnknownEntityException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{username}/apply")
    public ResponseEntity<Object> addExamsToStudent(@PathVariable String username,
                                                    @RequestBody Set<ExamEntity> exams) {
        Optional<StudentEntity> studentFromDb = studentService.findById(username);
        if (studentFromDb.isPresent()) {
            for (ExamEntity exam: exams) {
                Optional<ExamEntity> examFromDb = examService.findById(exam.getId());
                if (examFromDb.isPresent()) {
                    studentFromDb.get().addExam(examFromDb.get());
                } else return ResponseEntity.badRequest().body("Exam not found");
            }
            try {
                StudentEntity updatedStudent = studentService.update(studentFromDb.get());
                return ResponseEntity.ok().body(updatedStudent);
            } catch (UnknownEntityException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else return ResponseEntity.badRequest().body("Student not found");
    }

    @GetMapping("{studentUsername}/applied-exams")
    public ResponseEntity<Object> findStudentsAppliedExams(@Valid @PathVariable("studentUsername") String username) {
        return ResponseEntity.ok().body(examService.findStudentsAppliedExams(username));
    }

    @GetMapping("{studentUsername}/to-apply-to")
    public List<ExamEntity> findAllExamsForStudentToApplyTo(@PathVariable("studentUsername") String username) {
        return examService.findAllExamsForStudentToApplyTo(username);
    }
}
