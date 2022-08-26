package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.common.FilterParams;

import java.util.List;

/**
 * Work with entity gift certificate in layer DAO
 */
public interface GiftCertificateDAO extends GenericDAO<GiftCertificate> {

    /**
     * Filer request by parameters
     * @param filterParams entity with parameters for filter
     * @param offset
     * @param limit
     * @return filtered list of gift certificates
     */
    List<GiftCertificate> filterByParameters(FilterParams filterParams, int offset, int limit);
}

