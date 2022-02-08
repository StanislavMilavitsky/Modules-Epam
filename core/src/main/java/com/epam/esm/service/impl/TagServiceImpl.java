package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.TagConverter;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService {
    private final static Logger logger = Logger.getLogger(TagServiceImpl.class);
    private final TagDAO tagDAO;
    private final TagConverter tagConverter;

    public TagServiceImpl(TagDAO tagDAO, TagConverter tagConverter) {
        this.tagDAO = tagDAO;
        this.tagConverter = tagConverter;
    }

    @Override
    public TagDTO find(Long id) throws ServiceException {
            Tag tag = tagDAO.read(id);
            TagDTO tagDTO = tagConverter.toDTO(tag);
            return tagDTO;//todo if object == null then throw exception

    }

    @Transactional
    @Override
    public TagDTO add(TagDTO tagDTO) {
            Tag tag = tagConverter.fromDTO(tagDTO);
            Tag tagDao = this.tagDAO.create(tag);
            return tagConverter.toDTO(tagDao);
    }

    @Transactional
    @Override
    public long delete(Long id) throws ServiceException {
            long isDelete = tagDAO.delete(id);
            return isDelete;
    }
}
