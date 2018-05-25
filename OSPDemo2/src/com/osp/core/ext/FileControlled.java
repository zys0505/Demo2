package com.osp.core.ext;

import javax.servlet.ServletContext;

public interface FileControlled {
	/**
	 * 是否开启此配置，的application文件中的名称。
	 * @return
	 */
	String getEnableName();
	/**
	 * 配置类
	 * @return
	 */
	Class<?> getConfigClass();
	/**
	 * JavaConfig，Filter，Servlet，listener
	 * @param context
	 */
	void initWebContain(ServletContext context);
}
