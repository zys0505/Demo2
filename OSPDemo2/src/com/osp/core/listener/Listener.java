package com.osp.core.listener;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.Log4jConfigListener;

import com.osp.core.container.mvc.MvcConfig;
import com.osp.core.container.spring.SpringConfig;
import com.osp.core.ext.FileControlled;
import com.osp.core.ext.LoadExt;

public class Listener implements WebApplicationInitializer {

	private static final Class<?> SPRING_MVC = MvcConfig.class;
	private static final Class<?> SPRING = SpringConfig.class;

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// initSpring(servletContext);
		initLog4j(servletContext);
		initEncodeFilter(servletContext);
		initExtContainer(servletContext);
	}

	private void initExtContainer(ServletContext servletContext){
		Class<FileControlled>[] extClass = LoadExt.getFileClass();
		for(Class<FileControlled> clazz : extClass){
			try {
				FileControlled newInstance = (FileControlled) clazz.newInstance();
				newInstance.initWebContain(servletContext);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initEncodeFilter(ServletContext servletContext) {
		FilterRegistration.Dynamic addFilter = servletContext.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
		addFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
		addFilter.setInitParameter("encoding", "UTF-8");
	}

	private void initLog4j(ServletContext servletContext) {
		servletContext.setInitParameter("log4jConfigLocation", "classpath:config/log4j.properties");
		servletContext.addListener(Log4jConfigListener.class);
	}

	@SuppressWarnings("unused")
	private void initSpring(ServletContext servletContext) {
		/*
		 * ContextLoaderListener is added to ServletContext – the purpose of
		 * this is to 'glue' WebApplicationContext to the lifecycle of
		 * ServletContext
		 */
		WebApplicationContext context = getContext();
		servletContext.addListener(new ContextLoaderListener(context));
		/*
		 * DispatcherServlet is created and initialized with
		 * WebApplicationContext we have created, and it's mapped to "/*" URLs
		 * and set to eagerly load on application startup.
		 */
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet",
				new DispatcherServlet(context));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}

	private AnnotationConfigWebApplicationContext getContext() {
		/*
		 * AnnotationConfigWebApplicationContext is created. It's
		 * WebApplicationContext implementation that looks for Spring
		 * configuration in classes annotated with @Configuration annotation.
		 * setConfigLocation() method gets hint in which package(s) to look for
		 * them.
		 */
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(new Class[] { SPRING, SPRING_MVC });
		return context;
	}
}
