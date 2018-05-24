package com.osp.core.container.mvc;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
@EnableWebMvc
//指明controller所在的包名
@ComponentScan(basePackages = {"com.kd.*.action"})
public class MvcConfig extends WebMvcConfigurerAdapter  {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");
		registry.addResourceHandler("/common/**").addResourceLocations("/WEB-INF/common/");
		registry.addResourceHandler("/dynamic/**").addResourceLocations("/WEB-INF/dynamic/");
		super.addResourceHandlers(registry);
	}
	
	@Bean
    public ViewResolver urlBasedViewResolver() {
        UrlBasedViewResolver viewResolver;
        viewResolver = new UrlBasedViewResolver();
        viewResolver.setOrder(2);
        viewResolver.setPrefix("/WEB-INF/dynamic/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setCache(false);
        return viewResolver;
    }
	/**
	 * 配置json返回值得编码。
	 * <mvc:annotation-driven /> 会自动注册DefaultAnnotationHandlerMapping与AnnotationMethodHandlerAdapter 
	 * 然而没有设置他们的编码 
	 * xml 配置参照：
	 * <annotation-driven>
	        <message-converters register-defaults="true">
	            <!-- @ResponseBody乱码问题，将StringHttpMessageConverter的默认编码设为UTF-8 -->
	            <beans:bean class="org.springframework.http.converter.StringHttpMessageConverter">
	                <beans:constructor-arg value="UTF-8"/>
	            </beans:bean>
	            <!-- 配置Fastjson支持 -->
	            <beans:bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter">
	                <beans:property name="charset" value="UTF-8"/>
	                <beans:property name="supportedMediaTypes">
	                    <beans:list>
	                        <beans:value>application/json</beans:value>
	                        <beans:value>text/html;charset=UTF-8</beans:value>
	                    </beans:list>
	                </beans:property>
	                <beans:property name="features">
	                    <beans:list>
	                        <beans:value>WriteMapNullValue</beans:value>
	                        <beans:value>QuoteFieldNames</beans:value>
	                        <beans:value>WriteDateUseDateFormat</beans:value>
	                        <beans:value>WriteEnumUsingToString</beans:value>
	                    </beans:list>
	                </beans:property>
	            </beans:bean>
	        </message-converters>
	    </annotation-driven>
	    
	 * 会添加默认 
	 *  ByteArrayHttpMessageConverter 
		StringHttpMessageConverter 
		ResourceHttpMessageConverter 
		SourceHttpMessageConverter 
		XmlAwareFormHttpMessageConverter 
		Jaxb2RootElementHttpMessageConverter 
		MappingJacksonHttpMessageConverter 
		转换器
	 * @param converters
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("utf-8"));
		stringHttpMessageConverter.setWriteAcceptCharset(true);
		converters.add(stringHttpMessageConverter);
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		converters.add(mappingJackson2HttpMessageConverter);
		//TODO  默认有7中，。新的是什么
		super.configureMessageConverters(converters);
	}
}
