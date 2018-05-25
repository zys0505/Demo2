package com.osp.timer;

import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import com.osp.timer.config.JobManager;

public class JobManagerFace {
	
	private Scheduler sched;
	/**
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * @param triggerName 触发器名称
	 * @param jobName 任务名称
	 * @param jobClass job类
	 * @param time 时间设置，参考quartz说明文档
	 * @param targetProperty 属性集合存入jobDataMap，可以在job中取出
	 */
	public void addJob(String triggerName, String jobName, Class<? extends Job> jobClass,
			String time, Map<String, Object> targetProperty) {
		JobManager.addJob(sched, triggerName, jobName, jobClass, time, targetProperty);
	}
	/**
	 * 添加方法
	 * @param triggerName 触发器名称
	 * @param jobName 任务名称
	 * @param time  时间设置，参考quartz说明文档
	 * @param target 目标类
	 * @param targerMethodName 目标方法
	 * @param targetProperty 目标类属性注入map(key对应属性名称)
	 */
	public void addMehodJob(String triggerName, String jobName, String time, Class<?> target,
			String targerMethodName, Map<String, Object> targetProperty) {
		JobManager.addMehodJob(sched, triggerName, jobName, time, target, targerMethodName, targetProperty);
	}

	/**
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * @param triggerName 触发器名称
	 * @param time 时间设置，参考quartz说明文档
	 */
	public void modifyTriggerTime(String triggerName, String time) {
		JobManager.modifyTriggerTime(sched, triggerName, time);
	}

	/**
	 * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * @param jobName 任务名称
	 */
	public void removeJob(String jobName) {
		JobManager.removeJob(sched, jobName);
	}

	/**
	 * 移除一个触发器
	 * @param triggerName 触发器名称
	 */
	public void removeTrigger(String triggerName) {
		JobManager.removeTrigger(sched, triggerName);
	}

	/**
	 * @Description:启动所有定时任务
	 * 
	 * @param sched
	 *            调度器
	 * 
	 * @Title: QuartzManager.java
	 */
	public  void startJobs() {
		JobManager.startJobs(sched);
	}

	/**
	 * @Description:关闭所有定时任务
	 * @Title: QuartzManager.java
	 */
	public void shutdownJobs() {
		JobManager.shutdownJobs(sched);
	}

	/**
	 * 展示所有触发器
	 * @throws SchedulerException
	 */
	public List<Trigger> listTrigger() throws SchedulerException {
		System.out.println("中心控制器："+sched);
		List<Trigger> listTrigger = JobManager.listTrigger(sched);
		return listTrigger;
	}

	/**
	 * 展示所有job
	 * @throws SchedulerException
	 */
	public List<JobDetail> listJobs() throws SchedulerException {
		List<JobDetail> listJobs = JobManager.listJobs(sched);
		return listJobs;
	}
	
	
	public Scheduler getSched() {
		return sched;
	}
	public void setSched(Scheduler sched) {
		this.sched = sched;
	}
	
}
