package com.zhb.lts.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.github.ltsopensource.core.commons.utils.StringUtils;
import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;

/**
 * 统一调度运行JobRunner（遵循一个jvm里只运行一个TaskTracker实例，避免浪费资源）,要求：<br>
 * 1、所有JobRunner必须实现JobRunnerType接口，提供任务名称getTypeName()<br>
 * 2、所有job cliend提交的任务，必须传递参数JOB_RUNNER，路由到对应的JobRunner进行处理<br>
 * 3、所有JobRunner放在com.zhb.lts.tasktracker包下
 */
public class JobRunnerDispatcher implements JobRunner {
	private static final ConcurrentHashMap<String, JobRunnerType> JOB_RUNNER_MAP = new ConcurrentHashMap<String, JobRunnerType>();

	static {
		List<String> packs = new ArrayList<String>();
		packs.add("com.zhb.lts.tasktracker");
		List<Class<? extends JobRunnerType>> jobRunnerList = com.jfinal.ext.kit.ClassSearcher.of(JobRunnerType.class).scanPackages(packs).search();
		for (Class<? extends JobRunnerType> classTmp : jobRunnerList) {
			try {
				JobRunnerType jobRunner = classTmp.newInstance();
				JOB_RUNNER_MAP.put(jobRunner.getTypeName(),jobRunner);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Result run(JobContext jobContext) throws Throwable {
		Job job = jobContext.getJob();
		String type = job.getParam(LtsConstant.JOB_RUNNER_PARAM_KEY);
		if(StringUtils.isEmpty(type)){
			return new Result(Action.EXECUTE_FAILED,"不支持此类型的任务！（JOB_RUNNER参数为空）");
		}
		JobRunnerType jobRunner = JOB_RUNNER_MAP.get(type);
		if(jobRunner==null){
			return new Result(Action.EXECUTE_FAILED,"不支持此类型的任务！（JOB_RUNNER参数="+type+"）");
		}
		return jobRunner.run(jobContext);
	}
	
}
