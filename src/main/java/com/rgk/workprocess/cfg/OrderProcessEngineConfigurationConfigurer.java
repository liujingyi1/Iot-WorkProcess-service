package com.rgk.workprocess.cfg;

import lombok.extern.log4j.Log4j2;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class OrderProcessEngineConfigurationConfigurer implements ProcessEngineConfigurationConfigurer {
    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
        processEngineConfiguration.setActivityFontName("宋体");
        log.info("ShareniuProcessEngineConfigurationConfigurer配置的字体#############"+processEngineConfiguration.getActivityFontName());
    }
}
