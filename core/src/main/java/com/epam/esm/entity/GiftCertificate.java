package com.epam.esm.entity;

import com.epam.esm.dao.AuditListener;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Entity dao
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
@EntityListeners(AuditListener.class)
@Entity(name = "gift_certificate")
public class GiftCertificate extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "Should be positive")
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Column(name = "description")
    @Size(min = 2, max = 60, message = "Description must be between 2 and 60 characters")
    private String description;

    @Column(name = "price")
    @Positive(message = "Should be positive")
    private BigDecimal price;

    @Column(name = "duration")
    @Positive(message = "Should be positive")
    private Integer duration;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.s")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.s")
    private LocalDateTime lastUpdateDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "gift_certificate_has_tag", joinColumns = @JoinColumn(name = "id_gift_certificate"),
            inverseJoinColumns = @JoinColumn(name = "id_tag"))
    private List<Tag> tags;

    @OneToMany(mappedBy = "giftCertificate", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<OrderGiftCertificate> orderGiftCertificateSet;
}
