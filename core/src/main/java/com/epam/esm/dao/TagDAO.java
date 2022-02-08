package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

/**
 * Work with entity tag in layer DAO
 */
public interface TagDAO {
    /**
     * Create an entity from database with fields from Tag
     * @param tag is entity
     * @return entity Tag with auto generated key
     */
    Tag create(Tag tag);

    /**
     * Read an entity Tag from database by id
     * @param id is field from Tag
     * @return entity Tag
     */
    Tag read(Long id);

    /**
     * Delete an entity tag from database by id
     * @param id is field from Tag
     * @return id if more than 0 rows were changed or -1 if the rows were not changed
     */
    long delete(Long id);
}

