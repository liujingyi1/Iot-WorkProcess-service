package com.rgk.workprocess.service;

import com.rgk.workprocess.domain.JGridPage;
import com.rgk.workprocess.domain.ProcessDetailView;
import com.rgk.workprocess.domain.ReturnObject;
import com.rgk.workprocess.entity.MCustomOrder;

public interface ICustomerOrderService {

    public ReturnObject submitCustomerOrder(MCustomOrder customOrder);
    public ReturnObject delCustomerOrder(String id);
    public JGridPage<ProcessDetailView> ListByUserIdAndTaskKey(String userId, String taskKey, int page, Integer size, String sort, String properties);
    public JGridPage<ProcessDetailView> ListAll(int page, Integer size, String sort, String properties);
    public JGridPage<ProcessDetailView> ListByState(Integer state, Integer page, Integer rows, String sord, String sidx);
}
