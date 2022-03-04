package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.common.SortType;
import com.epam.esm.exception.ServiceException;

import java.util.List;

/**
 *  Service layer use methods from dao layer
 */
public interface GiftCertificateService {
    /**
     * Use method read in dao layer
     * @param id is field gift certificate
     * @return entity from database
     * @throws ServiceException if entity by id has not been exist
     */
    GiftCertificateDTO find(Long id) throws ServiceException;

    /**
     * Use method create in dao layer
     * @param giftCertificateDTO entity gift certificate
     * @return created entity
     * @throws ServiceException if the entity has not been added to the database
     */
    GiftCertificateDTO add(GiftCertificateDTO giftCertificateDTO) throws ServiceException;

    /**
     * Use method update in dao layer
     * @param giftCertificateDTO entity gift certificate
     * @return updated entity
     * @throws ServiceException if the entity has not been updated to the database
     */
    GiftCertificateDTO update(GiftCertificateDTO giftCertificateDTO) throws ServiceException;

    /**
     * Use method delete in dao layer
     * @param id is field entity gift certificate
     * @return id deleted entity
     * @throws ServiceException  if the entity has not been deleted to the database
     */
    long delete(Long id) throws ServiceException;

    /**
     * Use method dao layer that find gift certificates
     * @param tagName word that must be search
     * @return list of found certificates
     * @throws ServiceException if the certificates has not been
     */
    List<GiftCertificateDTO> findByTag(String tagName) throws ServiceException;

    /**
     * Use method dao layer that find gift certificates by name or description
     * @param part of word that must be searched
     * @return list of found certificates
     * @throws ServiceException if the certificates has not been
     */
    List<GiftCertificateDTO> searchByNameOrDescription(String part) throws ServiceException;

    /**
     * Use method of dao layer and sort by name
     * @param sortType enum value of Class SortType
     * @return sorted list
     * @throws ServiceException if the certificates has not been
     */
    List<GiftCertificateDTO> sortByName(SortType sortType) throws ServiceException;

    /**
     * Use method of dao layer and sort by date
     * @param sortType enum value of Class SortType
     * @return sorted list
     * @throws ServiceException if the certificates has not been
     */
    List<GiftCertificateDTO> sortByDate(SortType sortType) throws ServiceException;

}
