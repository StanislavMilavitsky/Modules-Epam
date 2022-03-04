package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;

import org.springframework.stereotype.Repository;

@Repository
public class TagDAOImpl extends GenericDAOImpl<Tag> implements TagDAO {

    public TagDAOImpl() {
        super(Tag.class);
    }
}