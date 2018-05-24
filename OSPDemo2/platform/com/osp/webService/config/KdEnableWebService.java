package com.osp.webService.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import com.osp.core.ext.FileControlled;

public class KdEnableWebService implements FileControlled{

	@Override
	public String getEnableName() {
		return "osp.webService.startup";
	}

	@Override
	public Class<?> getConfigClass() {
		return WebserviceConfig.class;
	}
	
	@Override
	public void initWebContain(ServletContext context) {
		ServletRegistration.Dynamic addServlet = context.addServlet("CXFServlet", org.apache.cxf.transport.servlet.CXFServlet.class);
		addServlet.addMapping("/services/*");
	}

}
