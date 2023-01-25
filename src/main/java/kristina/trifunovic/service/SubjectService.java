package kristina.trifunovic.service;

import kristina.trifunovic.entity.SubjectEntity;
import kristina.trifunovic.exception.EntityExistsException;
import kristina.trifunovic.exception.UnknownEntityException;
import kristina.trifunovic.repository.SubjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class SubjectService implements kristina.trifunovic.service.Service<SubjectEntity, Integer> {
    public static final String DIRECTORY = "C:/Kristina/Kristina-Trifunovic-BE/src/main/resources/literature";
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<SubjectEntity> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public SubjectEntity save(SubjectEntity subject) throws EntityExistsException {
        Optional<SubjectEntity> subjectFromDb = subjectRepository.findById(subject.getId());
        if (subjectFromDb.isPresent()) {
            throw new EntityExistsException("Subject already exists", subjectFromDb);
        }
        return subjectRepository.save(subject);
    }

    @Override
    public SubjectEntity update(SubjectEntity subject) throws UnknownEntityException {
        Optional<SubjectEntity> subjectToUpdate = subjectRepository.findById(subject.getId());
        if (subjectToUpdate.isPresent()) {
            return subjectRepository.save(subject);
        }
        throw new UnknownEntityException("Subject does not exist", subject);
    }

    @Override
    public void delete(Integer id) throws UnknownEntityException {
        Optional<SubjectEntity> subjectToDelete = subjectRepository.findById(id);
        if (subjectToDelete.isPresent()) {
            subjectRepository.delete(subjectToDelete.get());
        } else throw new UnknownEntityException("Subject does not exist", null);
    }

    @Override
    public Optional<SubjectEntity> findById(Integer id) {
        Optional<SubjectEntity> subjectFromDb = subjectRepository.findById(id);
        if (subjectFromDb.isPresent()) {
            return subjectFromDb;
        }
        return Optional.empty();
    }

    @Override
    public Page<SubjectEntity> findPage(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        return subjectRepository.findAll(pageable);
    }

    public List<SubjectEntity> findAllSubjectsWithNoProfessor() {
        return subjectRepository.findAllSubjectsWithNoProfessor();
    }

    public List<SubjectEntity> findAllSubjectsByProfessor(String username) {
        return subjectRepository.findSubjectsByProfessor(username);
    }

    public SubjectEntity uploadLiteratureToSubject(SubjectEntity subject, MultipartFile literature) throws UnknownEntityException, IOException {
        String fileName = StringUtils.cleanPath(literature.getOriginalFilename());
        Path fileStorage = get(DIRECTORY, fileName).toAbsolutePath().normalize();
        try {
            copy(literature.getInputStream(), fileStorage, REPLACE_EXISTING);
            subject.setLiterature(fileStorage.toString());
        } catch (IOException  e) {
            throw new IOException(e.getMessage());
        }
        return subjectRepository.save(subject);
    }
}
