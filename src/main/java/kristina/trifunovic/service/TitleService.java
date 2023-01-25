package kristina.trifunovic.service;

import kristina.trifunovic.entity.TitleEntity;
import kristina.trifunovic.exception.EntityExistsException;
import kristina.trifunovic.exception.UnknownEntityException;
import kristina.trifunovic.repository.TitleRepository;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class TitleService implements Service<TitleEntity, Integer> {
    private final TitleRepository titleRepository;

    public TitleService(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    @Override
    public List<TitleEntity> findAll() {
        return titleRepository.findAll();
    }

    @Override
    public TitleEntity save(TitleEntity title) throws EntityExistsException {
        return null;
    }

    @Override
    public TitleEntity update(TitleEntity title) throws UnknownEntityException {
        return null;
    }

    @Override
    public void delete(Integer integer) throws UnknownEntityException {

    }

    @Override
    public Optional<TitleEntity> findById(Integer id) {
        Optional<TitleEntity> titleFromDb = titleRepository.findById(id);
        if (titleFromDb.isPresent()) {
            return titleFromDb;
        }
        return Optional.empty();
    }

    @Override
    public Page<TitleEntity> findPage(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }
}
