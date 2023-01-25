package kristina.trifunovic.controller;

import kristina.trifunovic.entity.ProfessorEntity;
import kristina.trifunovic.exception.EntityExistsException;
import kristina.trifunovic.exception.UnknownEntityException;
import kristina.trifunovic.service.ProfessorService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("professors")
public class ProfessorRestController {
    private final ProfessorService professorService;

    public ProfessorRestController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping()
    public List<ProfessorEntity> findAll() {
        return professorService.findAll();
}

    @PostMapping()
    public @ResponseBody ResponseEntity<Object> save(@Valid @RequestBody ProfessorEntity professor) {
        try {
            ProfessorEntity savedProfessor = professorService.save(professor);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully saved " + savedProfessor);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping()
    public @ResponseBody ResponseEntity<Object> update(@Valid @RequestBody ProfessorEntity professor) {
        try {
            ProfessorEntity updatedProfessor = professorService.update(professor);
            return ResponseEntity.ok().body(updatedProfessor);
        } catch (UnknownEntityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{username}")
    public @ResponseBody ResponseEntity<Object> delete(@Valid @PathVariable String username) {
        try {
            professorService.delete(username);
            return ResponseEntity.ok().body("Successfully deleted professor with username " + username);
        } catch (UnknownEntityException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{username}")
    public @ResponseBody ResponseEntity<Object> findById(@Valid @PathVariable String username) {
        Optional<ProfessorEntity> professorFromDb = professorService.findById(username);
        if (professorFromDb.isPresent()) {
            return ResponseEntity.ok().body(professorFromDb.get());
        }
        return ResponseEntity.badRequest().body("Professor not found");
    }

    @GetMapping("page")
    public ResponseEntity<Page<ProfessorEntity>> findPage(@RequestParam(defaultValue = "0") Integer pageNo,
                                                          @RequestParam(defaultValue = "5") Integer pageSize,
                                                          @RequestParam(defaultValue = "lastName") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String sortOrder) {
        return new ResponseEntity<>(professorService.findPage(pageNo, pageSize, sortBy, sortOrder), new HttpHeaders(),
                HttpStatus.OK);
    }

    @GetMapping("subject/{subjectId}/professors")
    public @ResponseBody ResponseEntity<Object> findProfessorsBySubject(@Valid @PathVariable Integer subjectId) {
        return ResponseEntity.ok().body(professorService.findProfessorsBySubject(subjectId));
    }

}
