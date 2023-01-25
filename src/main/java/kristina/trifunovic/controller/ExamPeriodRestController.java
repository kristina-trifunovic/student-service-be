package kristina.trifunovic.controller;

import kristina.trifunovic.entity.ExamPeriodEntity;
import kristina.trifunovic.exception.EntityExistsException;
import kristina.trifunovic.exception.UnknownEntityException;
import kristina.trifunovic.service.ExamPeriodService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import static kristina.trifunovic.controller.CityRestController.getStringStringMap;

@RestController
@RequestMapping("exam-periods")
public class ExamPeriodRestController {
    private final ExamPeriodService examPeriodService;

    public ExamPeriodRestController(ExamPeriodService examPeriodService) {
        this.examPeriodService = examPeriodService;
    }

    @GetMapping
    public List<ExamPeriodEntity> findAll() {
        return examPeriodService.findAll();
    }

    @PostMapping
    public @ResponseBody ResponseEntity<Object> save(@RequestBody ExamPeriodEntity examPeriod) {
        try {
            ExamPeriodEntity savedExamPeriod = examPeriodService.save(examPeriod);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully added " + savedExamPeriod);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping()
    public @ResponseBody ResponseEntity<Object> update(@Valid @RequestBody ExamPeriodEntity examPeriod) {
        try {
            ExamPeriodEntity updatedExamPeriod = examPeriodService.update(examPeriod);
            return ResponseEntity.ok().body(updatedExamPeriod);
        } catch (UnknownEntityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public @ResponseBody ResponseEntity<Object> delete(@Valid @PathVariable Integer id) {
        try {
            examPeriodService.delete(id);
            return ResponseEntity.ok().body("Successfully deleted exam period with id " + id);
        } catch (UnknownEntityException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public @ResponseBody ResponseEntity<Object> findById(@Valid @PathVariable Integer id) {
        Optional<ExamPeriodEntity> examPeriodFromDb = examPeriodService.findById(id);
        if (examPeriodFromDb.isPresent()) {
            return ResponseEntity.ok().body(examPeriodFromDb.get());
        }
        return ResponseEntity.badRequest().body("Exam period not found");
    }

    @GetMapping("page")
    public ResponseEntity<Page<ExamPeriodEntity>> findPage(@RequestParam(defaultValue = "0") Integer pageNo,
                                                          @RequestParam(defaultValue = "5") Integer pageSize,
                                                          @RequestParam(defaultValue = "name") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String sortOrder) {
        return new ResponseEntity<>(examPeriodService.findPage(pageNo, pageSize, sortBy, sortOrder), new HttpHeaders(),
                HttpStatus.OK);
    }

    @GetMapping("active")
    public ResponseEntity<Object> findActiveExamPeriod() {
        try {
            Optional<ExamPeriodEntity> activeExamPeriod = examPeriodService.findActiveExamPeriod();
            return ResponseEntity.ok().body(activeExamPeriod.get());
        } catch (EntityExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidation(MethodArgumentNotValidException ex) {
//        return getStringStringMap(ex);
//    }

}
