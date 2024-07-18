/*
package io.festival.distance.infra.jpa.config;


import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SlaveDataSource {
    public static final String DISTANCE_SLAVE_DATASOURCE = "distanceSlaveDataSource";
    @Value("${spring.datasource.slave.hikari.jdbc-url}")
    private String slaveJdbcUrl;

    @Value("${spring.datasource.slave.hikari.driver-class-name}")
    private String slaveDriverClassName;

    @Value("${spring.datasource.slave.hikari.username}")
    private String slaveUsername;

    @Value("${spring.datasource.slave.hikari.password}")
    private String slavePassword;

    @Bean(DISTANCE_SLAVE_DATASOURCE)
    public DataSource slaveDataSource(){
        return DataSourceBuilder.create()
            .driverClassName(slaveDriverClassName)
            .url(slaveJdbcUrl)
            .username(slaveUsername)
            .password(slavePassword)
            .build();
    }
}
*/
