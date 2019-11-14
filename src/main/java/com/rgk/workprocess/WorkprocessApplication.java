package com.rgk.workprocess;

import com.rgk.workprocess.utils.ApplicationContextUtils;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients
public class WorkprocessApplication {

    public static void main(String[] args) {
        
        ConfigurableApplicationContext run = SpringApplication.run(WorkprocessApplication.class, args);
        ApplicationContextUtils.setContext(run);
    }

}
