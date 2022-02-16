package com.epam.esm.controller;


import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.SortType;
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
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDTO> find(@PathVariable(name = "id") Long id) throws ServiceException {
        GiftCertificateDTO giftCertificateDTO = giftCertificateService.find(id);
        return ResponseEntity.ok(giftCertificateDTO);
    }

    /**
     * Add gift certificate.
     *
     * @param giftCertificateDTO the certificate dto
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @PostMapping(value = "/")
    public ResponseEntity<GiftCertificateDTO> add(@RequestBody @Valid GiftCertificateDTO giftCertificateDTO,
                                                  BindingResult bindingResult) throws ServiceException, ControllerException {
        if (bindingResult.hasErrors()){
            logger.error("Wrong value of fields gift certificate");
            throw new ControllerException("Wrong value of fields gift certificate");
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
     */
    @PutMapping(value = "/", consumes = "application/json")
    public ResponseEntity<GiftCertificateDTO> update(@RequestBody @Valid GiftCertificateDTO giftCertificateDTO,
                                                     BindingResult bindingResult) throws ServiceException, ControllerException {
        if (bindingResult.hasErrors()){
            logger.error("Wrong value of fields gift certificate");
            throw new ControllerException("Wrong value of fields gift certificate");
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
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) throws ServiceException {
        long result = giftCertificateService.delete(id);
        return result != -1L ? ResponseEntity.ok("Delete successful!") : ResponseEntity.ok("Delete unsuccessful!");
    }

    /**
     * Find gift certificate by tag name.
     *
     * @param tagName the tag name
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @GetMapping("/byName/{name}")
    public ResponseEntity<List<GiftCertificateDTO>> findByTag(@PathVariable(name = "name") String tagName)
            throws ServiceException {
        List<GiftCertificateDTO> giftCertificateDTO = giftCertificateService.findByTag(tagName);
        return ResponseEntity.ok(giftCertificateDTO);
    }

    /**
     * Search gift certificate by name or description part.
     *
     * @param part the part
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @GetMapping("/byPart/{part}")
    public ResponseEntity<List<GiftCertificateDTO>> searchByNameOrDesc(@PathVariable(name = "part") String part)
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
    @GetMapping("/sortByName{sort}")
    public ResponseEntity<List<GiftCertificateDTO>> sortByName(@PathVariable(name = "sort") SortType sortType) throws ServiceException {
        List<GiftCertificateDTO> giftCertificateDTO = giftCertificateService.sortByName(sortType);
        return ResponseEntity.ok(giftCertificateDTO);
    }

    /**
     * Sort gift certificates by date.
     *
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @GetMapping("/sortByDate{sort}")
    public ResponseEntity<List<GiftCertificateDTO>> sortByDate(@PathVariable(name = "sort") SortType sortType) throws ServiceException {
        List<GiftCertificateDTO> giftCertificateDTO = giftCertificateService.sortByDate(sortType);
        return ResponseEntity.ok(giftCertificateDTO);
    }
}
