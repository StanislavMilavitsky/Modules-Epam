package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Entity dao and the fields should not change
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tag implements Serializable {

    @Positive(message = "Should be positive")
    private Long id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Description must be between 2 and 60 characters")
    private String name;
}
