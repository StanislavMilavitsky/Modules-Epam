package com.epam.esm.controller;


import com.epam.esm.common.FilterParams;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.common.SortType;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.IncorrectArgumentException;
import com.epam.esm.exception.NotExistEntityException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import lombok.extern.slf4j.Slf4j;


import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Gift certificate RestAPI.
 */
@RestController
@RequestMapping("/certificate")
@Slf4j
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Find gift certificate by id.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException the service exception
     * @throws ControllerException if id is incorrect
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDTO> find(@PathVariable(name = "id") Long id) throws ServiceException, ControllerException, NotExistEntityException {
        if (id > 0){
            GiftCertificateDTO giftCertificateDTO = giftCertificateService.find(id);
            return ResponseEntity.ok(giftCertificateDTO);
        } else {
            log.error("Negative id exception");
            throw  new ControllerException("Negative id exception");
        }
    }

    /**
     * Add gift certificate.
     *
     * @param giftCertificateDTO the certificate dto
     * @return the response entity
     * @throws ServiceException the service exception
     * @throws ControllerException if entity fields not valid
     */
    @PostMapping()
    public ResponseEntity<GiftCertificateDTO> add(@RequestBody @Valid GiftCertificateDTO giftCertificateDTO, BindingResult bindingResult)
            throws ServiceException, ControllerException {
        if (bindingResult.hasErrors()){
            log.error(bindingResultHandler(bindingResult));
            throw new ControllerException(bindingResultHandler(bindingResult));
        } else {
            GiftCertificateDTO result = giftCertificateService.add(giftCertificateDTO);
            return ResponseEntity.ok(result);
        }
    }

    /**
     * Update gift certificate. Mark the fields that are not specified for updating null.
     *
     * @param giftCertificateDTO the certificate dto
     * @return the response entity
     * @throws ServiceException the service exception
     * @throws ControllerException if entity fields not valid
     */
    @PutMapping()
    public ResponseEntity<GiftCertificateDTO> update(@RequestBody @Valid GiftCertificateDTO giftCertificateDTO,
                                                     BindingResult bindingResult) throws ServiceException, ControllerException, NotExistEntityException {
        if (bindingResult.hasErrors()){
            log.error(bindingResultHandler(bindingResult));
            throw new ControllerException(bindingResultHandler(bindingResult));
        } else {
            GiftCertificateDTO result = giftCertificateService.update(giftCertificateDTO);
            return ResponseEntity.ok(result);
        }
    }

    /**
     * Delete gift certificate by id.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException the service exception
     * @throws ControllerException if id is incorrect
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) throws ServiceException, ControllerException, NotExistEntityException {
        if (id > 0) {
            giftCertificateService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new ControllerException("Negative id exception");
        }

    }

    /**
     * Create the page with gift certificate by filter params
     *
     * @param tags names tags
     * @param part it is part of search
     * @param sortBy params that must be sort
     * @param type kind of sort
     * @param page page
     * @param size size list on page
     * @return list of gift certificate
     * @throws ServiceException if cant return list
     * @throws IncorrectArgumentException if incorrect argument
     */
    @GetMapping
    public ResponseEntity<PagedModel<GiftCertificateDTO>> filterByParameter(
            @RequestParam(value = "tags", required = false) List<String> tags,
            @RequestParam(value = "part", required = false) String part,
            @RequestParam(value = "sort_by", required = false) String sortBy,
            @RequestParam(value = "type", required = false) SortType type,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size
    ) throws ServiceException, IncorrectArgumentException {
        FilterParams filterParams = FilterParams.builder()
                .tags(tags)
                .part(part)
                .sortBy(sortBy)
                .type(type)
                .build();
        List<GiftCertificateDTO> list = giftCertificateService.filterByParameters(filterParams, page, size);
        long count = giftCertificateService.count();
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, count);
        List<Link> linkList = buildLink(tags, part, sortBy, type, page, size, pageMetadata.getTotalPages());
        PagedModel<GiftCertificateDTO> pagedModel = PagedModel.of(list, pageMetadata, linkList);
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Get default message by validate exception
     *
     * @param bindingResult exceptions of validate
     * @return string default message of exception
     */
    private String bindingResultHandler(BindingResult bindingResult){
        return bindingResult.getAllErrors().get(0).getDefaultMessage();
    }


    /**
     * Build dynamic page with link
     *
     * @return link on page
     * @throws ServiceException if cant create link
     * @throws IncorrectArgumentException if incorrect argument
     */
    private List<Link> buildLink(List<String> tags, String part, String sortBy, SortType type, int page, int size,
                                 long maxPage) throws ServiceException, IncorrectArgumentException {
        List<Link> linkList = new ArrayList<>();
        Link self = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                        .methodOn(GiftCertificateController.class)
                        .filterByParameter(tags, part, sortBy, type, page, size)
        ).withRel("self").expand();
        linkList.add(self);
        if (page > 1) {
            Link previous = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder
                            .methodOn(GiftCertificateController.class)
                            .filterByParameter(tags, part, sortBy, type, page - 1, size)
            ).withRel("previous").expand();
            linkList.add(previous);
        }
        if (maxPage > page) {
            Link next = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder
                            .methodOn(GiftCertificateController.class)
                            .filterByParameter(tags, part, sortBy, type, page + 1, size)
            ).withRel("next").expand();
            linkList.add(next);
        }
        return linkList;
    }
}
