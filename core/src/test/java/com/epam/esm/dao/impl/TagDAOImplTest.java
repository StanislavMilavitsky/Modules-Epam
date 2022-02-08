package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TagDAOImplTest {

    JdbcTemplate jdbcTemplate;
    DataSource dataSource;
    TagDAO tagDAO;

    @BeforeEach
    void setUp() {
            dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                    .generateUniqueName(true)
                    .addScript("classpath:tag-schema.sql")
                    .addScript("classpath:tag-test-data.sql")
                    .build();
            jdbcTemplate = new JdbcTemplate(dataSource);
        tagDAO = new TagDAOImpl(jdbcTemplate);
    }

    @Test
    void testCreatePositive(){
        Tag tag = new Tag();
        tag.setName("movie");
        Tag actual = this.tagDAO.create(tag);
        assertEquals(new Tag(6L,"movie"), actual);
    }

    @Test
    void read() {
        Tag actual = tagDAO.read(1L);
        Tag expected = new Tag(1L, "sport");
        assertEquals(expected, actual);
    }

    @Test
    void delete() {
        long actual = tagDAO.delete(3L);
        assertEquals(3L, actual);
    }
}