package kristina.trifunovic.controller;

import kristina.trifunovic.entity.StudentEntity;
import kristina.trifunovic.exception.EntityExistsException;
import kristina.trifunovic.exception.UnknownEntityException;
import kristina.trifunovic.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("students")
public class StudentRestController {

    private final StudentService studentService;

    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public List<StudentEntity> findAll() {
        return studentService.findAll();
    }

    @PostMapping()
    public @ResponseBody ResponseEntity<Object> save(@Valid @RequestBody StudentEntity student) {
        try {
            StudentEntity savedStudent = studentService.save(student);
            return ResponseEntity.status(HttpStatus.OK).body(savedStudent);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping()
    public @ResponseBody ResponseEntity<Object> update(@RequestBody StudentEntity student) {
        try {
            StudentEntity updatedStudent = studentService.update(student);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully updated " + updatedStudent);
        } catch (UnknownEntityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student not updated");
        }
    }

    @GetMapping("{username}")
    public @ResponseBody ResponseEntity<Object> findById(@PathVariable String username) {
        Optional<StudentEntity> studentFromDB = studentService.findById(username);
        if (studentFromDB.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(studentFromDB.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student not found");
    }

    @DeleteMapping("{username}")
    public @ResponseBody ResponseEntity<Object> delete(@PathVariable String username) {
        try {
            Optional<StudentEntity> existingStudent = studentService.findById(username);
            studentService.delete(username);
            return ResponseEntity.status(HttpStatus.OK).body(existingStudent.get() + " successfully deleted");

        } catch (UnknownEntityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("filter")
    public ResponseEntity<Page<StudentEntity>> findFilteredPage(@RequestParam(defaultValue = "") String firstName,
                                                                @RequestParam(defaultValue = "") String lastName,
                                                                @RequestParam(defaultValue = "") String email,
                                                                @RequestParam(defaultValue = "") String city,
                                                                @RequestParam(defaultValue = "0") Integer pageNo,
                                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                                @RequestParam(defaultValue = "lastName") String sortBy,
                                                                @RequestParam(defaultValue = "asc") String sortOrder) {
        return new ResponseEntity<>(studentService.findFilteredPage(firstName, lastName, email, city, pageNo, pageSize, sortBy, sortOrder), new HttpHeaders(),
                HttpStatus.OK);
    }

//    @GetMapping("filter")
//    public ResponseEntity<Object> findStudentsByFilter(@RequestParam(defaultValue = "") String firstName,
//                                                       @RequestParam(defaultValue = "") String lastName,
//                                                       @RequestParam(defaultValue = "") String email,
//                                                       @RequestParam(defaultValue = "") String city) {
//        try {
//            return ResponseEntity.ok().body(studentService.findStudentsByFilter(firstName, lastName, email, city));
//        } catch (NotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

//    @GetMapping("page")
//    public ResponseEntity<Page<StudentEntity>> findPage(@RequestParam(defaultValue = "0") Integer pageNo,
//                                                        @RequestParam(defaultValue = "5") Integer pageSize,
//                                                        @RequestParam(defaultValue = "lastName") String sortBy,
//                                                        @RequestParam(defaultValue = "asc") String sortOrder) {
//        return new ResponseEntity<>(studentService.findPage(pageNo, pageSize, sortBy, sortOrder), new HttpHeaders(),
//                HttpStatus.OK);
//    }
}
