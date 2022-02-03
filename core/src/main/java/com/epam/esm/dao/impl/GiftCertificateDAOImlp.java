package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.exception.DAOException;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.dto.GiftCertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateDAOImlp implements GiftCertificateDAO {

    private static final String READ_GIFT_CERTIFICATE_BY_ID_SQL = "SELECT id, name, description, price, duration," +
            " create_date, last_update_date FROM gift_certificate WHERE id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateDAOImlp(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public GiftCertificate create(GiftCertificate giftCertificate) throws DAOException {
        return null;
    }

    public GiftCertificate read(Long id) throws DAOException {
        return jdbcTemplate.query(READ_GIFT_CERTIFICATE_BY_ID_SQL, new GiftCertificateMapper());
    }

    public boolean update(GiftCertificateDTO giftCertificateDTO) throws DAOException {
        return false;
    }

    public boolean delete(Long id) throws DAOException {
        return false;
    }
}
