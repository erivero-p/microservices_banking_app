package com.talant.bootcamp.customerservice.mapper;

import com.talant.bootcamp.customerservice.models.dto.CustomerDTO;
import com.talant.bootcamp.customerservice.models.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO toDTO (CustomerEntity ce);

    CustomerEntity toEntity(CustomerDTO dto);

    @Mapping(target="id", ignore = true)
    CustomerEntity toEntityToCreate (CustomerDTO dto);

}
