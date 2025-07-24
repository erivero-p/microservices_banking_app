package com.talant.bootcamp.accountservice.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
//@ComponentScan
@EnableJpaRepositories("com.talant.bootcamp.repository")
// El cambio está en la siguiente anotación, le tienes que indicar donde estan las entidades, de lo contrario no pueden ser usadas como tal.
@EntityScan(basePackages = "com.talant.bootcamp.accountservice.entity")
public class AccountAppConfiguration {
    //Account entity recognition is below

}
