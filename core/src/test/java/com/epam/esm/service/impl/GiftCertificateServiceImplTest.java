package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.GiftCertificateDTOMapper;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.common.SortType;
import com.epam.esm.exception.DAOException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.entity.GiftCertificate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.common.SortType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @Mock
    GiftCertificateDAO dao;
    GiftCertificateDTOMapper converter;
    GiftCertificateService service;
    GiftCertificate giftCertificate;
    GiftCertificateDTO giftGiftCertificateDTO;

    @BeforeEach
    public void setUp() {
        converter = new GiftCertificateDTOMapper();
        service = new GiftCertificateServiceImpl(dao, converter);
        giftCertificate = new GiftCertificate(1L, "name", "description", new BigDecimal("50"),
                12, LocalDateTime.parse("2022-03-15T12:15:00"), LocalDateTime.parse("2022-05-10T12:15:00"));
        giftGiftCertificateDTO = new GiftCertificateDTO(1L, "name", "description", new BigDecimal("50"),
                12, "2022-03-15T12:15:00.0", "2022-05-10T12:15:00.0");
    }

    @Test
    void testFindPositive() throws DAOException, ServiceException {
        lenient().when(dao.read(anyLong())).thenReturn(giftCertificate);
        GiftCertificateDTO actual = service.find(1L);
        assertEquals(giftGiftCertificateDTO, actual);
    }

    @Test
    void testFindNegative() throws DAOException, ServiceException {
        giftCertificate.setName("names");
        lenient().when(dao.read(anyLong())).thenReturn(giftCertificate);
        GiftCertificateDTO actual = service.find(1L);
        assertNotEquals(giftGiftCertificateDTO, actual);
    }

    @Test
    void testFindException() throws DAOException {
        lenient().when(dao.read(anyLong())).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> service.find(1L));
    }

    @Test
    void testAddPositive() throws DAOException, ServiceException {
        lenient().when(dao.create(any(GiftCertificate.class))).thenReturn(giftCertificate);
        GiftCertificateDTO actual = service.add(giftGiftCertificateDTO);
        assertEquals(giftGiftCertificateDTO, actual);
    }

    @Test
    void testAddNegative() throws DAOException, ServiceException {
        lenient().when(dao.create(any(GiftCertificate.class))).thenReturn(giftCertificate);
        GiftCertificateDTO actual = service.add(giftGiftCertificateDTO);
        assertNotEquals(new GiftCertificateDTO(), actual);
    }

    @Test
    void testAddException() throws DAOException {
        lenient().when(dao.create(any(GiftCertificate.class))).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> service.add(giftGiftCertificateDTO));
    }

    @Test
    void testUpdatePositive() throws DAOException, ServiceException {
        lenient().when(dao.update(any(GiftCertificateDTO.class))).thenReturn(1L);
        lenient().when((dao.read(anyLong()))).thenReturn(giftCertificate);
        GiftCertificateDTO actual = service.update(giftGiftCertificateDTO);
        assertEquals(giftGiftCertificateDTO, actual);
    }

    @Test
    void testUpdateNegative() throws DAOException, ServiceException {
        lenient().when(dao.update(any(GiftCertificateDTO.class))).thenReturn(1L);
        lenient().when((dao.read(anyLong()))).thenReturn(giftCertificate);
        GiftCertificateDTO actual = service.update(giftGiftCertificateDTO);
        assertNotEquals(new GiftCertificateDTO(), actual);
    }

    @Test
    void testUpdateException() throws DAOException {
        lenient().when(dao.update(any(GiftCertificateDTO.class))).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> service.update(giftGiftCertificateDTO));
    }

    @Test
    void testDeletePositive() throws DAOException, ServiceException {
        lenient().when(dao.delete(anyLong())).thenReturn(1L);
        long actual = service.delete(1L);
        assertEquals(1L, actual);
    }

    @Test
    void testDeleteNegative() throws DAOException, ServiceException {
        lenient().when(dao.delete(anyLong())).thenReturn(1L);
        long actual = service.delete(1L);
        assertNotEquals(2L, actual);
    }

    @Test
    void testDeleteException() throws DAOException {
        lenient().when(dao.delete(anyLong())).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> service.delete(1L));
    }

    @Test
    void testFindByTagPositive() throws DAOException, ServiceException {
        lenient().when(dao.findByTag(anyString())).thenReturn(new ArrayList<>());
        List<GiftCertificateDTO> actual = service.findByTag("tagName");
        assertEquals(new ArrayList<>(), actual);
    }

    @Test
    void testFindByTagNegative() throws DAOException, ServiceException {
        ArrayList<GiftCertificate> list = new ArrayList<>();
        list.add(giftCertificate);
        lenient().when(dao.findByTag(anyString())).thenReturn(list);
        List<GiftCertificateDTO> actual = service.findByTag("tagName");
        assertNotEquals(new ArrayList<>(), actual);
    }

    @Test
    void testFindByTagException() throws DAOException {
        lenient().when(dao.findByTag(anyString())).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> service.findByTag("name"));
    }

    @Test
    void testSearchByNameOrDescriptionPositive() throws DAOException, ServiceException {
        lenient().when(dao.searchByNameOrDescription(anyString())).thenReturn(new ArrayList<>());
        List<GiftCertificateDTO> actual = service.searchByNameOrDescription("part");
        assertEquals(new ArrayList<>(), actual);
    }

    @Test
    void testSearchByNameOrDescriptionNegative() throws DAOException, ServiceException {
        ArrayList<GiftCertificate> list = new ArrayList<>();
        list.add(giftCertificate);
        lenient().when(dao.searchByNameOrDescription(anyString())).thenReturn(list);
        List<GiftCertificateDTO> actual = service.searchByNameOrDescription("part");
        assertNotEquals(new ArrayList<>(), actual);
    }

    @Test
    void testSearchByNameOrDescriptionException() throws DAOException {
        lenient().when(dao.searchByNameOrDescription(anyString())).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> service.searchByNameOrDescription("part"));
    }

    @Test
    void testSortByNamePositive() throws DAOException, ServiceException {
        lenient().when(dao.sortByName(any(SortType.class))).thenReturn(new ArrayList<>());
        List<GiftCertificateDTO> actual = service.sortByName(ASC);
        assertEquals(new ArrayList<>(), actual);
    }

    @Test
    void testSortByNameNegative() throws DAOException, ServiceException {
        ArrayList<GiftCertificate> list = new ArrayList<>();
        list.add(giftCertificate);
        lenient().when(dao.sortByName(any(SortType.class))).thenReturn(list);
        List<GiftCertificateDTO> actual = service.sortByName(ASC);
        assertNotEquals(new ArrayList<>(), actual);
    }

    @Test
    void testSortByNameException() throws DAOException {
        lenient().when(dao.sortByName(any(SortType.class))).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> service.sortByName(ASC));
    }

    @Test
    void testSortByDatePositive() throws DAOException, ServiceException {
        lenient().when(dao.sortByDate(any(SortType.class))).thenReturn(new ArrayList<>());
        List<GiftCertificateDTO> actual = service.sortByDate(ASC);
        assertEquals(new ArrayList<>(), actual);
    }

    @Test
    void testSortByDateNegative() throws DAOException, ServiceException {
        ArrayList<GiftCertificate> list = new ArrayList<>();
        list.add(giftCertificate);
        lenient().when(dao.sortByDate(any(SortType.class))).thenReturn(list);
        List<GiftCertificateDTO> actual = service.sortByDate(ASC);
        assertNotEquals(new ArrayList<>(), actual);
    }

    @Test
    void testSortByDateException() throws DAOException {
        lenient().when(dao.sortByDate(any(SortType.class))).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> service.sortByDate(ASC));
    }
}
