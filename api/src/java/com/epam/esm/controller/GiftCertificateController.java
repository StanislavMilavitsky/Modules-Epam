package com.epam.esm.controller;


import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.common.SortType;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Gift certificate RestAPI.
 */
@RestController
@RequestMapping("/certificate")
public class GiftCertificateController {

    private final static Logger logger = LogManager.getLogger(GiftCertificateServiceImpl.class);

    private final GiftCertificateService giftCertificateService;

    private GiftCertificateController(GiftCertificateService giftCertificateService) {
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
    public ResponseEntity<GiftCertificateDTO> find(@PathVariable(name = "id") Long id) throws ServiceException, ControllerException {
        if (id > 0){
            GiftCertificateDTO giftCertificateDTO = giftCertificateService.find(id);
            return ResponseEntity.ok(giftCertificateDTO);
        } else {
            logger.error("Negative id exception");
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
            logger.error(bindingResultHandler(bindingResult));
            throw new ControllerException(bindingResultHandler(bindingResult));
        }
        GiftCertificateDTO result = giftCertificateService.add(giftCertificateDTO);
        return ResponseEntity.ok(result);
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
                                                     BindingResult bindingResult) throws ServiceException, ControllerException {
        if (bindingResult.hasErrors()){
            logger.error(bindingResultHandler(bindingResult));
            throw new ControllerException(bindingResultHandler(bindingResult));
        }
        GiftCertificateDTO result = giftCertificateService.update(giftCertificateDTO);
        return ResponseEntity.ok(result);
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
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) throws ServiceException, ControllerException {
        if (id > 0) {
            long result = giftCertificateService.delete(id);
            String deleteSuccessful = String.format("Delete by id=%d successful!", id);
            String deleteUnsuccessful = String.format("Delete by id=%d unsuccessful!", id);
            return result != -1L ? ResponseEntity.ok(deleteSuccessful) : ResponseEntity.ok(deleteUnsuccessful);
        } else {
            throw new ControllerException("Negative id exception");
        }

    }

    /**
     * Find gift certificate by tag name.
     *
     * @param tagName the tag name
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @GetMapping("/search-name")
    public ResponseEntity<List<GiftCertificateDTO>> findByTag(@RequestParam(name = "name") String tagName)
            throws ServiceException {
        List<GiftCertificateDTO>giftCertificateDTO = giftCertificateService.findByTag(tagName);
        return ResponseEntity.ok(giftCertificateDTO);
    }

    /**
     * Search gift certificate by name or description part.
     *
     * @param part the part
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @GetMapping("/search-part")
    public ResponseEntity<List<GiftCertificateDTO>> searchByNameOrDesc(@RequestParam(name = "part") String part)
            throws ServiceException {
        List<GiftCertificateDTO> giftCertificateDTO = giftCertificateService.searchByNameOrDescription(part);
        return ResponseEntity.ok(giftCertificateDTO);
    }

    /**
     * Sort gift certificates by name.
     *
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort-by-name")
    public ResponseEntity<List<GiftCertificateDTO>> sortByName(@RequestParam(name = "sort") SortType sortType) throws ServiceException {
        List<GiftCertificateDTO> giftCertificateDTO = giftCertificateService.sortByName(sortType);
        return ResponseEntity.ok(giftCertificateDTO);
    }

    /**
     * Sort gift certificates by date.
     *
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort-by-date")
    public ResponseEntity<List<GiftCertificateDTO>> sortByDate(@RequestParam(name = "sort") SortType sortType) throws ServiceException {
        List<GiftCertificateDTO> giftCertificateDTO = giftCertificateService.sortByDate(sortType);
        return ResponseEntity.ok(giftCertificateDTO);
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
}
