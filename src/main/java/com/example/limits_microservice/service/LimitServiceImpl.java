package com.example.limits_microservice.service;

import com.example.limits_microservice.enums.LimitType;
import com.example.limits_microservice.entity.LimitEntity;
import com.example.limits_microservice.exception_handler.exceptions.ForbiddenException;
import com.example.limits_microservice.exception_handler.exceptions.NotFoundException;
import com.example.limits_microservice.exception_handler.exceptions.UserNotAuthenticatedException;
import com.example.limits_microservice.model.LimitDTO;
import com.example.limits_microservice.repository.LimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Limit entities.
 */
@Service
@RequiredArgsConstructor
public class LimitServiceImpl implements LimitService {
    private final LimitRepository limitRepository;

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if ( authentication != null && authentication.getPrincipal() instanceof UserDetails ) {
            return ( ( UserDetails ) authentication.getPrincipal() ).getUsername();
        }

        throw new UserNotAuthenticatedException( "User is not authorized!" );
    }

    @Transactional
    @Override
    public void deleteLimit( Long limitId ) {
        Optional<LimitEntity> optionalLimit = limitRepository.findById( limitId );

        if ( optionalLimit.isEmpty() ) {
            throw new NotFoundException( "This limit does not exist." );
        }

        if ( optionalLimit.get().getLimitType() == LimitType.ZERO ) {
            throw new ForbiddenException( "Cannot delete the default limit." );
        }

        limitRepository.deleteById( limitId );
    }

    @Override
    public List<LimitDTO> getLimits() {
        //todo: mapper here
        return limitRepository.getAllLimitsWithoutZero( getCurrentUsername() );
    }

    @Override
    public void addLimit( LimitDTO limitDTO ) {
        //todo: mapper here
        String userId = getCurrentUsername();
        LimitEntity limitEntity = createLimit( limitDTO );
        limitEntity.setUserId( userId );

        if ( isLimitExists( userId, limitEntity ) ) {
            throw new UnprocessableEntityException( "Limit already exist!" );
        }

        limitRepository.save( limitEntity );
    }

    @Override
    public void updateLimit( LimitDTO limitDTO ) {
        Optional<LimitEntity> optimalLimit = limitRepository.findLimit( limitId, account.getId() );

        if ( !optimalLimit.isPresent() ) {
            throw new NotFoundException( "Limit with ID " + limitId + " not exist!" );
        }

        if ( optimalLimit.get().getLimitType() == LimitType.ZERO ) {
            throw new ForbiddenException( "Cannot delete the default limit." );
        }

        limitRepository.save( limitEntity );
    }

    @Override
    public List<String> getLimitTypes() {
        return Arrays.stream( LimitType.values() )
                .map( Enum::toString )
                .toList();
    }

    private boolean isLimitExists( String userId, LimitEntity limitEntityToCheck ) {
        return limitRepository.existsBy( userId, limitEntityToCheck.getLimitType(), limitEntityToCheck.getCategory() );
    }
}
