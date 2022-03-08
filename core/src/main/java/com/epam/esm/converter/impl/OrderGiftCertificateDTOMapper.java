package com.epam.esm.converter.impl;

import com.epam.esm.converter.DTOMapper;
import com.epam.esm.dto.OrderGiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderGiftCertificate;
import org.springframework.stereotype.Component;

@Component
public class OrderGiftCertificateDTOMapper implements DTOMapper<OrderGiftCertificateDTO, OrderGiftCertificate> {

    @Override
    public OrderGiftCertificateDTO toDTO(OrderGiftCertificate orderGiftCertificate) {
        return OrderGiftCertificateDTO.builder()
                .orderId(orderGiftCertificate.getOrder().getId())
                .giftCertificateId(orderGiftCertificate.getGiftCertificate().getId())
                .oneCost(orderGiftCertificate.getOneCost())
                .quantity(orderGiftCertificate.getQuantity())
                .build();
    }

    @Override
    public OrderGiftCertificate fromDTO(OrderGiftCertificateDTO orderGiftCertificateDTO) {
        return OrderGiftCertificate.builder()
                .order(Order.builder()
                        .id(orderGiftCertificateDTO.getOrderId())
                        .build())
                .giftCertificate(GiftCertificate.builder()
                        .id(orderGiftCertificateDTO.getGiftCertificateId())
                        .build())
                .oneCost(orderGiftCertificateDTO.getOneCost())
                .quantity(orderGiftCertificateDTO.getQuantity())
                .build();
    }
}
