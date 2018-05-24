package com.osp.shiro.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.osp.common.util.PropertiesUtil;

public class ShiroCilentCondition implements  Condition{

	@Override
	public boolean matches(ConditionContext arg0, AnnotatedTypeMetadata arg1) {
		String value = PropertiesUtil.getValue("osp.shiro.islocal");
		if("1".equals(value)){
			return false;
		}
		return true;
	}

}
