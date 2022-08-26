package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.OrderDTOMapper;
import com.epam.esm.converter.impl.OrderGiftCertificateDTOMapper;
import com.epam.esm.converter.impl.TagDTOMapper;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderDAO dao;
    OrderService service;
    OrderDTOMapper orderMapper;
    TagDTOMapper tagDTOMapper;
    OrderGiftCertificateDTOMapper orderGiftCertificateDTOMapper;
    Order order;
    OrderDTO orderDTO;
    Tag tag;
    TagDTO tagDTO;

    @BeforeEach
    public void setUp() {
        tagDTOMapper = new TagDTOMapper();
        orderGiftCertificateDTOMapper = new OrderGiftCertificateDTOMapper();
        orderMapper = new OrderDTOMapper(orderGiftCertificateDTOMapper);
        service = new OrderServiceImpl(dao, orderMapper, tagDTOMapper);
        order = Order.builder()
                .id(1L)
                .timeOfPurchase(LocalDateTime.parse("2022-03-08T12:15:00.0"))
                .userId(1L)
                .build();
        orderDTO = OrderDTO.builder()
                .id(1L)
                .purchaseTime("2022-03-08T12:15:00.0")
                .userId(1L)
                .build();
        tag = Tag.builder()
                .id(1L)
                .name("tag").build();
        tagDTO = TagDTO.builder()
                .id(1L)
                .name("tag").build();
    }

    @Test
    void testAddPositive() throws ServiceException {
        lenient().when(dao.create(any(Order.class))).thenReturn(order);
        OrderDTO actual = service.add(orderDTO);
        assertEquals(orderDTO, actual);
    }

    @Test
    void testAddNegative() throws ServiceException {
        lenient().when(dao.create(any(Order.class))).thenReturn(order);
        OrderDTO actual = service.add(orderDTO);
        assertNotEquals(null, actual);
    }

    @Test
    void testAddServiceException() {
        lenient().when(dao.create(any(Order.class))).thenThrow(EmptyResultDataAccessException.class);
        assertThrows(ServiceException.class, () -> service.add(orderDTO));
    }

    @Test
    void testGetTopUserTagPositive() throws ServiceException {
        lenient().when(dao.getTopUserTag()).thenReturn(tag);
        TagDTO actual = service.getTopUserTag();
        assertEquals(tagDTO, actual);
    }

    @Test
    void testGetTopUserTagNegative() throws ServiceException {
        lenient().when(dao.getTopUserTag()).thenReturn(tag);
        TagDTO actual = service.getTopUserTag();
        assertNotEquals(null, actual);
    }

    @Test
    void testGetTopUserTagServiceException() {
        lenient().when(dao.getTopUserTag()).thenThrow(EmptyResultDataAccessException.class);
        assertThrows(ServiceException.class, () -> service.getTopUserTag());
    }
}