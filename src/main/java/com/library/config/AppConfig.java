package com.library.config;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.library")
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource d = new DriverManagerDataSource();
        d.setDriverClassName("oracle.jdbc.OracleDriver");
        d.setUrl("jdbc:oracle:thin:@localhost:1521/XEPDB1");
        d.setUsername("LIBRARY");
        d.setPassword("Your_password");
        return d;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource d) {
        return new JdbcTemplate(d);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource d) {
        return new DataSourceTransactionManager(d);
    }
}