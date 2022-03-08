package com.epam.esm.service;


import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.IncorrectArgumentException;
import com.epam.esm.exception.NotExistEntityException;
import com.epam.esm.exception.ServiceException;

import java.util.List;

/**
 *  Service layer use methods from dao layer
 */
public interface UserService {

    /**
     * Find user by id
     * @param id id user
     * @return user entity
     * @throws ServiceException if cant find
     * @throws NotExistEntityException if user have not be exist
     */
    UserDTO find(Long id) throws ServiceException, NotExistEntityException;

    /**
     * Find all users
     * @param page page
     * @param size count of user by page
     * @return list of users
     * @throws ServiceException  if cant find
     * @throws IncorrectArgumentException if incorrect argument
     */
    List<UserDTO> findAll(int page, int size) throws ServiceException, IncorrectArgumentException;

    /**
     * Sum count of user
     * @return sum of user
     * @throws ServiceException if cant sum users
     */
    long count() throws ServiceException;

}
