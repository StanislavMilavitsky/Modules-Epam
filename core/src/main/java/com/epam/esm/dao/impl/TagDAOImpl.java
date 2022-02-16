package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;


import java.util.HashMap;
import java.util.Map;

@Repository
public class TagDAOImpl implements TagDAO {
    private final static Logger logger = LogManager.getLogger(TagDAOImpl.class);

    private final static String READ_TAG_SQL = "SELECT id, name FROM tag WHERE id = ?";
    private final static String DELETE_TAG_SQL = "DELETE FROM tag WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public TagDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("tag")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Tag create(Tag tag) throws DAOException {
      try{
          Map<String, Object> parameters = new HashMap<>();
          parameters.put("name", tag.getName());
          Number id = jdbcInsert.executeAndReturnKey(parameters);
          tag.setId(id.longValue());
          return tag;
      } catch (DataAccessException exception) {
          String exceptionMessage = String.format("Create tag by name=%s exception sql!", tag.getName());
          logger.error(exceptionMessage, exception);
          throw new DAOException();
      }
    }

    @Override
    public Tag read(Long id) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(READ_TAG_SQL, new BeanPropertyRowMapper<>(Tag.class), id);
        } catch (EmptyResultDataAccessException exception) {
            String exceptionMessage = String.format("Find tag by id=%d exception sql!", id);
            logger.error(exceptionMessage, exception);
            throw new DAOException();
        }

    }

    @Override
    public long delete(Long id) throws DAOException {
        try {
            int rows = jdbcTemplate.update(DELETE_TAG_SQL, id);
            return rows > 0L ? id : -1L;
        } catch (EmptyResultDataAccessException exception) {
            String exceptionMessage = String.format("Delete tag by id=%d exception sql!", id);
            logger.error(exceptionMessage, exception);
            throw new DAOException();
        }
    }
}