package io.festival.distance.infra.jpa;


import static io.festival.distance.infra.jpa.config.MasterDataSource.DISTANCE_MASTER_DATASOURCE;
import static io.festival.distance.infra.jpa.config.SlaveDataSource.DISTANCE_SLAVE_DATASOURCE;

import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
@RequiredArgsConstructor
@Profile("prod")
public class DataSourceConfiguration {

    private final DataSourceCreator dataSourceCreator;

    @Bean
    public DataSource routingDataSource(
        @Qualifier(DISTANCE_MASTER_DATASOURCE) DataSource masterDataSource,
        @Qualifier(DISTANCE_SLAVE_DATASOURCE) DataSource slaveDataSource
    ) {
        Map<Object, Object> immutableDataSourceMap = dataSourceCreator.createImmutableDataSource(
            masterDataSource, slaveDataSource
        );
        return dataSourceCreator.createRoutingDataSource(immutableDataSourceMap, masterDataSource);
    }

    @Primary
    @Bean
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}