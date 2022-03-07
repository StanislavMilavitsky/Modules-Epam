package com.epam.esm.dao;

import com.epam.esm.exception.NotExistEntityException;

import java.util.List;
import java.util.Optional;

/**
 * Interface for all request classes for db
 * @param <T> class that make request
 */
public interface GenericDAO<T> {

    T create (T entity);

    Optional<T> findById(long id);

    T update (T entity);

    void delete(long id) throws NotExistEntityException;

    List<T> findAll(int offset, int limit);

    long getCountOfEntities();
}
