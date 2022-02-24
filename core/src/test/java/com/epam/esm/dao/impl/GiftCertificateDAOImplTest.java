package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.SortType;
import com.epam.esm.exception.DAOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class GiftCertificateDAOImplTest {

    JdbcTemplate jdbcTemplate;
    DataSource dataSource;
    GiftCertificateDAO giftCertificateDAO;
    GiftCertificate certificate;
    GiftCertificate expectedTwo;
    GiftCertificate expectedOne;

    @BeforeEach
    void setUp() {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .generateUniqueName(true)
                .addScript("classpath:gift-certificate-schema.sql")
                .addScript("classpath:test-gift-certificate-data.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(dataSource);
        giftCertificateDAO = new GiftCertificateDAOImpl(jdbcTemplate);
        certificate = new GiftCertificate(null, "name", "description", new BigDecimal("1"),
                2, LocalDateTime.parse("2022-02-11T12:15:00"), LocalDateTime.parse("2022-08-15T12:15:00"));
        expectedTwo = new GiftCertificate(2L, "Sport Gift",
                "For the amount of 100$ includes yoga", new BigDecimal("100.00"), 2,
                LocalDateTime.parse("2022-01-25T12:15:00"), LocalDateTime.parse("2022-02-15T12:15:00"));
        expectedOne = new GiftCertificate(1L, "Music gift",
                "Include most popular album of 10 groups", new BigDecimal("50.00"), 12,
                LocalDateTime.parse("2022-02-19T12:15:00"), LocalDateTime.parse("2022-03-10T12:15:00"));
    }

    @Test
    void testCreatPositive() throws DAOException {
        GiftCertificate actual = giftCertificateDAO.create(this.certificate);
        assertEquals(certificate, actual);
    }

    @Test
    void testCreatNegative() throws DAOException {
        GiftCertificate actual = giftCertificateDAO.create(this.certificate);
        assertNotEquals(expectedOne, actual);
    }

    @Test
    void testReadPositive() throws DAOException {
        GiftCertificate actual = giftCertificateDAO.read(2L);
        assertEquals(expectedTwo, actual);
    }

    @Test
    void testReadNegative() throws DAOException {
        GiftCertificate actual = giftCertificateDAO.read(2L);
        assertNotEquals(certificate, actual);
    }

    @Test
    void testUpdatePositive() throws DAOException {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(1L, "Musical gift", "Any", new BigDecimal("1"),
                2, "2022-02-20 13:00:00", "2022-03-20 14:00:00");
        long actual = giftCertificateDAO.update(giftCertificateDTO);
        assertEquals(1L, actual);
    }

    @Test
    void testDeletePositive() throws DAOException {
        long actual = giftCertificateDAO.delete(1L);
        assertEquals(1L, actual);
    }

    @Test
    void testSortByDatePositive() throws DAOException {
        List<GiftCertificate> actual = giftCertificateDAO.sortByDate(SortType.ASC);
        List<GiftCertificate> expected = new ArrayList<>();
        expected.add(expectedTwo);
        expected.add(expectedOne);
        assertEquals(expected, actual);
    }

    @Test
    void testSortByNamePositive() throws DAOException {
        List<GiftCertificate> actual = giftCertificateDAO.sortByName(SortType.ASC);
        List<GiftCertificate> expected = new ArrayList<>();
        expected.add(expectedOne);
        expected.add(expectedTwo);
        assertEquals(expected, actual);
    }
}