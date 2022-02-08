package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity service and can be change
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TagDTO {

    private Long id;
    private String name;
}
