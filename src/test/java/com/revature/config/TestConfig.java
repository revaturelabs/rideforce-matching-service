<<<<<<< HEAD
package com.revature.config;

import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * custom Test configuration that can be used to define additional beans or
 * customizations for a test.
 *
 * @see SpringBootTestContextBootstrapper
 */

@Configuration
@EnableJpaRepositories("com.revature.rideshare.matching.repositories")
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.revature.rideshare" })
public class TestConfig {

}
=======
package com.revature.config;

import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * custom Test configuration that can be used to define additional beans or
 * customizations for a test.
 *
 * @see SpringBootTestContextBootstrapper
 */

@Configuration
@EnableJpaRepositories("com.revature.rideshare.matching.repositories")
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.revature.rideshare" })
public class TestConfig {

}
>>>>>>> master
