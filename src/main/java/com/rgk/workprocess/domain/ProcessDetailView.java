package com.rgk.workprocess.domain;

import com.rgk.workprocess.entity.MCustomOrder;
import com.rgk.workprocess.entity.MProcessState;
import com.rgk.workprocess.entity.MProcessType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDetailView implements Serializable {

    private MCustomOrder customOrder;

    private MProcessState processState;

    private MProcessType processType;
    
    private PositionView positionView;

    private TaskView taskView;

    public ProcessDetailView(MCustomOrder customOrder, MProcessState processState, MProcessType processType) {
        this.customOrder = customOrder;
        this.processState = processState;
        this.processType = processType;
    }
}
