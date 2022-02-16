package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.GiftCertificateConverter;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.SortType;
import com.epam.esm.exception.DAOException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final static Logger logger = LogManager.getLogger(GiftCertificateServiceImpl.class);
    private final GiftCertificateDAO giftCertificateDAO;
    private final GiftCertificateConverter giftCertificateConverter;

    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, GiftCertificateConverter giftCertificateConverter) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.giftCertificateConverter = giftCertificateConverter;
    }

    @Override
    public GiftCertificateDTO find(Long id) throws ServiceException {
        try {
            GiftCertificate giftCertificate = giftCertificateDAO.read(id);
            return giftCertificateConverter.toDTO(giftCertificate);
        } catch (DAOException exception) {
            String exceptionMessage = String.format("Find gift certificate by id=%d exception!", id);
            logger.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Transactional
    @Override
    public GiftCertificateDTO add(GiftCertificateDTO giftCertificateDTO) throws ServiceException {
        try {
            GiftCertificate giftCertificate = giftCertificateConverter.fromDTO(giftCertificateDTO);
            GiftCertificate addedGiftCertificate = giftCertificateDAO.create(giftCertificate);
            return giftCertificateConverter.toDTO(addedGiftCertificate);
        } catch (DAOException exception) {
            String exceptionMessage = String.format("Add gift certificate by name=%s exception!", giftCertificateDTO.getName());
            logger.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Transactional
    @Override
    public GiftCertificateDTO update(GiftCertificateDTO giftCertificateDTO) throws ServiceException {
        try {
            long id = giftCertificateDAO.update(giftCertificateDTO);
            GiftCertificate certificate = giftCertificateDAO.read(id);
            return giftCertificateConverter.toDTO(certificate);
        } catch (DAOException exception) {
            String exceptionMessage = String.format("Update gift certificate by name=%s exception!", giftCertificateDTO.getName());
            logger.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Transactional
    @Override
    public long delete(Long id) throws ServiceException {
        try {
            return giftCertificateDAO.delete(id);
        } catch (DAOException exception) {
            String exceptionMessage = String.format("Delete gift certificate by id=%d exception!", id);
            logger.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public List<GiftCertificateDTO> findByTag(String tagName) throws ServiceException {
        try {
            List<GiftCertificate> byTag = giftCertificateDAO.findByTag(tagName);
            return byTag.stream().map(giftCertificateConverter::toDTO).collect(Collectors.toList());
        } catch (DAOException exception) {
            String exceptionMessage = String.format("Find gift certificate by tag=%s exception!", tagName);
            logger.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public List<GiftCertificateDTO> searchByNameOrDescription(String part) throws ServiceException {
        try {
            List<GiftCertificate> giftCertificates = giftCertificateDAO.searchByNameOrDescription(part);
            return giftCertificates.stream().map(giftCertificateConverter::toDTO).collect(Collectors.toList());
        } catch (DAOException exception) {
            String exceptionMessage = String.format("Find gift certificate by word=%s exception!", part);
            logger.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public List<GiftCertificateDTO> sortByName(SortType sortType) throws ServiceException {
        try {
            List<GiftCertificate> giftCertificates = giftCertificateDAO.sortByName(sortType);
            return giftCertificates.stream().map(giftCertificateConverter::toDTO).collect(Collectors.toList());
        } catch (DAOException exception) {
            String exceptionMessage = "Sort gift certificate by name";
            logger.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public List<GiftCertificateDTO> sortByDate(SortType sortType) throws ServiceException {
        try {
            List<GiftCertificate> giftCertificates = giftCertificateDAO.sortByDate(sortType);
            return giftCertificates.stream().map(giftCertificateConverter::toDTO).collect(Collectors.toList());
        } catch (DAOException exception) {
            String exceptionMessage = "Sort gift certificate by date";
            logger.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }
}
