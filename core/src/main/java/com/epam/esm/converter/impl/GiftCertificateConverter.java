package com.epam.esm.converter.impl;

import com.epam.esm.converter.Converter;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class GiftCertificateConverter implements Converter<GiftCertificateDTO, GiftCertificate> {
    private final static String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.s";

    @Override
    public GiftCertificateDTO toDTO(GiftCertificate giftCertificate) {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();
        giftCertificateDTO.setId(giftCertificate.getId());
        giftCertificateDTO.setName(giftCertificate.getName());
        giftCertificateDTO.setDescription(giftCertificate.getDescription());
        giftCertificateDTO.setDuration(giftCertificate.getDuration());
        giftCertificateDTO.setPrice(giftCertificate.getPrice());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        String formatCreateDate = giftCertificate.getCreateDate().format(formatter);
        giftCertificateDTO.setCreateDate(formatCreateDate);
        String formatLastUpdateDate = giftCertificate.getLastUpdateDate().format(formatter);
        giftCertificateDTO.setLastUpdateDate(formatLastUpdateDate);
        return giftCertificateDTO;
    }

    @Override
    public GiftCertificate fromDTO(GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(giftCertificateDTO.getId());
        giftCertificate.setName(giftCertificateDTO.getName());
        giftCertificate.setDescription(giftCertificateDTO.getDescription());
        giftCertificate.setDuration(giftCertificateDTO.getDuration());
        giftCertificate.setPrice(giftCertificateDTO.getPrice());
        giftCertificate.setCreateDate(LocalDateTime.parse(giftCertificateDTO.getCreateDate()));
        giftCertificate.setLastUpdateDate(LocalDateTime.parse(giftCertificateDTO.getLastUpdateDate()));
        return giftCertificate;
    }
}
