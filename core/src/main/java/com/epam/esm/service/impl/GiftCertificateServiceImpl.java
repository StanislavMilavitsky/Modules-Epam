package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.GiftCertificateConverter;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.SortType;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.apache.log4j.Logger;
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
        return  giftCertificateDTO;
    }

    @Transactional
    @Override
    public GiftCertificateDTO add(GiftCertificateDTO giftCertificateDTO) throws ServiceException {
        GiftCertificate giftCertificate = giftCertificateConverter.fromDTO(giftCertificateDTO);
        GiftCertificate addedGiftCertificate = giftCertificateDAO.create(giftCertificate);
        return giftCertificateConverter.toDTO(addedGiftCertificate);
    }

    @Transactional
    @Override
    public GiftCertificateDTO update(GiftCertificateDTO giftCertificateDTO) throws ServiceException {
        long id = giftCertificateDAO.update(giftCertificateDTO);
        if (id == -1L) {
            return new GiftCertificateDTO();
        }
        GiftCertificate certificate = giftCertificateDAO.read(id);
        return giftCertificateConverter.toDTO(certificate);
    }

    @Transactional
    @Override
    public long delete(Long id) throws ServiceException {
        return giftCertificateDAO.delete(id);
    }

    @Override
    public List<GiftCertificateDTO> findByTag(String tagName) throws ServiceException {
        List<GiftCertificate> byTag = giftCertificateDAO.findByTag(tagName);
        return byTag.stream().map(giftCertificateConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDTO> searchByNameOrDescription(String part) throws ServiceException {
            List<GiftCertificate> certificates = giftCertificateDAO.searchByNameOrDescription(part);
            return certificates.stream().map(giftCertificateConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDTO> sortByName(SortType sortType) throws ServiceException {
        List<GiftCertificate> certificates = giftCertificateDAO.sortByName(sortType);
        return certificates.stream().map(giftCertificateConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDTO> sortByDate(SortType sortType) throws ServiceException {
        List<GiftCertificate> certificates = giftCertificateDAO.sortByDate(sortType);
        return certificates.stream().map(giftCertificateConverter::toDTO).collect(Collectors.toList());
    }


}
