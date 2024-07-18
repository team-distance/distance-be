/*
package io.festival.distance.infra.jpa.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MasterDataSource {

    public static final String DISTANCE_MASTER_DATASOURCE = "distanceMasterDataSource";
    @Value("${spring.datasource.master.hikari.driver-class-name}")
    private String masterDriverClassName;

    @Value("${spring.datasource.master.hikari.jdbc-url}")
    private String masterJdbcUrl;

    @Value("${spring.datasource.master.hikari.username}")
    private String masterUsername;

    @Value("${spring.datasource.master.hikari.password}")
    private String masterPassword;

    @Bean(DISTANCE_MASTER_DATASOURCE)
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
            .driverClassName(masterDriverClassName)
            .url(masterJdbcUrl)
            .username(masterUsername)
            .password(masterPassword)
            .build();
    }
}
*/
