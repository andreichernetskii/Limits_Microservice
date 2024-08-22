package com.example.limits_microservice.repository;

import com.example.limits_microservice.enums.LimitType;
import com.example.limits_microservice.entity.LimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    // Retrieves a specific limitEntity associated with a given account.
    @Query( """
            SELECT limitEntity
            FROM LimitEntity limitEntity
            WHERE limitEntity.userId = :userId
            AND limitEntity.id = :limitId
            """)
    Optional<LimitEntity> findLimit( @Param( "limitId" ) Long limitId,
                                     @Param( "userId" ) String userId );

    // Retrieves the limitEntity amount for a specific limitEntity type.
    @Query( """
            SELECT limitAmount
            FROM LimitEntity
            WHERE limitType = :limitType
            """ )
    Double getLimitAmountByLimitType( @Param( "limitType" ) LimitType limitType );

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

    @Modifying
    @Query( """
            DELETE 
            FROM LimitEntity
            WHERE limitType = :limitType
            """ )
    void deleteByLimitType( @Param( "limitType" ) LimitType limitType );


}
