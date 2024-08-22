package com.example.limits_microservice.mapper;

import com.example.limits_microservice.entity.LimitEntity;
import com.example.limits_microservice.model.LimitDTO;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring" )
public interface LimitEntityToDtoMapper {
    LimitEntity limitDtoToEntity( LimitDTO limitDTO );
    LimitDTO limitEntityToDTO( LimitEntity limitEntity );
}
