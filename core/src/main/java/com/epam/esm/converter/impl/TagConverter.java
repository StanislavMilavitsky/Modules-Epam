package com.epam.esm.converter.impl;

import com.epam.esm.converter.Converter;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagConverter implements Converter<TagDTO, Tag> {
    @Override
    public TagDTO toDTO(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        return tagDTO;
    }

    @Override
    public Tag fromDTO(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setId(tagDTO.getId());
        tag.setName(tagDTO.getName());
        return tag;
    }
}
