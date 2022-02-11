package com.epam.esm.dao;

import com.epam.esm.entity.SortType;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDTO;

import java.util.List;

/**
 *
 */
public interface GiftCertificateDAO {

   GiftCertificate create(GiftCertificate giftCertificate);

   GiftCertificate read(Long id);

   long update(GiftCertificateDTO giftCertificateDTO);

   long delete(Long id);

   List<GiftCertificate> findByTag(String tag);

   List<GiftCertificate> searchByNameOrDescription(String part);

   List<GiftCertificate> sortByDate(SortType sortType);

   List<GiftCertificate> sortByName(SortType sortType);

}

