package com.osp.core.container.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"com.kd"},excludeFilters={@Filter(type=FilterType.ANNOTATION,value=EnableWebMvc.class)}) 
@ImportResource("classpath:config/spring/spring-base.xml")
public class SpringConfig {
}
