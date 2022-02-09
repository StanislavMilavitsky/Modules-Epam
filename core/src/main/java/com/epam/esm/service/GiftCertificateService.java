package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.SortType;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDTO find(Long id) throws ServiceException;

    GiftCertificateDTO add(GiftCertificateDTO certificateDTO) throws ServiceException;

    GiftCertificateDTO update(GiftCertificateDTO certificateDTO) throws ServiceException;

    long delete(Long id) throws ServiceException;

    List<GiftCertificateDTO> findByTag(String tagName) throws ServiceException;

    List<GiftCertificateDTO> searchByNameOrDesc(String part) throws ServiceException;

    List<GiftCertificateDTO> sortByName(SortType sortType) throws ServiceException;

    List<GiftCertificateDTO> sortByDate(SortType sortType) throws ServiceException;

}
