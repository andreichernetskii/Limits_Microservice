package com.example.limits_microservice.entity;

import com.example.limits_microservice.enums.LimitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity class representing the table of limits that can be created by users.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table( name = "Limits" )
public class LimitEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String userId;
    @Enumerated( EnumType.STRING )
    @Column( nullable = false )
    private LimitType limitType;
    @Column( nullable = false )
    private BigDecimal limitAmount;
    private String category;
    private LocalDate creationDate;
}
