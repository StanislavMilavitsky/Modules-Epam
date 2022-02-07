package com.epam.esm.dao;


import com.epam.esm.dao.exception.DAOException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.dto.GiftCertificateDTO;

public interface GiftCertificateDAO {

   GiftCertificate create(GiftCertificate giftCertificate) throws DAOException;

   GiftCertificate read(Long id) throws DAOException;

   long update(GiftCertificateDTO giftCertificateDTO) throws DAOException;

   long delete(Long id) throws DAOException;


}

