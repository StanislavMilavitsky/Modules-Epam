package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Entity dao and the fields should not change
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tag implements Serializable {

    private Long id;
    private String name;
}
