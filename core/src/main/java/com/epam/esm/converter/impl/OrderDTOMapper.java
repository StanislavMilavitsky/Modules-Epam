package com.epam.esm.converter.impl;

import com.epam.esm.converter.DTOMapper;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.OrderGiftCertificateDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderGiftCertificate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderDTOMapper implements DTOMapper<OrderDTO, Order> {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.s";
    private final OrderGiftCertificateDTOMapper orderGiftCertificateDTOMapper;

    public OrderDTOMapper(OrderGiftCertificateDTOMapper orderGiftCertificateDTOMapper) {
        this.orderGiftCertificateDTOMapper = orderGiftCertificateDTOMapper;
    }

    @Override
    public OrderDTO toDTO(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        String purchaseTime = order.getTimeOfPurchase().format(formatter);
        Set<OrderGiftCertificateDTO> set = order.getOrderGiftCertificates() != null ?
                order.getOrderGiftCertificates().stream()
                        .map(orderGiftCertificateDTOMapper::toDTO).collect(Collectors.toSet()) : null;
        return OrderDTO.builder()
                .id(order.getId())
                .purchaseTime(purchaseTime)
                .userId(order.getUserId())
                .ordersGiftCertificates(set)
                .build();
    }

    @Override
    public Order fromDTO(OrderDTO orderDTO) {
        Set<OrderGiftCertificate> set = orderDTO.getOrdersGiftCertificates() != null ? orderDTO.getOrdersGiftCertificates().stream()
                .map(orderGiftCertificateDTOMapper::fromDTO).collect(Collectors.toSet()) : null;
        LocalDateTime purchaseTime = StringUtils.isNotEmpty(orderDTO.getPurchaseTime()) ?
                LocalDateTime.parse(orderDTO.getPurchaseTime()) : null;
        return Order.builder()
                .id(orderDTO.getId())
                .timeOfPurchase(purchaseTime)
                .userId(orderDTO.getUserId())
                .orderGiftCertificates(set)
                .build();
    }
}
