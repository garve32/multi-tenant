package com.example.multitenant.config;

import com.example.multitenant.context.TenantEnum;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(value = {"com.example.multitenant.repository"}, annotationClass = Mapper.class, sqlSessionFactoryRef = "sessionFactory")
public class TenantDataSourceConfig {

    @Primary
    @Bean
    public DataSource dataSourceRouter() {

        TenantRoutingDataSource routingDataSource = new TenantRoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();

        targetDataSources.put(TenantEnum.COMPANY_A.getCode(), tenant1DataSource());
        targetDataSources.put(TenantEnum.COMPANY_B.getCode(), tenant2DataSource());
        targetDataSources.put(TenantEnum.COMPANY_C.getCode(), tenant3DataSource());

        routingDataSource.setTargetDataSources(targetDataSources);
        return routingDataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.tenant1")
    protected DataSource tenant1DataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.tenant2")
    protected DataSource tenant2DataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.tenant3")
    protected DataSource tenant3DataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "sessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return factoryBean.getObject();
    }

}
