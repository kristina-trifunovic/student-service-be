package kristina.trifunovic.service;

import kristina.trifunovic.entity.ProfessorEntity;
import kristina.trifunovic.exception.EntityExistsException;
import kristina.trifunovic.exception.UnknownEntityException;
import kristina.trifunovic.repository.ProfessorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ProfessorService implements Service<ProfessorEntity, String> {
    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfessorService(ProfessorRepository professorRepository, PasswordEncoder passwordEncoder) {
        this.professorRepository = professorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<ProfessorEntity> findAll() {
        return professorRepository.findAll();
    }

    @Override
    public ProfessorEntity save(ProfessorEntity professor) throws EntityExistsException {
        Optional<ProfessorEntity> existingProfessor = professorRepository.findById(professor.getUsername());
        if (existingProfessor.isPresent()) {
            throw new EntityExistsException("Professor " + professor.getFirstName() + " " + professor.getLastName() + " already exists", professor);
        }
        professor.setPassword(passwordEncoder.encode(professor.getPassword()));
        return professorRepository.save(professor);
    }

    @Override
    public ProfessorEntity update(ProfessorEntity professor) throws UnknownEntityException {
        Optional<ProfessorEntity> existingProfessor = professorRepository.findById(professor.getUsername());
        if (!existingProfessor.isPresent()) {
            throw new UnknownEntityException("Professor does not exist", professor);
        }
        professor.setPassword(passwordEncoder.encode(professor.getPassword()));
        return professorRepository.save(professor);
    }

    @Override
    public void delete(String username) throws UnknownEntityException {
        Optional<ProfessorEntity> professorToDelete = professorRepository.findById(username);
        if (professorToDelete.isPresent()) {
            professorRepository.delete(professorToDelete.get());
        } else throw new UnknownEntityException("Professor not found", null);
    }

    @Override
    public Optional<ProfessorEntity> findById(String username) {
        Optional<ProfessorEntity> professorFromDb = professorRepository.findById(username);
        if (professorFromDb.isPresent()) {
            return professorFromDb;
        }
        return Optional.empty();
    }

    @Override
    public Page<ProfessorEntity> findPage(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        return professorRepository.findAll(pageable);
    }

    public List<ProfessorEntity> findProfessorsBySubject(Integer id) {
        return professorRepository.findProfessorsBySubject(id);
    }
}
