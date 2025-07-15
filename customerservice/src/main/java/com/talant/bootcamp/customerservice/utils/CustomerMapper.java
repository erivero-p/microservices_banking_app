package com.talant.bootcamp.customerservice.utils;

import com.talant.bootcamp.customerservice.models.dto.CustomerDTO;
import com.talant.bootcamp.customerservice.models.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    public CustomerDTO toDTO(CustomerEntity entity);
    public CustomerEntity toEntity(CustomerDTO dto);
}
