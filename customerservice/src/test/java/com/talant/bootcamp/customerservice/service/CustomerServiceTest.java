package com.talant.bootcamp.customerservice.service;

import com.talant.bootcamp.customerservice.exception.BadRequestException;
import com.talant.bootcamp.customerservice.mapper.CustomerMapper;
import com.talant.bootcamp.customerservice.models.dto.CustomerDTO;
import com.talant.bootcamp.customerservice.models.entity.CustomerEntity;
import com.talant.bootcamp.customerservice.repository.CustomersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer Service Tests")
public class CustomerServiceTest {

    @Mock
    private CustomersRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomersService customerService;

    private CustomerEntity customerEntity;
    private CustomerDTO customerDTO;
    private CustomerEntity customerToAdd;
    private CustomerDTO customerDtoShow;


    @Nested
    class CrudOperations{
        @BeforeEach
        void setUp(){

            //Set customerEntity
            customerEntity = new CustomerEntity();
            customerEntity.setName("Julio");
            customerEntity.setEmail("julito@example.com");
            customerEntity.setBirthday(LocalDate.now().minusYears(18));
            customerEntity.setId(UUID.randomUUID());

            //Set standard customer DTO
            customerDTO = new CustomerDTO(
                    customerEntity.getId(),
                    customerEntity.getName(),
                    customerEntity.getBirthday(),
                    customerEntity.getEmail()
                    );

        }

        /**
         * Set customer DTO to add
         * */
        private void setCustomerToCreate(){
            customerToAdd = new CustomerEntity();
            customerToAdd.setName(customerEntity.getName());
            customerToAdd.setEmail(customerEntity.getEmail());
            customerToAdd.setBirthday(customerEntity.getBirthday());
        }
        /**
         * Set customer DTO to add
         * */
        private void setCustomerToShow(){
            customerDtoShow = new CustomerDTO(
                    customerEntity.getId(),
                    customerEntity.getName(),
                    customerEntity.getBirthday(),
                    customerEntity.getEmail()
            );
        }


        @Test
        @DisplayName("Should create a customer successfully")
        void shouldCreateCustomer(){
            setCustomerToCreate();
            setCustomerToShow();

            //Arrange
            when(customerRepository.existsByEmail(customerDTO.email())).thenReturn(false);
            when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);
            when(customerMapper.toEntityToCreate(customerDTO)).thenReturn(customerToAdd);
            when(customerMapper.toDtoToShow(customerEntity)).thenReturn(customerDtoShow);

            //Act
            CustomerDTO customerCreated = customerService.createCustomer(customerDTO);

            //Assert
            assertNotNull(customerCreated);
            assertEquals(customerCreated.id(), customerDtoShow.id());
            assertEquals(customerCreated.name(), customerDtoShow.name());
            assertEquals(customerCreated.email(), customerDtoShow.email());
            assertEquals(customerCreated.birthday(), customerDtoShow.birthday());

        }
        @Test
        @DisplayName("Should throw an exception when trying to add a new customer with a register email")
        void emailAlreadyRegistered(){
            setCustomerToCreate();
            setCustomerToShow();

            customerToAdd.setBirthday(LocalDate.now().minusYears(17));

            //Arrange
            when(customerRepository.existsByEmail(customerDTO.email())).thenReturn(true);

            //Act & Assert
            assertThrows(BadRequestException.class, ()->{
                customerService.createCustomer(customerDTO);
            });

        }

    }



}
