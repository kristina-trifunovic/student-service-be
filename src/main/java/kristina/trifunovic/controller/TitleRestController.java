package kristina.trifunovic.controller;

import kristina.trifunovic.entity.TitleEntity;
import kristina.trifunovic.service.TitleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import static kristina.trifunovic.controller.CityRestController.getStringStringMap;

@RestController
@RequestMapping("titles")
public class TitleRestController {
    private final TitleService titleService;

    public TitleRestController(TitleService titleService) {
        this.titleService = titleService;
    }

    @GetMapping
    public List<TitleEntity> findAll() {
        return titleService.findAll();
    }

    @GetMapping("{id}")
    public @ResponseBody ResponseEntity<Object> findById(@Valid @PathVariable Integer id) {
        Optional<TitleEntity> titleFromDb = titleService.findById(id);
        if (titleFromDb.isPresent()) {
            return ResponseEntity.ok().body(titleFromDb.get());
        }
        return ResponseEntity.badRequest().body("Title not found");
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidation(MethodArgumentNotValidException ex) {
//        return getStringStringMap(ex);
//    }
}
