package com.tes.ebayuserauthservice.service;

import com.tes.ebayuserauthservice.exception.ModelIsNullException;
import com.tes.ebayuserauthservice.exception.ModelNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class GenericCrudService<T, ID> implements CrudService<T, ID> {
    private final JpaRepository<T, ID> repository;

    protected GenericCrudService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public T save(Optional<T> optionalT) {
        return optionalT.map(repository::save).orElseThrow(() -> new ModelIsNullException(
                "The " + optionalT + " model is null"
        ));
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id).orElseThrow(() -> new ModelNotFoundException(
                "A model with id " + id + " was not found"
        ));
    }

    @Transactional
    @Override
    public T update(Optional<T> optionalT) {
        return optionalT.map(repository::save).orElseThrow(() -> new ModelIsNullException(
                "The " + optionalT + " model is null"
        ));
    }

    @Transactional
    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }
}
