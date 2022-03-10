package com.epam.esm.converter.impl;

import com.epam.esm.converter.DTOMapper;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagDTOMapper implements DTOMapper<TagDTO, Tag> {

    @Override
    public TagDTO toDTO(Tag tag) {
        return TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName()).build();
    }

    @Override
    public Tag fromDTO(TagDTO tagDTO) {
        return Tag.builder()
                .id(tagDTO.getId())
                .name(tagDTO.getName()).build();
    }
}
