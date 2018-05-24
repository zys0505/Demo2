package com.osp.shiro.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import com.osp.core.ext.FileControlled;

public class KdEnableShiro implements FileControlled {

	@Override
	public String getEnableName() {
		return "osp.shiro.startup";
	}

	@Override
	public Class<?> getConfigClass() {
		return ShiroConfig.class;
	}

	@Override
	public void initWebContain(ServletContext context) {
		FilterRegistration.Dynamic addFilter = context.addFilter("shiroFilter", org.springframework.web.filter.DelegatingFilterProxy.class);
		addFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST,DispatcherType.FORWARD,DispatcherType.INCLUDE), true, "/*");
		addFilter.setInitParameter("targetFilterLifecycle", "true");
		addFilter.setInitParameter("targetBeanName", "shiroFilter");
	}

}
