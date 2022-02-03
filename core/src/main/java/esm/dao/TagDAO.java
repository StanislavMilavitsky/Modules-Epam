package esm.dao;

import com.epam.esm.dao.exception.DAOException;
import com.epam.esm.entity.Tag;

public interface TagDAO {

    Tag create(Tag tag) throws DAOException;

    Tag read(Long id) throws DAOException;

    boolean delete(Long id) throws DAOException;
}

