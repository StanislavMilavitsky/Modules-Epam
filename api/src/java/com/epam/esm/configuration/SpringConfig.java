package com.epam.esm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

/**
 * Spring configuration with beans for spring
 */
@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
@PropertySource("classpath:application-${spring.profiles.active}.properties")
public class SpringConfig implements WebMvcConfigurer {
    /**
     * Environment from spring container(environments get from application.properties)
     */
    private Environment environment;

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * Set properties for database
     *
     * @return bean driver manager by database
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("datasource.driverClassName"));
        dataSource.setUrl(environment.getProperty("datasource.driverClassName"));
        dataSource.setUsername(environment.getProperty("datasource.driverClassName"));
        dataSource.setPassword(environment.getProperty("datasource.driverClassName"));

        return dataSource;
    }

    /**
     * Make connection with database and give him data manager in constructor
     *
     * @return connection with data base
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

}
