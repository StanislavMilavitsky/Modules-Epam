package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.UserDTOMapper;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.exception.IncorrectArgumentException;
import com.epam.esm.exception.NotExistEntityException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.Page;
import com.epam.esm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final UserDTOMapper userMapper;

    public UserServiceImpl(UserDAO userDAO, UserDTOMapper userMapper) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO find(Long id) throws ServiceException, NotExistEntityException {
        try {
            Optional<User> user = userDAO.findById(id);
            return user.map(userMapper::toDTO)
                    .orElseThrow(() -> new NotExistEntityException("User with id=" + id + " not exist!"));
        } catch (DataAccessException exception){
            String exceptionMessage = String.format("Find user service by id = %d exception!", id);
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public List<UserDTO> findAll(int page, int size) throws ServiceException, IncorrectArgumentException {
        try {
            long count = count();
            Page userPage = new Page(page, size, count);
            List<User> tags = userDAO.findAll(userPage.getOffset(), userPage.getLimit());
            return tags.stream().map(userMapper::toDTO).collect(Collectors.toList());
        } catch (DataAccessException exception) {
            String exceptionMessage = "Find all users service exception!";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }

    }

    @Override
    public long count() throws ServiceException {
        try {
            return userDAO.getCountOfEntities();
        } catch (DataAccessException exception) {
            String exceptionMessage = "Count users service exception!";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }
}
