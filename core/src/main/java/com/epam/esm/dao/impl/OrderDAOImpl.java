package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderGiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;

import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class OrderDAOImpl extends GenericDAOImpl<Order> implements OrderDAO {

    public OrderDAOImpl() {
        super(Order.class);
    }

    @Override
    public Order create(Order order){
        Order orderBuild = Order.builder()
                .userId(order.getUserId())
                .timeOfPurchase(order.getTimeOfPurchase())
                .orderGiftCertificates(new HashSet<>())
                .build();
        entityManager.persist(orderBuild);
        Set<OrderGiftCertificate> orderGiftCertificates = order.getOrderGiftCertificates();
        if (!orderGiftCertificates.isEmpty()) {
            Order orderId = Order.builder().id(orderBuild.getId()).build();
            orderGiftCertificates.forEach(o -> {
                o.setOrder(orderId);
                entityManager.persist(o);
            });
        }
        Optional<Order> result = findById(orderBuild.getId());
        result.ifPresent(o -> o.setOrderGiftCertificates(orderGiftCertificates));
        return result.orElseGet(Order::new);
    }


    @Override
    public Tag getTopUserTag() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = builder.createQuery();

          /*
          SELECT t.id ,t.name,sum(oc.quantity) FROM  order o
          join order_gift_certificate oc on o.id=oc.id
          join gift_certificate gc on oc.id_certificate=gc.id
          join gift_certificate_has_tag tc on gc.id=tc.id_certificate
          join tag t on tc.id_tag=t.id
          where o.user_id=
          (SELECT u.id  FROM user u
          join  order o on u.id = o.id
          join order_gift_certificate oc on c.id_order = o.user_id
          group by u.id
          order by sum(oc.quantity*oc.one_cost) desc limit 1)
          group by t.name limit 1
         */

        Root<User> root = query.from(User.class);
        Join<Object, Object> join = root.join("orders").join("orderGiftCertificates");
        Expression<Number> prod = builder.prod(join.get("quantity"), join.get("oneCost"));
        query.select(root.get("id"))
                .groupBy(root.get("id"))
                .orderBy(builder.desc((builder.sum(prod))));
        long userId = (long) entityManager.createQuery(query).setMaxResults(1).getSingleResult();

        CriteriaQuery<Tuple> orderQuery = builder.createTupleQuery();
        Root<Order> orderRoot = orderQuery.from(Order.class);
        Join<Object, Object> tagJoin = orderRoot.join("orderGiftCertificates")
                .join("giftCertificate").join("tags");
        orderQuery.select(builder.tuple(tagJoin.get("id"), tagJoin.get("name")))
                .where(builder.equal(orderRoot.get("userId"), userId))
                .groupBy(tagJoin.get("id"));
        Optional<Tuple> tuple = entityManager.createQuery(orderQuery)
                .setMaxResults(1)
                .getResultList().stream().findFirst();
        return !tuple.isPresent() ? new Tag() : Tag.builder()
                .id((long) tuple.get().get(0))
                .name(tuple.get().get(1).toString()).build();
    }
}
