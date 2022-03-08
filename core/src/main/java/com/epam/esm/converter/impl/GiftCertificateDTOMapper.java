package com.epam.esm.converter.impl;

import com.epam.esm.converter.DTOMapper;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GiftCertificateDTOMapper implements DTOMapper<GiftCertificateDTO, GiftCertificate> {
    private final static String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.s";

    private final TagDTOMapper tagDTOMapper;

    public GiftCertificateDTOMapper(TagDTOMapper tagDTOMapper) {
        this.tagDTOMapper = tagDTOMapper;
    }

    @Override
    public GiftCertificateDTO toDTO(GiftCertificate giftCertificate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        String formatCreateDate = giftCertificate.getCreateDate().format(formatter);
        String formatLastUpdateDate = giftCertificate.getLastUpdateDate().format(formatter);
        List<TagDTO> tagDTOList = giftCertificate.getTags() != null ?
                giftCertificate.getTags().stream().map(tagDTOMapper::toDTO).collect(Collectors.toList()) : null;
        return GiftCertificateDTO.builder()
                .id(giftCertificate.getId())
                .name(giftCertificate.getName())
                .description(giftCertificate.getDescription())
                .duration(giftCertificate.getDuration())
                .price(giftCertificate.getPrice())
                .createDate(formatCreateDate)
                .lastUpdateDate(formatLastUpdateDate)
                .tags(tagDTOList).build();
    }

    @Override
    public GiftCertificate fromDTO(GiftCertificateDTO giftCertificateDTO) {
        List<Tag> tagList = giftCertificateDTO.getTags() != null ?
                giftCertificateDTO.getTags().stream().map(tagDTOMapper::fromDTO).collect(Collectors.toList()) :
                new ArrayList<>();
        LocalDateTime createDate = StringUtils.isNotEmpty(giftCertificateDTO.getCreateDate()) ?
                LocalDateTime.parse(giftCertificateDTO.getCreateDate()) : null;
        LocalDateTime lastUpdateDate = StringUtils.isNotEmpty(giftCertificateDTO.getLastUpdateDate()) ?
                LocalDateTime.parse(giftCertificateDTO.getLastUpdateDate()) : null;
        return GiftCertificate.builder()
                .id(giftCertificateDTO.getId())
                .name(giftCertificateDTO.getName())
                .description(giftCertificateDTO.getDescription())
                .duration(giftCertificateDTO.getDuration())
                .price(giftCertificateDTO.getPrice())
                .createDate(createDate)
                .lastUpdateDate(lastUpdateDate)
                .tags(tagList).build();
    }
}

