package com.osp.timer.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.MutableTrigger;

import com.osp.timer.MethodJob;

public class JobManager {
	
	private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

	/**
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * @param sched 中心调度器，建议从spring中获取
	 * @param triggerName 触发器名称
	 * @param jobName 任务名称
	 * @param jobClass job类
	 * @param time 时间设置，参考quartz说明文档
	 * @param targetProperty 属性集合存入jobDataMap，可以在job中取出
	 */
	public static void addJob(Scheduler sched, String triggerName, String jobName, Class<? extends Job> jobClass,
			String time, Map<String, Object> targetProperty) {
		try {
			// 任务名，任务组，任务执行类
			JobBuilder newJob = JobBuilder.newJob(jobClass);
			JobDetail jobDetail = newJob.build();
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			jobDataMap.put(MethodJob.PROPERTY_NAME, targetProperty);
			// 触发器
			CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(time);
			MutableTrigger trigger = cronSchedule.build();
			TriggerKey paramTriggerKey = new TriggerKey(triggerName);
			trigger.setKey(paramTriggerKey);
			sched.scheduleJob(jobDetail, trigger);
			// 启动
			if (sched.isShutdown()) {
				sched.start();
			} else {
				sched.rescheduleJob(paramTriggerKey, trigger);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 添加方法
	 * @param sched 中心调度器，建议从spring中获取
	 * @param triggerName 触发器名称
	 * @param jobName 任务名称
	 * @param time  时间设置，参考quartz说明文档
	 * @param target 目标类
	 * @param targerMethodName 目标方法
	 * @param targetProperty 目标类属性注入map(key对应属性名称)
	 */
	public static void addMehodJob(Scheduler sched, String triggerName, String jobName, String time, Class<?> target,
			String targerMethodName, Map<String, Object> targetProperty) {
		try {
			// 任务名，任务组，任务执行类
			JobBuilder newJob = JobBuilder.newJob(MethodJob.class);
			JobDetail jobDetail = newJob.build();
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			jobDataMap.put(MethodJob.CLASS_NAME, target);
			jobDataMap.put(MethodJob.METHOD_NAME, targerMethodName);
			jobDataMap.put(MethodJob.PROPERTY_NAME, targetProperty);
			// 触发器
			CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(time);
			MutableTrigger trigger = cronSchedule.build();
			TriggerKey paramTriggerKey = new TriggerKey(triggerName);
			trigger.setKey(paramTriggerKey);
			sched.scheduleJob(jobDetail, trigger);
			// 启动
			if (sched.isShutdown()) {
				sched.start();
			} else {
				sched.rescheduleJob(paramTriggerKey, trigger);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * @param sched sched 中心调度器，建议从spring中获取
	 * @param triggerName 触发器名称
	 * @param time 时间设置，参考quartz说明文档
	 */
	public static void modifyTriggerTime(Scheduler sched, String triggerName, String time) {
		try {
			TriggerKey triggerKey = new TriggerKey(triggerName);
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(time)
						.withMisfireHandlingInstructionDoNothing();
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronSchedule).build();
			}
			// 启动
			if (sched.isShutdown()) {
				sched.start();
			} else {
				sched.rescheduleJob(triggerKey, trigger);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * @param sched 中心调度器，建议从spring中获取
	 * @param jobName 任务名称
	 */
	public static void removeJob(Scheduler sched, String jobName) {
		try {
			TriggerKey triggerKey = new TriggerKey(TRIGGER_GROUP_NAME);
			sched.pauseTrigger(triggerKey);
			sched.unscheduleJob(triggerKey);
			sched.deleteJob(new JobKey(jobName));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 移除一个触发器
	 * @param sched 中心调度器，建议从spring中获取
	 * @param triggerName 触发器名称
	 */
	public static void removeTrigger(Scheduler sched, String triggerName) {
		try {
			TriggerKey triggerKey = new TriggerKey(triggerName);
			sched.pauseTrigger(triggerKey);
			sched.unscheduleJob(triggerKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description:启动所有定时任务
	 * 
	 * @param sched
	 *            调度器
	 * 
	 * @Title: QuartzManager.java
	 */
	public static void startJobs(Scheduler sched) {
		try {
			sched.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description:关闭所有定时任务
	 * 
	 * 
	 * @param sched
	 *            调度器
	 * 
	 * 
	 * @Title: QuartzManager.java
	 */
	public static void shutdownJobs(Scheduler sched) {
		try {
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 展示所有触发器
	 * @param sched 中心调度
	 * @throws SchedulerException
	 */
	public static List<Trigger> listTrigger(Scheduler sched) throws SchedulerException {
		List<Trigger> retVal = new ArrayList<>();
		List<String> triggerGroupNames = sched.getTriggerGroupNames();
		for (String triggerGroupName : triggerGroupNames) {
			GroupMatcher<TriggerKey> triggerGroupEquals = GroupMatcher.triggerGroupEquals(triggerGroupName);
			Set<TriggerKey> triggerKeys = sched.getTriggerKeys(triggerGroupEquals);
			for (TriggerKey triggerKey : triggerKeys) {
				Trigger trigger = sched.getTrigger(triggerKey);
				retVal.add(trigger);
				System.out.println(trigger);
			}
		}
		return retVal;
	}

	/**
	 * 展示所有job
	 * @param sched 中心调度
	 * @throws SchedulerException
	 */
	public static List<JobDetail> listJobs(Scheduler sched) throws SchedulerException {
		List<JobDetail> retVal = new ArrayList<>();
		List<String> triggerGroupNames = sched.getJobGroupNames();
		for (String triggerGroupName : triggerGroupNames) {
			GroupMatcher<JobKey> jobGroupEquals = GroupMatcher.jobGroupEquals(triggerGroupName);
			Set<JobKey> jobKeys = sched.getJobKeys(jobGroupEquals);
			for (JobKey jobKey : jobKeys) {
				JobDetail jobDetail = sched.getJobDetail(jobKey);
				retVal.add(jobDetail);
				System.out.println(jobDetail);
			}
		}
		return retVal;
	}
}
