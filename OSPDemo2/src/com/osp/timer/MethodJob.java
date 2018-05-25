package com.osp.timer;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class MethodJob implements Job{
	
	public static final String CLASS_NAME = "job_class_name";
	public static final String METHOD_NAME = "job_method_name";
	public static final String PROPERTY_NAME = "job_property_name";
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext paramJobExecutionContext) throws JobExecutionException {
		JobDataMap jobDataMap = paramJobExecutionContext.getJobDetail().getJobDataMap();
		Class<?> clazz = (Class<?>) jobDataMap.get(CLASS_NAME);
		String methodName = (String) jobDataMap.get(METHOD_NAME);
		Map<String,Object> property = (Map<String, Object>) jobDataMap.get(PROPERTY_NAME);
		try {
			Object newInstance = clazz.newInstance();
			for(Entry<String,Object> en:property.entrySet()){
				PropertyDescriptor propertyDescriptor;
				try {
					propertyDescriptor = new PropertyDescriptor(en.getKey(),clazz);
					Method writeMethod = propertyDescriptor.getWriteMethod();
					writeMethod.invoke(newInstance, en.getValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Method method = clazz.getMethod(methodName);
			method.invoke(newInstance);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println("timer execute over");
	}

}
