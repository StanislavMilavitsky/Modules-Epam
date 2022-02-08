package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ServiceException;

public interface GiftCertificateService {

    GiftCertificateDTO find(Long id) throws ServiceException;

    GiftCertificateDTO add(GiftCertificateDTO certificateDTO) throws ServiceException;

    GiftCertificateDTO update(GiftCertificateDTO certificateDTO) throws ServiceException;

    long delete(Long id) throws ServiceException;

}
