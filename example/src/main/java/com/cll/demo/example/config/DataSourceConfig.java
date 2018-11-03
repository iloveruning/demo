package com.cll.demo.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author chenliangliang
 * @date 2018/11/3
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class DataSourceConfig extends HikariConfig {

    @Bean
    public DataSource hikariDataSource(){
        this.addDataSourceProperty("useUnicode",true);
        this.addDataSourceProperty("characterEncoding","utf8");
        this.setConnectionInitSql("SET NAMES utf8mb4");
        return new HikariDataSource(this);
    }
}
