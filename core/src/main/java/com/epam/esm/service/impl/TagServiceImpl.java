package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.TagConverter;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import com.sun.istack.internal.logging.Logger;
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
            if (tagDTO != null){
                return tagDTO;
            } else {
                logger.warning("Find tag exception!");
                throw new ServiceException("Find tag exception!");
            }


    }

    @Transactional
    @Override
    public TagDTO add(TagDTO tagDTO) throws ServiceException {
            Tag tag = tagConverter.fromDTO(tagDTO);
            Tag tagDao = this.tagDAO.create(tag);
            if (tagDao != null){
                return tagConverter.toDTO(tagDao);
            } else {
                logger.warning("Add tag exception!");
                throw new ServiceException("Add tag exception!");
            }

    }

    @Transactional
    @Override
    public long delete(Long id) throws ServiceException {
            long isDelete = tagDAO.delete(id);
            if(isDelete != -1){
                return isDelete;
            } else {
                logger.warning("Delete tag exception!");
                throw new ServiceException("Delete tag exception!");
            }

    }
}
