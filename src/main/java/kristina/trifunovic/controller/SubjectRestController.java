package kristina.trifunovic.controller;

import kristina.trifunovic.entity.ProfessorEntity;
import kristina.trifunovic.entity.SubjectEntity;
import kristina.trifunovic.exception.EntityExistsException;
import kristina.trifunovic.exception.UnknownEntityException;
import kristina.trifunovic.service.ProfessorService;
import kristina.trifunovic.service.SubjectService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("subjects")
public class SubjectRestController {
    private final SubjectService subjectService;
    private final ProfessorService professorService;

    public SubjectRestController(SubjectService subjectService, ProfessorService professorService) {
        this.subjectService = subjectService;
        this.professorService = professorService;
    }

    @GetMapping
    public List<SubjectEntity> findAll() {
        return subjectService.findAll();
    }

    @PostMapping
    public @ResponseBody ResponseEntity<Object> save( @RequestBody SubjectEntity subject) {
        try {
            SubjectEntity savedSubject = subjectService.save(subject);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully added " + savedSubject);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping()
    public @ResponseBody ResponseEntity<Object> update(@Valid @RequestBody SubjectEntity subject) {
        try {
            SubjectEntity updatedSubject = subjectService.update(subject);
            return ResponseEntity.ok().body(updatedSubject);
        } catch (UnknownEntityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public @ResponseBody ResponseEntity<Object> delete(@Valid @PathVariable Integer id) {
        try {
            subjectService.delete(id);
            return ResponseEntity.ok().body("Successfully deleted subject with id " + id);
        } catch (UnknownEntityException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public @ResponseBody ResponseEntity<Object> findById(@Valid @PathVariable Integer id) {
        Optional<SubjectEntity> subjectFromDb = subjectService.findById(id);
        if (subjectFromDb.isPresent()) {
            return ResponseEntity.ok().body(subjectFromDb.get());
        }
        return ResponseEntity.badRequest().body("Subject not found");
    }

    @GetMapping("page")
    public ResponseEntity<Page<SubjectEntity>> findPage(@RequestParam(defaultValue = "0") Integer pageNo,
                                                        @RequestParam(defaultValue = "5") Integer pageSize,
                                                        @RequestParam(defaultValue = "name") String sortBy,
                                                        @RequestParam(defaultValue = "asc") String sortOrder) {
        return new ResponseEntity<>(subjectService.findPage(pageNo, pageSize, sortBy, sortOrder), new HttpHeaders(),
                HttpStatus.OK);
    }

    @PostMapping("/{professorUsername}/subjects")
    public @ResponseBody ResponseEntity<Object> addSubjectsToProfessor(
            @Valid @PathVariable(name = "professorUsername") String professorUsername,
            @RequestBody List<SubjectEntity> subjects) {

        Optional<ProfessorEntity> professor = professorService.findById(professorUsername);
        if (professor.isPresent()) {
            for (SubjectEntity subject : subjects) {
                Optional<SubjectEntity> subjectFromDb = subjectService.findById(subject.getId());
                if (subjectFromDb.isPresent()) {
                    professor.get().addSubject(subjectFromDb.get());
                } else return ResponseEntity.badRequest().body("Subject not found");
            }
            try {
                ProfessorEntity updatedProfessor = professorService.update(professor.get());
                return ResponseEntity.ok().body(updatedProfessor);
            } catch (UnknownEntityException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else return ResponseEntity.badRequest().body("Professor not found");
    }

    @GetMapping("{professorUsername}/subjects")
    public ResponseEntity<List<SubjectEntity>> findAllSubjectsByProfessorId(@PathVariable(name = "professorUsername") String username) {
        List<SubjectEntity> subjects = subjectService.findAllSubjectsByProfessor(username);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @GetMapping("/no-professors")
    public List<SubjectEntity> findAllSubjectsWithNoProfessor() {
        return subjectService.findAllSubjectsWithNoProfessor();
    }

    @PostMapping(value = "{subjectId}/literature", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadLiteratureToSubject(@PathVariable Integer subjectId, @RequestParam("literature") MultipartFile literature) {
        Optional<SubjectEntity> subject = subjectService.findById(subjectId);
        if (subject.isPresent()) {
            try {
                return ResponseEntity.ok(subjectService.uploadLiteratureToSubject(subject.get(), literature));
            } catch (UnknownEntityException | IOException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else return ResponseEntity.badRequest().body("Subject not found");
    }
}
