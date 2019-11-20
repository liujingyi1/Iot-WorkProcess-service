package com.rgk.workprocess.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.activiti.engine.history.HistoricDetail;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDetailNode {
    String activityInstanceId;
    String taskId;
    String taskName;
    String identity;
//    List<HistoricDetail> detailList;
    String result;
    String message;
    Date date;
}
