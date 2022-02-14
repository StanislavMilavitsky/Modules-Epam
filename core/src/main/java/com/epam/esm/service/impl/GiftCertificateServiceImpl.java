package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.GiftCertificateConverter;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.SortType;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import com.sun.istack.internal.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final static Logger logger = Logger.getLogger(GiftCertificateServiceImpl.class);
    private final GiftCertificateDAO giftCertificateDAO;
    private final GiftCertificateConverter giftCertificateConverter;

    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, GiftCertificateConverter giftCertificateConverter) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.giftCertificateConverter = giftCertificateConverter;
    }

    @Override
    public GiftCertificateDTO find(Long id) throws ServiceException {
        GiftCertificate giftCertificate = giftCertificateDAO.read(id);
        GiftCertificateDTO giftCertificateDTO = giftCertificateConverter.toDTO(giftCertificate);
        if (giftCertificateDTO != null) {
            return  giftCertificateDTO;
        } else {
            String exception = String.format("Find gift certificate by id =%d exception!", id);
                    logger.warning(exception);
            throw new ServiceException(exception);
        }
    }

    @Transactional
    @Override
    public GiftCertificateDTO add(GiftCertificateDTO giftCertificateDTO) throws ServiceException {
        GiftCertificate giftCertificate = giftCertificateConverter.fromDTO(giftCertificateDTO);
        GiftCertificate addedGiftCertificate = giftCertificateDAO.create(giftCertificate);
        if (addedGiftCertificate != null){
            return giftCertificateConverter.toDTO(addedGiftCertificate);
        } else {
            String exception = String.format("Add gift certificate %s exception!", giftCertificateDTO.getName());
            logger.warning(exception);
            throw new ServiceException(exception);
        }

    }

    @Transactional
    @Override
    public GiftCertificateDTO update(GiftCertificateDTO giftCertificateDTO) throws ServiceException {
        long id = giftCertificateDAO.update(giftCertificateDTO);
        if (id != -1L) {
            GiftCertificate certificate = giftCertificateDAO.read(id);
            return giftCertificateConverter.toDTO(certificate);
        } else {
            String exception = String.format("Update gift certificate by id=%d exception!", giftCertificateDTO.getId());
            logger.warning(exception);
            throw new ServiceException(exception);
        }

    }

    @Transactional
    @Override
    public long delete(Long id) throws ServiceException {
        long result = giftCertificateDAO.delete(id);
        if(result != -1L){
            return result;
        } else {
            String exception = String.format("Delete gift certificate by id=%d exception!", id);
            logger.warning(exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public List<GiftCertificateDTO> findByTag(String tagName) throws ServiceException {
        List<GiftCertificate> byTag = giftCertificateDAO.findByTag(tagName);
        if( byTag != null){
            return byTag.stream().map(giftCertificateConverter::toDTO).collect(Collectors.toList());
        } else {
            String exception = String.format("Find by tag %s exception!", tagName);
            logger.warning(exception);
            throw new ServiceException(exception);
        }

    }

    @Override
    public List<GiftCertificateDTO> searchByNameOrDescription(String part) throws ServiceException {
            List<GiftCertificate> giftCertificates = giftCertificateDAO.searchByNameOrDescription(part);
        if (giftCertificates != null) {
            return giftCertificates.stream().map(giftCertificateConverter::toDTO).collect(Collectors.toList());
        } else {
            String exception = String.format("Search by %s exception", part);
            logger.warning(exception);
            throw new ServiceException(exception);
        }
    }

    @Override
    public List<GiftCertificateDTO> sortByName(SortType sortType) throws ServiceException {
        List<GiftCertificate> giftCertificates = giftCertificateDAO.sortByName(sortType);
        if (giftCertificates != null){
            return giftCertificates.stream().map(giftCertificateConverter::toDTO).collect(Collectors.toList());
        } else {
            logger.warning("Sort by name exception!");
            throw new ServiceException("Sort by name exception!");
        }
    }

    @Override
    public List<GiftCertificateDTO> sortByDate(SortType sortType) throws ServiceException {
        List<GiftCertificate> giftCertificates = giftCertificateDAO.sortByDate(sortType);
        if (giftCertificates != null){
            return giftCertificates.stream().map(giftCertificateConverter::toDTO).collect(Collectors.toList());
        } else {
            logger.warning("Sort by date exception!");
            throw new ServiceException("Sort by date exception!");
        }
    }
}
