package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.SortType;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {

    private static final String READ_GIFT_CERTIFICATE_BY_ID_SQL = "SELECT id, name, description, price, duration," +
            " create_date, last_update_date FROM gift_certificate WHERE id = ?";
    private static final String DELETE_GIFT_CERTIFICATE_BY_ID_SQL = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String SELECT_GIFT_CERTIFICATE_BY_TAG_SQL = "SELECT gc.id, name, description, price, duration," +
            " create_date, last_update_date FROM gift_certificate gc " +
            "JOIN gift_certificate_has_tag gct ON gc.id = gct.id_gift_certificate " +
            "JOIN tag t ON gct.id_tag = t.id WHERE t.name = ?" ;
    private static final String PERCENT = "%";
    private static final String SELECT_BY_NAME_OR_DESCRIPTION_SQL = "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration," +
            " gc.create_date, gc.last_update_date FROM gift_certificate gc WHERE gc.name LIKE ?";
    private static final String SORT_BY_DATE_SQL = "SELECT gc.id, name, description, price, duration, create_date, last_update_date" +
            " FROM gift_certificate gc ORDER BY gc.create_date ";
    private static final String SORT_BY_NAME_SQL = "SELECT gc.id, name, description, price, duration, create_date, last_update_date" +
            " FROM gift_certificate gc ORDER BY gc.name ";
    private static final String UPDATE_GIFT_CERTIFICATE_SQL = "UPDATE gift_certificate SET name = ?, description =?, price =?, duration =?," +
            " last_update_date =? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public GiftCertificateDAOImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("gift_certificate")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate){
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", giftCertificate.getName());//todo изменить
            parameters.put("description", giftCertificate.getDescription());
            parameters.put("price", giftCertificate.getPrice());
            parameters.put("duration", giftCertificate.getDuration());
            parameters.put("create_date", giftCertificate.getCreateDate());
            parameters.put("last_update_date", giftCertificate.getLastUpdateDate());
            Number id = jdbcInsert.executeAndReturnKey(parameters);
            giftCertificate.setId(id.longValue());
            return giftCertificate;

        }


    @Override
    public GiftCertificate read(Long id){//todo если не подключится к базе данных exception ?
            return jdbcTemplate.queryForObject(READ_GIFT_CERTIFICATE_BY_ID_SQL, new BeanPropertyRowMapper<>(GiftCertificate.class), id);
    }

    @Override
    public long update(GiftCertificateDTO giftCertificateDTO)  {
         int rows = jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_SQL, giftCertificateDTO.getName(), giftCertificateDTO.getDescription(),
                giftCertificateDTO.getPrice(), giftCertificateDTO.getDuration(), giftCertificateDTO.getLastUpdateDate(), giftCertificateDTO.getId());
        return rows > 0L ? giftCertificateDTO.getId() : -1L;
    }

    @Override
    public long delete(Long id) {
        int rows = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_BY_ID_SQL, id);
        return rows > 0L ? id : -1L;
    }

    @Override
    public List<GiftCertificate> findByTag(String tag) {
        return jdbcTemplate.query(SELECT_GIFT_CERTIFICATE_BY_TAG_SQL, new BeanPropertyRowMapper<>(GiftCertificate.class),
        tag);
    }

    @Override
    public List<GiftCertificate> searchByNameOrDescription(String part) {
        String sqlPart = PERCENT + part + PERCENT;
        return jdbcTemplate.query(SELECT_BY_NAME_OR_DESCRIPTION_SQL, new BeanPropertyRowMapper<>(GiftCertificate.class),
                sqlPart);
    }

    @Override
    public List<GiftCertificate> sortByDate(SortType sortType) {
        StringBuilder builder = new StringBuilder(SORT_BY_DATE_SQL);
        if (sortType == SortType.DESC){
            builder.append(SortType.DESC.name());
        }
        return jdbcTemplate.query(builder.toString(), new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public List<GiftCertificate> sortByName(SortType sortType) {
        StringBuilder builder = new StringBuilder(SORT_BY_NAME_SQL);
        if(sortType == SortType.DESC){
            builder.append(SortType.DESC.name());
        }
        return jdbcTemplate.query(builder.toString(), new BeanPropertyRowMapper<>(GiftCertificate.class));
    }
}
