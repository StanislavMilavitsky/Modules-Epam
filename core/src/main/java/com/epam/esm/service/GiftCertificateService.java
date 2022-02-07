package com.epam.esm.service;

public interface GiftCertificateService {

    CertificateDTO find(Long id) throws ServiceException;

    CertificateDTO add(CertificateDTO certificateDTO) throws ServiceException;

    CertificateDTO update(CertificateDTO certificateDTO) throws ServiceException;

    long delete(Long id) throws ServiceException;

}
