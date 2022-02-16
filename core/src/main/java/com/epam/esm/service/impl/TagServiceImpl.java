package com.epam.esm.service.impl;

import com.epam.esm.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.esm.converter.impl.TagConverter;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService {
    private final static Logger logger = LogManager.getLogger(TagServiceImpl.class);
    private final TagDAO tagDAO;
    private final TagConverter tagConverter;

    public TagServiceImpl(TagDAO tagDAO, TagConverter tagConverter) {
        this.tagDAO = tagDAO;
        this.tagConverter = tagConverter;
    }

    @Override
    public TagDTO find(Long id) throws ServiceException {
        try {
            Tag tag = tagDAO.read(id);
            return tagConverter.toDTO(tag);
        } catch (DAOException exception) {
            String exceptionMessage = String.format("Find tag by id=%d exception!", id);
            logger.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Transactional
    @Override
    public TagDTO add(TagDTO tagDTO) throws ServiceException {
        try {
            Tag tag = tagConverter.fromDTO(tagDTO);
            Tag tagDao = this.tagDAO.create(tag);
            return tagConverter.toDTO(tagDao);
        } catch (DAOException exception) {
            String exceptionMessage = String.format("Add tag by name=%s exception!", tagDTO.getName());
            logger.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Transactional
    @Override
    public long delete(Long id) throws ServiceException {
        try {
            return tagDAO.delete(id);
        } catch (DAOException exception) {
            String exceptionMessage = String.format("Delete tag by id=%d exception!", id);
            logger.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }

    }
}
