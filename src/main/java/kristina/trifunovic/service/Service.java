package kristina.trifunovic.service;

import java.util.List;
import java.util.Optional;

import kristina.trifunovic.entity.Entity;
import kristina.trifunovic.exception.EntityExistsException;
import kristina.trifunovic.exception.UnknownEntityException;
import org.springframework.data.domain.Page;

public interface Service<T extends Entity, ID> {
	List<T> findAll();
	T save(T t) throws EntityExistsException;
	T update(T t) throws UnknownEntityException;
	void delete(ID id) throws UnknownEntityException;
	Optional<T> findById(ID id);
	Page<T> findPage(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);
}
