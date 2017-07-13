package org.xsnake.cloud.common.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@RefreshScope
public class DataSourceConfiguration {

	@Value(value = "${crm_database.url}")
	String url;

	@Value(value = "${crm_database.driver}")
	String driver;
	
	@Value(value = "${crm_database.username}")
	String username;
	
	@Value(value = "${crm_database.password}")
	String password;
	
	@Bean
	public DataSource dataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(url);
		dataSource.setDriverClassName(driver);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
	
}
