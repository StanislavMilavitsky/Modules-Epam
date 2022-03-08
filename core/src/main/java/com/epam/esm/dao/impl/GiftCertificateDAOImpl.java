package com.epam.esm.dao.impl;

import com.epam.esm.common.FilterParams;
import com.epam.esm.common.SortType;
import com.epam.esm.dao.GiftCertificateDAO;
import  com.epam.esm.common.GiftCertificateHandler;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

import static com.epam.esm.common.GiftCertificateConstant.*;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Repository
public class GiftCertificateDAOImpl extends GenericDAOImpl<GiftCertificate> implements GiftCertificateDAO {

    public GiftCertificateDAOImpl (){
       super(GiftCertificate.class);
   }


    @Override
    public List<GiftCertificate> filterByParameters(FilterParams filterParams, int offset, int limit) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        buildFilterQuery(builder, root, query, filterParams);
        return entityManager.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        Long id = giftCertificate.getId();
        GiftCertificate giftCertificateDB = findById(id).get();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        Map<String, Object> changeParameters = GiftCertificateHandler.defineChanges(giftCertificate, giftCertificateDB);
        if (!changeParameters.isEmpty()){
            CriteriaUpdate<GiftCertificate> update = criteriaBuilder.createCriteriaUpdate(GiftCertificate.class);
            update.from(GiftCertificate.class);
            changeParameters.forEach(update::set);
            entityManager.merge(giftCertificateDB);
        }
        return giftCertificateDB;
    }

    private void buildFilterQuery (CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root, CriteriaQuery<GiftCertificate> query,
                                   FilterParams filterParams) {
        query.select(root);
        Predicate predicatePart = criteriaBuilder.and();
        if (isNotEmpty(filterParams.getPart())){
            predicatePart = predicatePart(criteriaBuilder, root, filterParams.getPart());
        }
        Predicate predicateTag = criteriaBuilder.and();//todo
        if (filterParams.getTags() != null && !filterParams.getTags().isEmpty()){
            predicateTag = predicateTag(criteriaBuilder, root, filterParams.getTags());
        }
        query.where(criteriaBuilder.and(predicatePart, predicateTag));

        Path<Object> path = isNotEmpty(filterParams.getSortBy()) && filterParams.getSortBy().equalsIgnoreCase(DATE) ?
                root.get(CREATE_DATE) :
                root.get(NAME);
        query.orderBy(filterParams.getType() == SortType.DESC ? criteriaBuilder.desc(path) : criteriaBuilder.asc(path));
    }

    private Predicate predicatePart(CriteriaBuilder builder, Root<GiftCertificate> root, String part){
        String partLike = PERCENT + part + PERCENT;
        Predicate likeName = builder.like(root.get(NAME), partLike);
        Predicate likeDescription = builder.like(root.get(DESCRIPTION), partLike);
        return builder.or(likeName, likeDescription);
    }

    private Predicate predicateTag (CriteriaBuilder builder, Root<GiftCertificate> root, List<String> tags){
        Predicate predicate;
        if (tags.size() == 1) {
            predicate = builder.equal(root.join(TAGS).get(NAME), tags.get(0));
        } else {
            Predicate tagOne = builder.equal(root.join(TAGS).get(NAME), tags.get(0));
            Predicate tagTwo = builder.equal(root.join(TAGS).get(NAME), tags.get(1));
            predicate = builder.and(tagOne, tagTwo);
        }
        return predicate;
    }
}
