package com.epam.esm.service.impl;

import com.epam.esm.common.FilterParams;
import com.epam.esm.converter.impl.GiftCertificateDTOMapper;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.IncorrectArgumentException;
import com.epam.esm.exception.NotExistEntityException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;

import com.epam.esm.service.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final GiftCertificateDTOMapper giftCertificateDTOMapper;

    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, GiftCertificateDTOMapper giftCertificateDTOMapper) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.giftCertificateDTOMapper = giftCertificateDTOMapper;
    }

    @Override
    public GiftCertificateDTO find(Long id) throws ServiceException, NotExistEntityException {
        try {
           Optional<GiftCertificate> giftCertificate = giftCertificateDAO.findById(id);
            return giftCertificate.map(giftCertificateDTOMapper :: toDTO)
                    .orElseThrow(() -> new NotExistEntityException("Cant find gift certificate by id = " + id));
        } catch (DataAccessException exception) {
            String exceptionMessage = String.format("Cant find gift certificate by id=%d !", id);
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public GiftCertificateDTO add(GiftCertificateDTO giftCertificateDTO) throws ServiceException {
        try {
            GiftCertificate giftCertificate = giftCertificateDTOMapper.fromDTO(giftCertificateDTO);
            GiftCertificate addedGiftCertificate = giftCertificateDAO.create(giftCertificate);
            return giftCertificateDTOMapper.toDTO(addedGiftCertificate);
        } catch (DataAccessException exception) {
            String exceptionMessage = String.format("Add gift certificate by name=%s exception!", giftCertificateDTO.getName());
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public GiftCertificateDTO update(GiftCertificateDTO giftCertificateDTO) throws ServiceException, NotExistEntityException {
        try {
            GiftCertificate giftCertificate = giftCertificateDTOMapper.fromDTO(giftCertificateDTO);
            GiftCertificate updatedGiftCertificate = giftCertificateDAO.update(giftCertificate);
            return find(updatedGiftCertificate.getId());
        } catch (DataAccessException exception) {
            String exceptionMessage = String.format("Update gift certificate by name=%s exception!", giftCertificateDTO.getName());
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException, NotExistEntityException {
        try {
            giftCertificateDAO.delete(id);
        } catch (DataAccessException exception) {
            String exceptionMessage = String.format("Delete gift certificate by id=%d exception!", id);
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public List <GiftCertificateDTO> filterByParameters(FilterParams filterParams, int page, int size) throws ServiceException,
            IncorrectArgumentException {
        try {
            long count = count();
            Page giftCertificatePage = new Page(page, size, count);
            List<GiftCertificate> giftCertificates = giftCertificateDAO.filterByParameters(filterParams, giftCertificatePage.getOffset(),
                    giftCertificatePage.getLimit());
            return giftCertificates.stream().map(giftCertificateDTOMapper::toDTO).collect(Collectors.toList());
        } catch (DataAccessException exception) {
            String exceptionMessage = "Filter by parameters exception!";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public long count() throws ServiceException{
        try {
            return giftCertificateDAO.getCountOfEntities();
        } catch (DataAccessException exception){
            String exceptionMessage = "Count certificates service exception!";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

}
