package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;


import java.util.HashMap;
import java.util.Map;

@Repository
public class TagDAOImpl implements TagDAO {

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
    public Tag create(Tag tag) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", tag.getName());
            Number id = jdbcInsert.executeAndReturnKey(parameters);
            tag.setId(id.longValue());
            return tag;
    }

    @Override
    public Tag read(Long id) {
            return jdbcTemplate.queryForObject(READ_TAG_SQL, new BeanPropertyRowMapper<>(Tag.class), id);

    }

    @Override
    public long delete(Long id) {
        int rows = jdbcTemplate.update(DELETE_TAG_SQL, id);
        return rows > 0L ? id : -1L;
    }
}
