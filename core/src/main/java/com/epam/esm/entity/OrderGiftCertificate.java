package com.epam.esm.entity;

import com.epam.esm.dao.AuditListener;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EntityListeners(AuditListener.class)
@Entity(name = "order_gift_certificate")
public class OrderGiftCertificate extends GenericEntity implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_order")
    @EqualsAndHashCode.Exclude
    private Order order;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_gift_certificate")
    @EqualsAndHashCode.Exclude
    private GiftCertificate giftCertificate;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "one_cost")
    private BigDecimal oneCost;

    @Override
    public Long getId() {
        return order.getId() + giftCertificate.getId();
    }
}
