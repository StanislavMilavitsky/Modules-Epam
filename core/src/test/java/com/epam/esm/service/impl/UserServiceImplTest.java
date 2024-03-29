package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.OrderDTOMapper;
import com.epam.esm.converter.impl.OrderGiftCertificateDTOMapper;
import com.epam.esm.converter.impl.UserDTOMapper;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.exception.IncorrectArgumentException;
import com.epam.esm.exception.NotExistEntityException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserDAO dao;
    UserService service;
    UserDTOMapper dtoMapper;
    OrderDTOMapper orderDTOMapper;
    OrderGiftCertificateDTOMapper orderGiftCertificateDTOMapper;
    User user;
    UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        orderGiftCertificateDTOMapper = new OrderGiftCertificateDTOMapper();
        orderDTOMapper = new OrderDTOMapper(orderGiftCertificateDTOMapper);
        dtoMapper = new UserDTOMapper(orderDTOMapper);
        service = new UserServiceImpl(dao, dtoMapper);
        user = User.builder()
                .id(1L)
                .name("User")
                .surname("Surname").build();
        userDTO = UserDTO.builder()
                .id(1L)
                .name("User")
                .surname("Surname").build();
    }

    @Test
    void testFindPositive() throws NotExistEntityException, ServiceException {
        lenient().when(dao.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        UserDTO actual = service.find(1L);
        assertEquals(userDTO, actual);
    }

    @Test
    void testFindNegative() throws NotExistEntityException, ServiceException {
        lenient().when(dao.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        UserDTO actual = service.find(1L);
        assertNotEquals(null, actual);
    }

    @Test
    void testFindServiceException() {
        lenient().when(dao.findById(Mockito.anyLong())).thenThrow(EmptyResultDataAccessException.class);
        assertThrows(ServiceException.class, () -> service.find(1L));
    }

    @Test
    void testFindNotExistEntityException() {
        lenient().when(dao.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(NotExistEntityException.class, () -> service.find(1L));
    }

    @Test
    void testFindAllPositive() throws ServiceException, IncorrectArgumentException {
        List<User> users = new ArrayList<>();
        users.add(user);
        lenient().when(dao.getCountOfEntities()).thenReturn(10L);
        lenient().when(dao.findAll(anyInt(), anyInt())).thenReturn(users);
        List<UserDTO> actual = service.findAll(1, 2);
        List<UserDTO> expected = new ArrayList<>();
        expected.add(userDTO);
        assertEquals(expected, actual);
    }

    @Test
    void testFindAllNegative() throws ServiceException, IncorrectArgumentException {
        List<User> users = new ArrayList<>();
        users.add(user);
        lenient().when(dao.getCountOfEntities()).thenReturn(10L);
        lenient().when(dao.findAll(anyInt(), anyInt())).thenReturn(users);
        List<UserDTO> actual = service.findAll(1, 2);
        assertNotEquals(0, actual.size());
    }

    @Test
    void testFindAllServiceException() {
        lenient().when(dao.getCountOfEntities()).thenReturn(10L);
        lenient().when(dao.findAll(anyInt(), anyInt())).thenThrow(EmptyResultDataAccessException.class);
        assertThrows(ServiceException.class, () -> service.findAll(1, 2));
    }

    @Test
    void testFindAllIncorrectArgumentException() {
        lenient().when(dao.getCountOfEntities()).thenReturn(9L);
        assertThrows(IncorrectArgumentException.class, () -> service.findAll(4, 3));
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