package kristina.trifunovic.service;

import kristina.trifunovic.entity.CityEntity;
import kristina.trifunovic.exception.EntityExistsException;
import kristina.trifunovic.exception.UnknownEntityException;
import kristina.trifunovic.repository.CityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class CityService implements Service<CityEntity, Long> {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<CityEntity> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public CityEntity save(CityEntity city) throws EntityExistsException {
        Optional<CityEntity> existingCity = cityRepository.findById(city.getPostalCode());
        if (existingCity.isPresent()) {
            throw new EntityExistsException("City with postal code " + city.getPostalCode() + " already exists", city);
        }
        return cityRepository.save(city);
    }

    @Override
    public CityEntity update(CityEntity city) throws UnknownEntityException {
        Optional<CityEntity> existingCity = cityRepository.findById(city.getPostalCode());
        if (!existingCity.isPresent()) {
            throw new UnknownEntityException("City does not exist", city);
        }
        return cityRepository.save(city);
    }

    @Override
    public void delete(Long id) throws UnknownEntityException {
        Optional<CityEntity> cityToDelete = cityRepository.findById(id);
        if (cityToDelete.isPresent()) {
            cityRepository.delete(cityToDelete.get());
        } else throw new UnknownEntityException("City does not exist", null);
    }

    @Override
    public Optional<CityEntity> findById(Long id) {
        Optional<CityEntity> cityFromDb = cityRepository.findById(id);
        if (cityFromDb.isPresent()) {
            return cityFromDb;
        }
        return Optional.empty();
    }

    @Override
    public Page<CityEntity> findPage(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        return cityRepository.findAll(pageable);
    }
}
