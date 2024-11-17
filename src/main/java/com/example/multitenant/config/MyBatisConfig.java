package com.example.multitenant.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyBatisConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSourceRouter() {

        TenantRoutingDataSource ds = new TenantRoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        DataSource dataSource1 = DataSourceBuilder.create()
                .url(env.getProperty("spring.datasource.tenant1.url"))
                .username(env.getProperty("spring.datasource.tenant1.username"))
                .password(env.getProperty("spring.datasource.tenant1.password"))
                .driverClassName(env.getProperty("spring.datasource.tenant1.driverClassName"))
                .build();

        DataSource dataSource2 = DataSourceBuilder.create()
                .url(env.getProperty("spring.datasource.tenant2.url"))
                .username(env.getProperty("spring.datasource.tenant2.username"))
                .password(env.getProperty("spring.datasource.tenant2.password"))
                .driverClassName(env.getProperty("spring.datasource.tenant2.driverClassName"))
                .build();

        targetDataSources.put("tenant1", dataSource1);
        targetDataSources.put("tenant2", dataSource2);

        ds.setTargetDataSources(targetDataSources);
        return ds;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return factoryBean.getObject();
    }

}
