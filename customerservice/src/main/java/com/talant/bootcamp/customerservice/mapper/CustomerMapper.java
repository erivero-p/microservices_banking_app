package com.talant.bootcamp.customerservice.mapper;

import com.talant.bootcamp.customerservice.models.dto.CustomerDTO;
import com.talant.bootcamp.customerservice.models.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    public CustomerDTO toDTO(CustomerEntity entity);
    public CustomerEntity toEntity(CustomerDTO dto);

    @Mapping(target="id", ignore = true)
    public CustomerEntity toEntityToCreate(CustomerDTO dto);

    //When more specific attributes are added to the DTO, then add the @Mapping
    public CustomerDTO toDtoToShow(CustomerEntity entity);

}
