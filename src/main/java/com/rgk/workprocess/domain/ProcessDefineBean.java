package com.rgk.workprocess.domain;

import lombok.Data;

@Data
public class ProcessDefineBean {
    String id;
    String name;
    String deploymentTime;
    String category;
    String url;
    String tenantId;
    boolean suspended;
    int runProcessCount;
}
