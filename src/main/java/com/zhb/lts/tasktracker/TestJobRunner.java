package com.zhb.lts.tasktracker;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.logger.Logger;
import com.github.ltsopensource.core.logger.LoggerFactory;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.zhb.lts.util.JobRunnerType;
import com.zhb.lts.util.LtsConstant;

public class TestJobRunner implements JobRunnerType {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TestJobRunner.class);

	@Override
	public Result run(JobContext jobContext) throws Throwable {
		System.out.println("1111111111111111111111111111");
		System.out.println("1111111111111111111111111111");
		System.out.println("1111111111111111111111111111");
		System.out.println("1111111111111111111111111111");
		System.out.println("1111111111111111111111111111");
		System.out.println("1111111111111111111111111111");
		
		return new Result(Action.EXECUTE_SUCCESS, getTypeName() + "执行成功。");
	}

	@Override
	public String getTypeName() {
		return LtsConstant.JobRunnerTypeName.test;
	}

}
