package com.osp.shiro.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.osp.core.ext.FileControlled;

public class KdEnableValidationCode implements FileControlled{

	@Override
	public String getEnableName() {
		return "osp.shiro.loginFilter.code";
	}

	@Override
	public Class<?> getConfigClass() {
		return null;
	}
	
	@Override
	public void initWebContain(ServletContext context) {
		ServletRegistration.Dynamic addServlet = context.addServlet("Kaptcha", KaptchaServlet.class);
//		<!-- 是否有边框 -->
		addServlet.setInitParameter("kaptcha.border", "no");
//		<!-- 使用哪些字体 -->
		addServlet.setInitParameter("kaptcha.textproducer.font.names", "Arial");
//		<!-- 图片宽度 -->
		addServlet.setInitParameter("kaptcha.image.width", "135");
//		<!-- 字体颜色 -->
		addServlet.setInitParameter("kaptcha.textproducer.font.color", "red");
//		<!-- 字符个数 -->
		addServlet.setInitParameter("kaptcha.textproducer.char.length", "4");
//		<!-- 干扰线的颜色 -->
		addServlet.setInitParameter("kaptcha.noise.color", "black");
//		<!-- 字体大小 -->
		addServlet.setInitParameter("kaptcha.textproducer.font.size", "43");
//		<!-- 图片高度 -->
		addServlet.setInitParameter("kaptcha.image.height", "50");
//		<!-- 使用哪些字符生成验证码 -->
		addServlet.setInitParameter("kaptcha.textproducer.char.string", "ACDEFHKPRSTWX345679");
		addServlet.addMapping("/static/Kaptcha.jpg");
	}

}
