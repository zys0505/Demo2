package com.osp.timer.config;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.osp.timer.JobManagerFace;

public class TimerConfig {
	
	@Bean(name = { "org.springframework.context.annotation.internalScheduledAnnotationProcessor" })
	public ScheduledAnnotationBeanPostProcessor scheduledAnnotationProcessor() {
		ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor = new ScheduledAnnotationBeanPostProcessor();
		return scheduledAnnotationBeanPostProcessor;
	}
	
	@Bean
	public SchedulerFactoryBean createSchedule(){
		return new org.springframework.scheduling.quartz.SchedulerFactoryBean();
	}
	
	@Bean
	public JobManagerFace createJobManagerFace(Scheduler sch){
		JobManagerFace jobManagerFace = new JobManagerFace();	
		jobManagerFace.setSched(sch);
		return jobManagerFace;
	}
}
