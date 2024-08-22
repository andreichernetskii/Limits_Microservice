package com.example.limits_microservice.model;

import com.example.limits_microservice.enums.LimitType;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) class for transferring information about limits.
 */
@Data
@AllArgsConstructor
@Builder
public class LimitDTO {
    @Nullable
    private Long id;
    @Nullable
    private String userId;
    private LimitType limitType;
    private BigDecimal limitAmount;
    private String category;
    private LocalDate creationDate;
}
