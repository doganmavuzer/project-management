package com.project.management.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Value("${custom.database.class-name}")
    private String className;

    @Value("${custom.database.url}")
    private String url;

    @Value("${custom.database.user-name}")
    private String userName;

    @Value("${custom.database.password}")
    private String password;

    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName(className)
                .url(url)
                .username(userName)
                .password(password)
                .build();
    }

}
