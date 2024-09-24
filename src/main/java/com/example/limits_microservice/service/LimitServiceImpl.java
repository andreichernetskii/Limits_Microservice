package com.example.limits_microservice.service;

import com.example.limits_microservice.enums.LimitType;
import com.example.limits_microservice.entity.LimitEntity;
import com.example.limits_microservice.exception_handler.exceptions.ForbiddenException;
import com.example.limits_microservice.exception_handler.exceptions.NotFoundException;
import com.example.limits_microservice.exception_handler.exceptions.UnprocessableEntityException;
import com.example.limits_microservice.exception_handler.exceptions.UserNotAuthenticatedException;
import com.example.limits_microservice.kafka.producer.KafkaProducerService;
import com.example.limits_microservice.mapper.LimitEntityToDtoMapper;
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
    private final LimitEntityToDtoMapper mapper;
    private final KafkaProducerService kafkaProducerService;

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
        return limitRepository.getAllLimitsWithoutZero( getCurrentUsername() )
                .stream().map( mapper::limitEntityToDTO ).toList();
    }

    @Override
    public void addLimit( LimitDTO limitDTO ) {
        LimitEntity limitEntity = mapper.limitDtoToEntity( limitDTO );
        limitEntity.setUserId( getCurrentUsername() );

        if ( isLimitExists( limitEntity ) ) {
            throw new UnprocessableEntityException( "Limit already exist!" );
        }

        limitRepository.save( limitEntity );
        kafkaProducerService.sendLimitDTO( limitDTO );
    }

    @Override
    public void updateLimit( LimitDTO limitDTO ) {
        Optional<LimitEntity> optimalLimit = limitRepository.findById( limitDTO.getId() );

        if ( optimalLimit.isEmpty() ) {
            throw new NotFoundException( "Limit does not exist!" );
        }

        if ( optimalLimit.get().getLimitType() == LimitType.ZERO ) {
            throw new ForbiddenException( "Cannot delete the default limit." );
        }

        limitRepository.save( mapper.limitDtoToEntity( limitDTO ) );
    }

    @Override
    public List<String> getLimitTypes() {
        return Arrays.stream( LimitType.values() )
                .map( Enum::toString )
                .toList();
    }

    private boolean isLimitExists( LimitEntity limitEntityToCheck ) {
        return limitRepository.existsBy( getCurrentUsername(), limitEntityToCheck.getLimitType(), limitEntityToCheck.getCategory() );
    }
}
