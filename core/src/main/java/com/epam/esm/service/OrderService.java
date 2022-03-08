package com.epam.esm.service;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ServiceException;

/**
 *  Service layer use methods from dao layer
 */
public interface OrderService {

    /**
     * Add order use method dao layer
     * @param orderDTO entity order
     * @return added entity
     * @throws ServiceException if order cant added
     */
    OrderDTO add(OrderDTO orderDTO) throws ServiceException;

    /**
     * Get top user with top tag from dao
     * @return top tag user
     * @throws ServiceException if cant find user or tags
     */
    TagDTO getTopUserTag() throws ServiceException;
}
