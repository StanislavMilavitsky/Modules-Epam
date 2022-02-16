package com.epam.esm.dao;

import com.epam.esm.entity.SortType;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.DAOException;

import java.util.List;

/**
 * Work with entity gift certificate in layer DAO
 */
public interface GiftCertificateDAO {
   /**
    * Create an entity in database with fields from gift certificate
    * @param giftCertificate is field from gift certificate
    * @return entity gift certificate with id
    * @throws DAOException if certificate have not been created
    */
   GiftCertificate create(GiftCertificate giftCertificate) throws DAOException;

   /**
    * Read an entity gift certificate from database by id
    * @param id field from entity
    * @return entity tag or null
    *@throws DAOException if certificate have not been found
    */
   GiftCertificate read(Long id) throws DAOException;

   /**
    * Update entity in database without id and create date
    * @param giftCertificateDTO entity from service layer
    * @return id if update and -1 if entity has not been updated
    * @throws DAOException if certificate have not been update
    */
   long update(GiftCertificateDTO giftCertificateDTO) throws DAOException;

   /**
    * Delete entity from database by id
    * @param id field from entity gift certificate
    * @return id if entity was in database or -1 if entity has not exist
    * @throws DAOException if certificate have not been deleted
    */
   long delete(Long id) throws DAOException;

   /**
    * Find entity find gift certificate by name tag
    * @param tag String value from filed entity tag
    * @return list of gift certificates
    * @throws DAOException if certificate have not been found by tag
    */
   List<GiftCertificate> findByTag(String tag) throws DAOException;

   /**
    * Search gift certificate in database by name or description
    * @param part it is String value of word
    * @return list of gift certificates
    * @throws DAOException if certificate have not been found
    */
   List<GiftCertificate> searchByNameOrDescription(String part) throws DAOException;

   /**
    * Select entity from database and sort them by date
    * @param sortType is type from enum class SortType
    * @return list of sorted entity
    * @throws DAOException if certificate have not been sorted
    */
   List<GiftCertificate> sortByDate(SortType sortType) throws DAOException;

   /**
    * Select entity from database and sort them by name
    * @param sortType is type from enum class SortType
    * @return list of sorted entity
    * @throws DAOException if certificate have not been sorted
    */
   List<GiftCertificate> sortByName(SortType sortType) throws DAOException;

}

