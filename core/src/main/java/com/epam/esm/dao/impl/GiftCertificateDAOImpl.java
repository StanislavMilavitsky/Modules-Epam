package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.exception.DAOException;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.dto.GiftCertificateDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class GiftCertificateDAOImpl implements GiftCertificateDAO {

    private final static Logger logger = Logger.getLogger(GiftCertificateDAOImpl.class);

    private static final String READ_GIFT_CERTIFICATE_BY_ID_SQL = "SELECT id, name, description, price, duration," +
            " create_date, last_update_date FROM gift_certificate WHERE id = ?";
    private static final String DELETE_GIFT_CERTIFICATE_BY_ID_SQL = "DELETE FROM gift_certificate WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public GiftCertificateDAOImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("gift_certificate")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) throws DAOException {
        try{
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", giftCertificate.getName());
            parameters.put("description", giftCertificate.getDescription());
            parameters.put("price", giftCertificate.getPrice());
            parameters.put("duration", giftCertificate.getDuration());
            parameters.put("create_date", giftCertificate.getCreateDate());
            parameters.put("last_update_date", giftCertificate.getLastUpdateDate());
            Number id = jdbcInsert.executeAndReturnKey(parameters);
            giftCertificate.setId(id.longValue());
            return giftCertificate;
        } catch (DataAccessException ex){
            logger.error("Create gift certificate exception", ex);
            throw new DAOException("Create gift certificate exception", ex);
        }

    }

    @Override
    public GiftCertificate read(Long id) throws DAOException {
        try{
            return jdbcTemplate.queryForObject(READ_GIFT_CERTIFICATE_BY_ID_SQL, new GiftCertificateMapper(), id); //new BeanPropertyRowMapper<>(Person.class())
        } catch (DataAccessException ex){//todo
            logger.error("Read gift certificate exception", ex);
            throw new DAOException("Read gift certificate exception", ex);
        }

    }

    @Override
    public long update(GiftCertificateDTO giftCertificateDTO) throws DAOException {
        return false;
    }

    @Override
    public long delete(Long id) throws DAOException {
        try{
            int rows = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_BY_ID_SQL, id);
            return rows > 0L ? id : -1L;
        } catch (DataAccessException ex){
            logger.error("Delete gift certificate exception", ex);
            throw new DAOException("Delete gift certificate exception", ex);
        }
    }
}
