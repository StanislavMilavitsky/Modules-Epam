package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.SortType;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {

    private final static Logger logger = LogManager.getLogger(TagDAOImpl.class);

    private static final String READ_GIFT_CERTIFICATE_BY_ID_SQL = "SELECT id, name, description, price, duration," +
            " create_date, last_update_date FROM gift_certificate WHERE id = ?";
    private static final String DELETE_GIFT_CERTIFICATE_BY_ID_SQL = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String SELECT_GIFT_CERTIFICATE_BY_TAG_SQL = "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration," +
            " gc.create_date, gc.last_update_date FROM gift_certificate gc " +
            "JOIN gift_certificate_has_tag gct ON gc.id = gct.id_gift_certificate " +
            "JOIN tag t ON gct.id_tag = t.id WHERE t.name = ?" ;
    private static final String PERCENT = "%";
    private static final String SELECT_BY_NAME_OR_DESCRIPTION_SQL = "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration," +
            " gc.create_date, gc.last_update_date FROM gift_certificate gc WHERE gc.name LIKE ? OR gc.description LIKE ?";
    private static final String SORT_BY_DATE_SQL = "SELECT gc.id, name, description, price, duration, create_date, last_update_date" +
            " FROM gift_certificate gc ORDER BY gc.create_date ";
    private static final String SORT_BY_NAME_SQL = "SELECT gc.id, name, description, price, duration, create_date, last_update_date" +
            " FROM gift_certificate gc ORDER BY gc.name ";
    private static final String UPDATE_GIFT_CERTIFICATE_SQL = "UPDATE gift_certificate SET name = ?, description =?, price =?, duration =?," +
            " last_update_date =? WHERE id = ?";

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String DURATION = "duration";
    public static final String CREATE_DATE = "create_date";
    public static final String LAST_UPDATE_DATE = "last_update_date";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public GiftCertificateDAOImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("gift_certificate")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) throws DAOException{
        try{
            Map<String, Object> parameters = new HashMap<>();
            parameters.put(NAME, giftCertificate.getName());
            parameters.put(DESCRIPTION, giftCertificate.getDescription());
            parameters.put(PRICE, giftCertificate.getPrice());
            parameters.put(DURATION, giftCertificate.getDuration());
            parameters.put(CREATE_DATE, giftCertificate.getCreateDate());
            parameters.put(LAST_UPDATE_DATE, giftCertificate.getLastUpdateDate());
            Number id = jdbcInsert.executeAndReturnKey(parameters);
            giftCertificate.setId(id.longValue());
            return giftCertificate;
        } catch (DataAccessException exception){
            String exceptionMessage = String.format("Create gift certificate by name=%s exception sql!", giftCertificate.getName());
            logger.error(exceptionMessage, exception);
            throw new DAOException(exceptionMessage, exception);
        }
        }


    @Override
    public GiftCertificate read(Long id) throws DAOException {
        try{
            return jdbcTemplate.queryForObject(READ_GIFT_CERTIFICATE_BY_ID_SQL, new BeanPropertyRowMapper<>(GiftCertificate.class), id);
        } catch (DataAccessException exception){
            String exceptionMessage = String.format("Read gift certificate by id=%d exception sql!", id);
            logger.error(exceptionMessage, exception);
            throw new DAOException(exceptionMessage, exception);
        }
    }

    @Override
    public long update(GiftCertificateDTO giftCertificateDTO) throws DAOException {
        try{
            int rows = jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_SQL, giftCertificateDTO.getName(), giftCertificateDTO.getDescription(),
                    giftCertificateDTO.getPrice(), giftCertificateDTO.getDuration(), LocalDateTime.parse(giftCertificateDTO.getLastUpdateDate()), giftCertificateDTO.getId());
            return rows > 0L ? giftCertificateDTO.getId() : -1L;
        } catch (DataAccessException exception){
            String exceptionMessage = String.format("Update gift certificate by name=%s exception sql!", giftCertificateDTO.getName());
            logger.error(exceptionMessage, exception);
            throw new DAOException(exceptionMessage, exception);
        }

    }

    @Override
    public long delete(Long id) throws DAOException {
        try{
            int rows = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_BY_ID_SQL, id);
            return rows > 0L ? id : -1L;
        } catch (DataAccessException exception){
            String exceptionMessage = String.format("Delete gift certificate by id=%d exception sql!", id);
            logger.error(exceptionMessage, exception);
            throw new DAOException(exceptionMessage, exception);
        }
    }

    @Override
    public List<GiftCertificate> findByTag(String tag) throws DAOException {
        try{
            return jdbcTemplate.query(SELECT_GIFT_CERTIFICATE_BY_TAG_SQL, new BeanPropertyRowMapper<>(GiftCertificate.class), tag);
        } catch (DataAccessException exception){
            String exceptionMessage = String.format("Find gift certificate by tag=%s exception sql!", tag);
            logger.error(exceptionMessage, exception);
            throw new DAOException(exceptionMessage, exception);
        }
    }

    @Override
    public List<GiftCertificate> searchByNameOrDescription(String part) throws DAOException {
        try{
            String sqlPart = PERCENT + part + PERCENT;
            return jdbcTemplate.query(SELECT_BY_NAME_OR_DESCRIPTION_SQL, new BeanPropertyRowMapper<>(GiftCertificate.class), sqlPart, sqlPart);
        } catch (DataAccessException exception){
            String exceptionMessage = String.format("Find gift certificate by word=%s exception sql!", part);
            logger.error(exceptionMessage, exception);
            throw new DAOException(exceptionMessage, exception);
        }
    }

    @Override
    public List<GiftCertificate> sortByDate(SortType sortType) throws DAOException {
        try{
            StringBuilder builder = new StringBuilder(SORT_BY_DATE_SQL);
            if (sortType == SortType.DESC){
                builder.append(SortType.DESC.name());
            }
            return jdbcTemplate.query(builder.toString(), new BeanPropertyRowMapper<>(GiftCertificate.class));
        } catch (DataAccessException exception){
            String exceptionMessage = "Sort gift certificate by date exception sql";
            logger.error(exceptionMessage, exception);
            throw new DAOException(exceptionMessage, exception);
        }
    }

    @Override
    public List<GiftCertificate> sortByName(SortType sortType) throws DAOException {
        try{
            StringBuilder builder = new StringBuilder(SORT_BY_NAME_SQL);
            if(sortType == SortType.DESC){
                builder.append(SortType.DESC.name());
            }
            return jdbcTemplate.query(builder.toString(), new BeanPropertyRowMapper<>(GiftCertificate.class));
        }  catch (DataAccessException exception){
            String exceptionMessage = "Sort gift certificate by name exception sql";
            logger.error(exceptionMessage, exception);
            throw new DAOException(exceptionMessage, exception);
        }
    }
}
