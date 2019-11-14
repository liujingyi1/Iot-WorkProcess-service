package com.rgk.workprocess.domain;

import lombok.Data;

import java.util.List;

@Data
public class TaskView {
    String taskKey;
    String taskId;
    List<String> formProperty;
}
