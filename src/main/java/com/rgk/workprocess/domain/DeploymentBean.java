package com.rgk.workprocess.domain;

import lombok.Data;

import java.util.Date;

@Data
public class DeploymentBean {
    String id;
    String name;
    Date deploymentTime;
    String category;
    String tenantId;
    boolean suspended;
    int runProcessCount;
}
