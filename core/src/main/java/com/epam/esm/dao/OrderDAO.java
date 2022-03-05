package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;

public interface OrderDAO extends GenericDAO<Order> {

    Tag getTopUserTag();
}
