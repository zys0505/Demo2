package com.osp.core.container.mybatis;

import java.util.Properties;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.github.pagehelper.PageHelper;
/**
 * 没有使用，过于复杂不利于调试。
 * @author liudonghe
 *
 */
@Configuration
public class MybatisConfig {
	@Bean
	public SqlSessionFactoryBean getSqlSessionFactoryBean(@Qualifier("dataSource")DataSource dataSource){
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		ClassPathResource classPathResource = new ClassPathResource("classpath:com/kd/*/mapping/*.xml");
		sqlSessionFactoryBean.setMapperLocations(new Resource[]{classPathResource});
		com.github.pagehelper.PageHelper helper = new PageHelper();
		Properties properties = new Properties();
		properties.put("dialect", "oracle");
		helper.setProperties(properties);
		return sqlSessionFactoryBean;	
	}
}
