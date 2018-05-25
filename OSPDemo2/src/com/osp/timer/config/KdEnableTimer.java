package com.osp.timer.config;

import javax.servlet.ServletContext;

import com.osp.core.ext.FileControlled;

public class KdEnableTimer implements FileControlled{

	@Override
	public String getEnableName() {
		return "osp.timer.startup";
	}

	@Override
	public Class<?> getConfigClass() {
		return TimerConfig.class;
	}

	@Override
	public void initWebContain(ServletContext context) {
		
	}

}
