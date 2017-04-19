package com.zhb.lts.util;

import com.github.ltsopensource.tasktracker.runner.JobRunner;


public interface JobRunnerType extends JobRunner{

	String getTypeName();
}
