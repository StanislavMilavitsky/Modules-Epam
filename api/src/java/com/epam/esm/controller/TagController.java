package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.IncorrectArgumentException;
import com.epam.esm.exception.NotExistEntityException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Tag RestAPI.
 */
@RestController
@RequestMapping("/tags")
@Slf4j
public class TagController extends CommonController<TagDTO> {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Find tag by id.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException the service exception
     * @throws ControllerException if id is incorrect
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> find(@PathVariable(name = "id") Long id) throws ServiceException, ControllerException {
        if(id > 0 ){
            TagDTO tag = tagService.find(id);
            return ResponseEntity.ok(tag);
        } else {
            log.error("Negative id exception");
            throw  new ControllerException("Negative id exception");
        }
    }

    /**
     * Add tag.
     *
     * @param tagName the tag name
     * @return the response entity
     * @throws ServiceException the service exception
     * @throws ControllerException if tag name incorrect
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<TagDTO> add(@RequestBody @Valid TagDTO tagName, BindingResult bindingResult) throws ServiceException, ControllerException {
        if (bindingResult.hasErrors()){
            log.error(bindingResultHandler(bindingResult));
            throw new ControllerException(bindingResultHandler(bindingResult));
        }
        TagDTO result = tagService.add(tagName);
        return ResponseEntity.ok(result);
    }

    /**
     * Delete tag by id.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException the service exception
     * @throws ControllerException if id is incorrect
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) throws ServiceException, ControllerException, NotExistEntityException {
        if (id > 0) {
            tagService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } else {
            log.error("Negative id exception");
            throw  new ControllerException("Negative id exception");
        }
    }

    /**
     * Find all tags
     *
     * @param page page
     * @param size tags on page
     * @return list of tags
     * @throws ServiceException if cant find
     * @throws IncorrectArgumentException if incorrect argument
     */
    @Override
    @GetMapping
    public ResponseEntity<PagedModel<TagDTO>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size
    ) throws ServiceException, IncorrectArgumentException {
        List<TagDTO> tags = tagService.findAll(page, size);
        long count = tagService.count();
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, count);
        List<Link> linkList = buildLink(page, size, (int) pageMetadata.getTotalPages());
        PagedModel<TagDTO> pagedModel = PagedModel.of(tags, pageMetadata, linkList);
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Get default message by validate exception
     * @param bindingResult exceptions of validate
     * @return string default message of exception
     */
    private String bindingResultHandler(BindingResult bindingResult){
       return bindingResult.getAllErrors().get(0).getDefaultMessage();
    }
}
