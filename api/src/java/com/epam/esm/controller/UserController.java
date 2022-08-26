package com.epam.esm.controller;


import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.ControllerException;
import com.epam.esm.exception.IncorrectArgumentException;
import com.epam.esm.exception.NotExistEntityException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User RestAPI.
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController extends CommonController<UserDTO> {

    private final UserService userService;
    private final OrderService orderService;

    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * Find user by id use method from service layer
     *
     * @param id user
     * @return entity user
     * @throws ServiceException if cant find user
     * @throws NotExistEntityException if user have not been exist
     * @throws ControllerException if negative id
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> find(@PathVariable(name = "id") Long id)
            throws ServiceException, NotExistEntityException, ControllerException {
            if(id > 0) {
                UserDTO userDTO = userService.find(id);
                return ResponseEntity.ok(userDTO);
            } else {
                log.error("Negative id exception!");
                throw  new ControllerException("Negative id exception!");
            }
    }

    /**
     * Create user order
     *
     * @param id user
     * @param orderDTO entity of order
     * @return created entity of order
     * @throws ServiceException if cant create order
     * @throws ControllerException if negative id
     */

    @PostMapping("/{id}/orders")
    public ResponseEntity<OrderDTO> orderGiftCertificate(@PathVariable(name = "id") Long id, @RequestBody OrderDTO orderDTO)
            throws ServiceException, ControllerException {
        if(id > 0){
            orderDTO.setUserId(id);
            orderDTO.setPurchaseTime(LocalDateTime.now().toString());
            OrderDTO order = orderService.add(orderDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } else {
            log.error("Negative id exception!");
            throw  new ControllerException("Negative id exception!");
        }

    }

    /**
     * Get top user top tag
     *
     * @return top tag of top user
     * @throws ServiceException if cant find top tag
     */
    @GetMapping("/top-tag")
    public ResponseEntity<TagDTO> getTopUserTag() throws ServiceException {
        TagDTO tag = orderService.getTopUserTag();
        return ResponseEntity.ok(tag);
    }

    /**
     * Find all users use method from service layer
     *
     * @param page page
     * @param size size of page
     * @return list of users
     * @throws ServiceException if cant find users
     * @throws IncorrectArgumentException if incorrect argument
     */
    @Override
    @GetMapping
    public ResponseEntity<PagedModel<UserDTO>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size
    ) throws ServiceException, IncorrectArgumentException {
        List<UserDTO> tags = userService.findAll(page, size);
        long count = userService.count();
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, count);
        List<Link> linkList = buildLink(page, size, (int) pageMetadata.getTotalPages());
        PagedModel<UserDTO> pagedModel = PagedModel.of(tags, pageMetadata, linkList);
        return ResponseEntity.ok(pagedModel);
    }

}
