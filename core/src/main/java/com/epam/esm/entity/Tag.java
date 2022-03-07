package com.epam.esm.entity;

import com.epam.esm.dao.AuditListener;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Entity dao
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
@EntityListeners(AuditListener.class)
@Entity(name = "tag")
public class Tag extends GenericEntity implements Comparable<Tag> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "Should be positive")
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Description must be between 2 and 60 characters")
    @Column(name = "name")
    private String name;

    @Override
    public int compareTo(Tag tag) {
        return name.compareTo(tag.getName());
    }
}
