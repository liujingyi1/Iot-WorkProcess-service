package com.rgk.workprocess.domain;

import lombok.Data;

import java.util.List;

@Data
public class DeploymentListBean {

    List<DeploymentBean> data;
    //部署流程的数量
    int total;
    //正在运行的实例数
    int start;
}
