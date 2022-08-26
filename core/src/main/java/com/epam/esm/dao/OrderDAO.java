package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;

/**
 * Work with entity order in layer DAO
 */
public interface OrderDAO extends GenericDAO<Order> {

    /**
     * Get top user with the largest  tag
     * @return top tag
     */
    Tag getTopUserTag();
}
