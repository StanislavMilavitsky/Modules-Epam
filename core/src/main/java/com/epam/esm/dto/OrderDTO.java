package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Entity service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OrderDTO {

    private Long id;

    private String purchaseTime;

    private Long userId;

    private Set<OrderGiftCertificateDTO>  ordersGiftCertificates;
}
