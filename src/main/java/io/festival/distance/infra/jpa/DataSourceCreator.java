package io.festival.distance.infra.jpa;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class DataSourceCreator {
    public Map<Object,Object> createImmutableDataSource(
        DataSource masterDataSource,
        DataSource slaveDataSource
    ){
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("master", masterDataSource);
        dataSourceMap.put("slave", slaveDataSource);
        return Collections.unmodifiableMap(dataSourceMap);
    }

    public RoutingDataSource createRoutingDataSource(
        Map<Object,Object> immutableDataSourceMap,
        DataSource masterDataSource
    ) {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(immutableDataSourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        return routingDataSource;
    }
}
