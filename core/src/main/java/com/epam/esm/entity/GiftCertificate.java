package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GiftCertificate implements Serializable {

    @Positive
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Size(min = 2, max = 60, message = "Description must be between 2 and 60 characters")
    private String description;

    @Positive(message = "Should be positive")
    private BigDecimal price;

    @Positive(message = "Should be positive")
    private Integer duration;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.s")
    private LocalDateTime createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.s")
    private LocalDateTime lastUpdateDate;
}
