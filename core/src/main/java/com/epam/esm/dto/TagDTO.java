package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Entity service and can be change
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TagDTO {

    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Description must be between 2 and 60 characters")
    private String name;
}
