package kristina.trifunovic.controller;

import kristina.trifunovic.entity.CityEntity;
import kristina.trifunovic.exception.EntityExistsException;
import kristina.trifunovic.exception.UnknownEntityException;
import kristina.trifunovic.service.CityService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("cities")
public class CityRestController {
    private final CityService cityService;

    public CityRestController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping()
    public List<CityEntity> findAll() {
        return cityService.findAll();
    }

    @PostMapping()
    public @ResponseBody ResponseEntity<Object> save(@Valid @RequestBody CityEntity city) {
        try {
            CityEntity savedCity = cityService.save(city);
            return ResponseEntity.status(HttpStatus.OK).body(savedCity);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping()
    public @ResponseBody ResponseEntity<Object> update(@Valid @RequestBody CityEntity city) {
        try {
            CityEntity updatedCity = cityService.update(city);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCity);
        } catch (UnknownEntityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public @ResponseBody ResponseEntity<Object> delete(@Valid @PathVariable Long id) {
        try {
            Optional<CityEntity> existingCity = cityService.findById(id);
            cityService.delete(id);
            if (existingCity.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(existingCity.get() + " successfully deleted");
            } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("City not found");
        } catch (UnknownEntityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public @ResponseBody ResponseEntity<Object> findById(@Valid @PathVariable Long id) {
        Optional<CityEntity> cityFromDB = cityService.findById(id);
        if (cityFromDB.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(cityFromDB.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("City not found");
    }

    @GetMapping("page")
    public ResponseEntity<Page<CityEntity>> findPage(@RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "5") Integer pageSize,
                                                     @RequestParam(defaultValue = "postalCode") String sortBy,
                                                     @RequestParam(defaultValue = "asc") String sortOrder) {
        return new ResponseEntity<>(cityService.findPage(pageNo, pageSize, sortBy, sortOrder), new HttpHeaders(),
                HttpStatus.OK);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidation(MethodArgumentNotValidException ex) {
//        return getStringStringMap(ex);
//    }
//
//    static Map<String, String> getStringStringMap(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }

}
