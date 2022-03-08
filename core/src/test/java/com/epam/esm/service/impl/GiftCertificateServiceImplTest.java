package com.epam.esm.service.impl;

import com.epam.esm.common.FilterParams;
import com.epam.esm.converter.impl.GiftCertificateDTOMapper;
import com.epam.esm.converter.impl.TagDTOMapper;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.IncorrectArgumentException;
import com.epam.esm.exception.NotExistEntityException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    private static final String NAME = "Sport gym";
    private static final String DESCRIPTION = "Include 30 minute swimming pool";
    private static final String PRICE = "99";
    private static final String CREATE_DATE = "2022-02-08T12:15:00.0";
    private static final String LAST_UPDATE_DATE = "2022-03-08T12:15:00.0";

    @Mock
    GiftCertificateDAO dao;
    GiftCertificateService service;
    GiftCertificateDTOMapper certificateDTOMapper;
    TagDTOMapper tagDTOMapper;
    GiftCertificate giftCertificate;
    GiftCertificateDTO giftCertificateDTO;
    List<GiftCertificate> giftCertificates;

    @BeforeEach
    public void setUp() {
        tagDTOMapper = new TagDTOMapper();
        certificateDTOMapper = new GiftCertificateDTOMapper(tagDTOMapper);
        service = new GiftCertificateServiceImpl(dao, certificateDTOMapper);
        giftCertificate = GiftCertificate.builder()
                .id(1L)
                .name(NAME)
                .description(DESCRIPTION)
                .price(new BigDecimal(PRICE))
                .duration(2)
                .createDate(LocalDateTime.parse(CREATE_DATE))
                .lastUpdateDate(LocalDateTime.parse(LAST_UPDATE_DATE))
                .build();
        giftCertificateDTO = GiftCertificateDTO.builder()
                .id(1L)
                .name(NAME)
                .description(DESCRIPTION)
                .price(new BigDecimal(PRICE))
                .duration(2)
                .createDate(CREATE_DATE)
                .lastUpdateDate(LAST_UPDATE_DATE)
                .build();
        giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificate);
    }

    @Test
    void testFindPositive() throws NotExistEntityException, ServiceException {
        lenient().when(dao.findById(anyLong())).thenReturn(Optional.of(giftCertificate));
        GiftCertificateDTO actual = service.find(1L);
        assertEquals(giftCertificateDTO, actual);
    }

    @Test
    void testFindNegative() throws NotExistEntityException, ServiceException {
        lenient().when(dao.findById(anyLong())).thenReturn(Optional.of(giftCertificate));
        GiftCertificateDTO actual = service.find(1L);
        assertNotEquals(null, actual);
    }

    @Test
    void testFindServiceException() {
        lenient().when(dao.findById(anyLong())).thenThrow(EmptyResultDataAccessException.class);
        assertThrows(ServiceException.class, () -> service.find(1L));
    }

    @Test
    void testFindNotExistEntityException() {
        lenient().when(dao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotExistEntityException.class, () -> service.find(1L));
    }

    @Test
    void testAddPositive() throws ServiceException {
        lenient().when(dao.create(any(GiftCertificate.class))).thenReturn(giftCertificate);
        GiftCertificateDTO actual = service.add(giftCertificateDTO);
        assertEquals(giftCertificateDTO, actual);
    }

    @Test
    void testAddNegative() throws ServiceException {
        lenient().when(dao.create(any(GiftCertificate.class))).thenReturn(giftCertificate);
        GiftCertificateDTO actual = service.add(giftCertificateDTO);
        assertNotEquals(null, actual);
    }

    @Test
    void testAddServiceException() {
        lenient().when(dao.create(any(GiftCertificate.class))).thenThrow(EmptyResultDataAccessException.class);
        assertThrows(ServiceException.class, () -> service.add(giftCertificateDTO));
    }

    @Test
    void testUpdateServiceException() {
        lenient().when(dao.update(any(GiftCertificate.class))).thenThrow(EmptyResultDataAccessException.class);
        assertThrows(ServiceException.class, () -> service.update(giftCertificateDTO));
    }

    @Test
    void testFilterByParametersPositive() throws ServiceException, IncorrectArgumentException {
        lenient().when(dao.getCountOfEntities()).thenReturn(10L);
        lenient().when(dao.filterByParameters(any(), anyInt(), anyInt()))
                .thenReturn(giftCertificates);
        List<GiftCertificateDTO> actual = service
                .filterByParameters(new FilterParams(), 2, 3);
        List<GiftCertificateDTO> expected = new ArrayList<>();
        expected.add(giftCertificateDTO);
        assertEquals(expected, actual);
    }

    @Test
    void testFilterByParametersNegative() throws ServiceException, IncorrectArgumentException {
        lenient().when(dao.getCountOfEntities()).thenReturn(10L);
        lenient().when(dao.filterByParameters(any(), anyInt(), anyInt()))
                .thenReturn(giftCertificates);
        List<GiftCertificateDTO> actual = service
                .filterByParameters(new FilterParams(), 2, 3);
        assertNotEquals(0, actual.size());
    }

    @Test
    void testFilterByParametersServiceException() {
        lenient().when(dao.getCountOfEntities()).thenReturn(10L);
        lenient().when(dao.filterByParameters(any(), anyInt(), anyInt()))
                .thenThrow(EmptyResultDataAccessException.class);
        assertThrows(ServiceException.class, () -> service
                .filterByParameters(new FilterParams(), 2, 3));
    }

    @Test
    void testFilterByParametersIncorrectArgumentException() {
        lenient().when(dao.getCountOfEntities()).thenReturn(9L);
        lenient().when(dao.filterByParameters(any(), anyInt(), anyInt()))
                .thenReturn(giftCertificates);
        assertThrows(IncorrectArgumentException.class, () -> service
                .filterByParameters(new FilterParams(), 4, 3));
    }

    @Test
    void testCountPositive() throws ServiceException {
        lenient().when(dao.getCountOfEntities()).thenReturn(10L);
        long actual = service.count();
        assertEquals(10L, actual);
    }

    @Test
    void testCountNegative() throws ServiceException {
        lenient().when(dao.getCountOfEntities()).thenReturn(10L);
        long actual = service.count();
        assertNotEquals(1L, actual);
    }

    @Test
    void testCountServiceException() {
        lenient().when(dao.getCountOfEntities()).thenThrow(EmptyResultDataAccessException.class);
        assertThrows(ServiceException.class, () -> service.count());
    }
}