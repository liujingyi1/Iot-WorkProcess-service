package com.rgk.workprocess.utils;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtils implements EnvironmentAware {

	private Environment enviroment;
	@Override
	public void setEnvironment(Environment environment) {
		this.enviroment = environment;
	}
	
	public String getActiveProfile() {
		if (enviroment.getActiveProfiles().length > 0) {
			return enviroment.getActiveProfiles()[0];
		}
		return enviroment.getDefaultProfiles()[0];
	}

}
