package com.example.limits_microservice.repository;

import com.example.limits_microservice.entity.LimitEntity;
import com.example.limits_microservice.enums.LimitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Limit entities in the database.
 */
@Repository
public interface LimitRepository extends JpaRepository<LimitEntity, Long> {

    // Retrieves all limits except for the ZERO type associated with a specific account.
    @Query( """
            SELECT limits
            FROM LimitEntity limits
            WHERE limits.limitType != 'ZERO'
            AND limits.account.id = :userId
            """)
    List<LimitEntity> getAllLimitsWithoutZero( @Param( "userId" ) String userId );

    @Query( """
          SELECT 
          CASE WHEN COUNT( limitType ) > 0 
          THEN true ELSE false
          END 
          FROM LimitEntity 
          WHERE ( :userId IS NULL OR userId = :userId )
          AND limitType = :limitType
          AND ( :category IS NULL OR category = :category )
          """ )
    Boolean existsBy( @Param( "userId" ) String userId,
                      @Param( "limitType" ) LimitType limitType,
                      @Param( "category" ) String category );
}
