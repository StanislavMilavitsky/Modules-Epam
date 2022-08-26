package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entity service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OrderGiftCertificateDTO {

    private Long orderId;

    private Long giftCertificateId;

    private Long quantity;

    private BigDecimal oneCost;
}
