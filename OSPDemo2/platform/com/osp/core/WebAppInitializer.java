package com.osp.core;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.osp.core.container.mvc.MvcConfig;
import com.osp.core.container.spring.SpringConfig;
import com.osp.core.ext.LoadExt;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {  
	private static final Class<?> SPRING_MVC = MvcConfig.class;
	private static final Class<?> SPRING = SpringConfig.class;
    /** 
     * 配置ContextLoaderListener 
     */  
    @Override  
    protected Class<?>[] getRootConfigClasses() {  
    	Class<?>[] retVal = new Class<?>[]{SPRING}; 
    	retVal = loadExtConfigClass(retVal);
        return retVal;
    }  
  
    /** 
     * 配置DispatcherServlet 
     */  
    @Override  
    protected Class<?>[] getServletConfigClasses() {  
        return new Class<?>[]{SPRING_MVC};  
    }  
  
    /** 
     * 配置ServletMappings 
     */  
    @Override  
    protected String[] getServletMappings() {  
        return new String [] {"/"};  
    }  
  
    private Class<?>[] loadExtConfigClass(Class<?>[] par){
    	Class<?>[] extClass = LoadExt.findExtConfigClass();
    	return (Class<?>[]) ArrayUtils.addAll(par, extClass);
    }
}  