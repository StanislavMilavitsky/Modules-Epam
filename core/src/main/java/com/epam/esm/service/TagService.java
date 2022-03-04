package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.IncorrectArgumentException;
import com.epam.esm.exception.NotExistEntityException;
import com.epam.esm.exception.ServiceException;

import java.util.List;

/**
 * Service layer use methods from dao layer
 */
public interface TagService {
    /**
     * Use method read from dao layer that find entity with this id or if object == null throws exception
     * @param id field entity
     * @return entity from database
     * @throws ServiceException when entity didnt find
     */
    TagDTO find(Long id) throws ServiceException;

    /**
     * Use method creat from dao layer and gave him entity
     * @param tagDTO entity
     * @return entity with generate key
     */
    TagDTO add(TagDTO tagDTO) throws ServiceException;

    /**
     * Use method delete from dao layer
     * @param id field entity
     * @return Id if the rows have changed and -1 if the rows have not changed
     * @throws ServiceException if id is not found
     */
    void delete(Long id) throws ServiceException, NotExistEntityException;

    List<TagDTO> findAll(int page, int size) throws ServiceException, IncorrectArgumentException;

    long count() throws ServiceException;
}
