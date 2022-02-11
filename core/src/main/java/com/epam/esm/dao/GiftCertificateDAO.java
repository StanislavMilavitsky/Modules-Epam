package com.epam.esm.dao;

import com.epam.esm.entity.SortType;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDTO;

import java.util.List;

/**
 * Work with entity gift certificate in layer DAO
 */
public interface GiftCertificateDAO {
   /**
    * Create an entity in database with fields from gift certificate
    * @param giftCertificate is field from gift certificate
    * @return entity gift certificate with id
    */
   GiftCertificate create(GiftCertificate giftCertificate);

   /**
    * Read an entity gift certificate from database by id
    * @param id field from entity
    * @return entity tag or null
    */
   GiftCertificate read(Long id);

   /**
    *  Update entity in database without id and create date
    * @param giftCertificateDTO entity from service layer
    * @return id if update and -1 if entity has not been updated
    */
   long update(GiftCertificateDTO giftCertificateDTO);

   /**
    * Delete entity from database by id
    * @param id field from entity gift certificate
    * @return id if entity was in database or -1 if entity has not exist
    */
   long delete(Long id);

   /**
    * Find entity find gift certificate by name tag
    * @param tag String value from filed entity tag
    * @return list of gift certificates
    */
   List<GiftCertificate> findByTag(String tag);

   /**
    * Search gift certificate in database by name or description
    * @param part it is String value of word
    * @return list of gift certificates
    */
   List<GiftCertificate> searchByNameOrDescription(String part);

   /**
    * Select entity from database and sort them by date
    * @param sortType is type from enum class SortType
    * @return list of sorted entity
    */
   List<GiftCertificate> sortByDate(SortType sortType);

   /**
    * Select entity from database and sort them by name
    * @param sortType is type from enum class SortType
    * @return list of sorted entity
    */
   List<GiftCertificate> sortByName(SortType sortType);

}

