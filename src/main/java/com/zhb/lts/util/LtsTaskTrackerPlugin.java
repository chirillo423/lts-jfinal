package com.zhb.lts.util;

import com.github.ltsopensource.tasktracker.TaskTracker;
import com.github.ltsopensource.tasktracker.TaskTrackerBuilder;
import com.jfinal.plugin.IPlugin;

public class LtsTaskTrackerPlugin implements IPlugin{
	
	private String conf = "config.properties";
	
	private static TaskTracker taskTracker = null;
	
	public static TaskTracker getTaskTracker() {
		return taskTracker;
	}

	public LtsTaskTrackerPlugin() {
		super();
	}

	public LtsTaskTrackerPlugin(String conf) {
		super();
		this.conf = conf;
	}
	
	@Override
	public boolean start() {
		taskTracker = new TaskTrackerBuilder().setPropertiesConfigure(conf).build();
		taskTracker.start();
		return true;
	}

	@Override
	public boolean stop() {
		taskTracker.stop();
		return true;
	}

}
