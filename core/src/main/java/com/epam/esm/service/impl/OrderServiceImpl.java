package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.OrderDTOMapper;
import com.epam.esm.converter.impl.TagDTOMapper;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    private final OrderDTOMapper orderDTOMapper;

    private final TagDTOMapper tagDTOMapper;

    public OrderServiceImpl(OrderDAO orderDAO, OrderDTOMapper orderMapper, TagDTOMapper tagMapper) {
        this.orderDAO = orderDAO;
        this.orderDTOMapper = orderMapper;
        this.tagDTOMapper = tagMapper;
    }

    @Override
    public OrderDTO add(OrderDTO orderDTO) throws ServiceException {
        try {
            Order order = orderDTOMapper.fromDTO(orderDTO);
            Order orderDB = orderDAO.create(order);
            return orderDTOMapper.toDTO(orderDB);
        } catch (DataAccessException exception) {
            String exceptionMessage = "Create order service exception!";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public TagDTO getTopUserTag() throws ServiceException {
        try {
            Tag tag = orderDAO.getTopUserTag();
            return tagDTOMapper.toDTO(tag);
        } catch (DataAccessException exception) {
            String exceptionMessage = "Get top tag service exception!";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

}
