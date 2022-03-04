package com.epam.esm.service.impl;

import com.epam.esm.exception.IncorrectArgumentException;
import com.epam.esm.exception.NotExistEntityException;
import lombok.extern.slf4j.Slf4j;

import com.epam.esm.service.Page;
import com.epam.esm.converter.impl.TagDTOMapper;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;
    private final TagDTOMapper tagDTOMapper;

    public TagServiceImpl(TagDAO tagDAO, TagDTOMapper tagDTOMapper) {
        this.tagDAO = tagDAO;
        this.tagDTOMapper = tagDTOMapper;
    }

    @Override
    public TagDTO find(Long id) throws ServiceException {
        try {
            Optional<Tag> tag = tagDAO.findById(id);
            return tag.map(tagDTOMapper::toDTO).orElseGet(TagDTO::new);
        } catch (DataAccessException exception) {
            String exceptionMessage = String.format("Find tag by id=%d not exist!", id);
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public TagDTO add(TagDTO tagDTO) throws ServiceException {
        try {
            Tag fromDTO = tagDTOMapper.fromDTO(tagDTO);
            Tag tag = tagDAO.create(fromDTO);
            return tagDTOMapper.toDTO(tag);
        } catch (DataAccessException exception) {
            String exceptionMessage = String.format("Add tag by name=%s exception!", tagDTO.getName());
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException, NotExistEntityException {
        try {
            tagDAO.delete(id);
        } catch (DataAccessException exception) {
            String exceptionMessage = String.format("Delete tag by id=%d exception!", id);
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }

    }

    @Override
    public List<TagDTO> findAll(int page, int size) throws ServiceException, IncorrectArgumentException {
        try {
            long count = count();
            Page tagPage = new Page(page, size, count);
            List<Tag> tags = tagDAO.findAll(tagPage.getOffset(), tagPage.getLimit());
            return tags.stream().map(tagDTOMapper::toDTO).collect(Collectors.toList());
        } catch (DataAccessException exception) {
            String exceptionMessage = "Find all tag service exception!";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public long count() throws ServiceException {
        try {
            return tagDAO.getCountOfEntities();
        } catch (DataAccessException exception) {
            String exceptionMessage = "Count tag service exception!";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }
}
