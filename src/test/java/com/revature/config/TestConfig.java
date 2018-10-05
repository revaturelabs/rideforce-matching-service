package com.revature.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.revature.rideshare.matching.repositories")
@EnableTransactionManagement
@ComponentScan("com.revature.rideshare.matching")

public class TestConfig {

}
