package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.TagConverter;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    TagDAO tagDAO;
    TagConverter tagConverter;
    TagService tagService;
    Tag correctTag;
    TagDTO correctTagDTO;

    @BeforeEach
    void setUp() {
        tagConverter = new TagConverter();
        tagService = new TagServiceImpl(tagDAO, tagConverter);
        correctTag = new Tag(1L, "name");
        correctTagDTO = new TagDTO(1L, "name");
    }

    @Test
    void testFindTagPositive() throws ServiceException {
        lenient().when(tagDAO.read(anyLong())).thenReturn(correctTag);
        TagDTO actual = tagService.find(1L);
        assertEquals(correctTagDTO, actual);
    }

    @Test
    void testFindTagNegative() throws ServiceException {
        lenient().when(tagDAO.read(anyLong())).thenReturn(correctTag);
        TagDTO actual = tagService.find(1L);
        TagDTO expected = new TagDTO(1L, "names");
        assertNotEquals(expected, actual);
    }

    @Test
    void testAddPositive() throws ServiceException {
        lenient().when(tagDAO.create(any(Tag.class))).thenReturn(correctTag);
        TagDTO actual = tagService.add(correctTagDTO);
        assertEquals(correctTagDTO, actual);
    }

    @Test
    void testAddNegative() throws ServiceException {
        lenient().when(tagDAO.create(any(Tag.class))).thenReturn(correctTag);
        TagDTO actual = tagService.add(correctTagDTO);
        assertNotEquals(new TagDTO(), actual);
    }

    @Test
    void testDeletePositive() throws ServiceException {
        lenient().when(tagDAO.delete(anyLong())).thenReturn(1L);
        long actual = tagService.delete(1L);
        assertEquals(1L, actual);
    }

    @Test
    void testDeleteNegative() throws ServiceException {
        lenient().when(tagDAO.delete(anyLong())).thenReturn(1L);
        long actual = tagService.delete(1L);
        assertNotEquals(2L, actual);
    }
}