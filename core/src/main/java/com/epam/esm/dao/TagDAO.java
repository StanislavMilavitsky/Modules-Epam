package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DAOException;

/**
 * Work with entity tag in layer DAO
 */
public interface TagDAO {
    /**
     * Create an entity in database with fields from Tag
     * @param tag is entity
     * @return entity Tag with auto generated key
     * @throws DAOException when tag have not been created
     */
    Tag create(Tag tag) throws DAOException;

    /**
     * Read an entity Tag from database by id
     * @param id is field from Tag
     * @return entity Tag or null
     * @throws DAOException when id have not been found
     */
    Tag read(Long id) throws DAOException;

    /**
     * Delete an entity tag from database by id
     * @param id is field from Tag
     * @return id if more than 0 rows were changed or -1 if the rows were not changed
     * @throws DAOException when id have not been found
     */
    long delete(Long id) throws DAOException;
}

