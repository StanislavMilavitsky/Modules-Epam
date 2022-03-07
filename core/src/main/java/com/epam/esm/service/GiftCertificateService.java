package com.epam.esm.service;

import com.epam.esm.common.FilterParams;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.IncorrectArgumentException;
import com.epam.esm.exception.NotExistEntityException;
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
    GiftCertificateDTO find(Long id) throws ServiceException, NotExistEntityException;

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
    GiftCertificateDTO update(GiftCertificateDTO giftCertificateDTO) throws ServiceException, NotExistEntityException;

    /**
     * Use method delete in dao layer
     * @param id is field entity gift certificate
     * @throws ServiceException  if the entity has not been deleted to the database
     */
    void delete(Long id) throws ServiceException, NotExistEntityException;

    /**
     * Filter list by params
     * @param filterParams params from request for filter
     * @param page
     * @param size
     * @return Filtered list gift certificate
     * @throws ServiceException filter by parameters wrong
     * @throws IncorrectArgumentException if incorrect argument
     */
    List <GiftCertificateDTO> filterByParameters(FilterParams filterParams, int page, int size) throws ServiceException, IncorrectArgumentException;

    /**
     * Count of certificate
     * @return count certificate
     * @throws ServiceException if cant sum count
     */
    long count() throws ServiceException;
}
