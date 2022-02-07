package com.epam.esm.service;

public interface TagService {

    TagDTO find(Long id) throws ServiceException;

    TagDTO add(TagDTO tagDTO) throws ServiceException;

    long delete(Long id) throws ServiceException;
}
