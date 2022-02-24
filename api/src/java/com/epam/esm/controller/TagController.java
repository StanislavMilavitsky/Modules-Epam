package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Tag RestAPI.
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    private TagController(TagService tagService) {
        this.tagService = tagService;
    }

    private final static Logger logger = LogManager.getLogger(GiftCertificateServiceImpl.class);

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
            logger.error("Negative id exception");
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
            logger.error(bindingResultHandler(bindingResult));
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
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) throws ServiceException, ControllerException {
        if (id > 0) {
            long result = tagService.delete(id);
            String deleteSuccessful = String.format("Delete by id=%d successful!", id);
            String deleteUnsuccessful = String.format("Delete by id=%d unsuccessful!", id);
            return result != -1L ? ResponseEntity.ok(deleteSuccessful) : ResponseEntity.ok(deleteUnsuccessful);

        } else {
            logger.error("Negative id exception");
            throw  new ControllerException("Negative id exception");
        }
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
