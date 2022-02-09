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
    private static final String SELECT_BY_NAME_OR_DESC_SQL = "SELECT gc.id, name, description, price, duration," +
            " create_date, last_update_date FROM gift_certificate gc WHERE name OR description LIKE ?";
    private static final String SORT_BY_DATE_SQL = "SELECT gc.id, name, description, price, duration, create_date, last_update_date" +
            " FROM gift_certificate gc ORDER BY gc.create_date ";
    private static final String SORT_BY_NAME_SQL = "SELECT gc.id, name, description, price, duration, create_date, last_update_date" +
            " FROM gift_certificate gc ORDER BY gc.name ";
    private static final String UPDATE_GIFT_CERTIFICATE_SQL = "UPDATE gift_certificate SET name = ?, description =?, price =?,duration =?," +
            " last_update_date =? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private List<GiftCertificate> giftCertificates;

    @Autowired
    public GiftCertificateDAOImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("gift_certificate")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate){
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

        }


    @Override
    public GiftCertificate read(Long id){
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
        List<GiftCertificate> giftCertificates =
                jdbcTemplate.query(SELECT_GIFT_CERTIFICATE_BY_TAG_SQL, new BeanPropertyRowMapper<>(GiftCertificate.class),
                tag);
        return giftCertificates;//todo как лушче возвращать
    }

    @Override
    public List<GiftCertificate> searchByNameOrDesc(String part) {
        String sqlPart = PERCENT + part + PERCENT;
        List<GiftCertificate> giftCertificates =
                jdbcTemplate.query(SELECT_BY_NAME_OR_DESC_SQL, new BeanPropertyRowMapper<>(GiftCertificate.class),
                        sqlPart);
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> sortByDate(SortType sortType) {
        StringBuilder builder = new StringBuilder(SORT_BY_DATE_SQL);
        if (sortType == SortType.DESC){
            builder.append(SortType.DESC.name());
        }
        List<GiftCertificate> giftCertificates =
                jdbcTemplate.query(builder.toString(), new BeanPropertyRowMapper<>(GiftCertificate.class));
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> sortByName(SortType sortType) {
        StringBuilder builder = new StringBuilder(SORT_BY_NAME_SQL);
        if(sortType == SortType.DESC){
            builder.append(SortType.DESC.name());
        }
        List<GiftCertificate> giftCertificates =
                jdbcTemplate.query(builder.toString(), new BeanPropertyRowMapper<>(GiftCertificate.class));
        return giftCertificates;
    }
}
