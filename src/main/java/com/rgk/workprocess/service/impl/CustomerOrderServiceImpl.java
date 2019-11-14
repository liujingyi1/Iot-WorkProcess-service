package com.rgk.workprocess.service.impl;

import com.rgk.workprocess.domain.JGridPage;
import com.rgk.workprocess.domain.ProcessDetailView;
import com.rgk.workprocess.domain.ReturnObject;
import com.rgk.workprocess.entity.MCustomOrder;
import com.rgk.workprocess.respository.ICustomerOrderDao;
import com.rgk.workprocess.service.ICustomerOrderService;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Log4j2
@Service
public class CustomerOrderServiceImpl implements ICustomerOrderService {

    @Autowired
    ICustomerOrderDao customerOrderDao;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    TaskService taskService;

    @Transactional
    @Override
    public ReturnObject submitCustomerOrder(MCustomOrder customOrder) {
        ReturnObject returnObject = new ReturnObject(0, null, "fail");
        if (customOrder != null) {
            customerOrderDao.save(customOrder);
            if (StringUtils.isNoneBlank(customOrder.getId())) {
                returnObject = new ReturnObject()
                        .setResult(customOrder)
                        .setCode(1)
                        .setMessage("success");
            }
        }
        return returnObject;
    }

    @Transactional
    @Override
    public ReturnObject delCustomerOrder(String id) {
        customerOrderDao.deleteById(id);
        return new ReturnObject();
    }

    @Transactional(readOnly=true)
    @Override
    public JGridPage<ProcessDetailView> ListByUserIdAndTaskKey(String userId, String taskKey, int page, Integer size, String sord, String properties) {
        JGridPage<MCustomOrder> jGridPage = new JGridPage<MCustomOrder>();

        List<Task> tasks = taskService.createTaskQuery()
                .taskCandidateOrAssigned(userId)
                .taskDefinitionKey(taskKey)
                .listPage((page - 1) * size, page * size);




//        List<ProcessDetailView> processDetailView = customerOrderDao.findProcessDetailView();

        List<MCustomOrder> all = customerOrderDao.findByStateLessThan(4);
//        List<MCustomOrder> all = customerOrderDao.findAll();
        jGridPage.setPage(page);
        jGridPage.setTotal(all.size()/size);
        jGridPage.setRecords(all.size());
        jGridPage.setRows(all);
        return null;
    }

    @Override
    public JGridPage<ProcessDetailView> ListAll(int page, Integer size, String sort, String properties) {

        JGridPage<ProcessDetailView> jGridPage = new JGridPage<ProcessDetailView>();

        List<ProcessDetailView> processDetailView = customerOrderDao.findProcessDetailView();

        jGridPage.setPage(page);
        jGridPage.setTotal(processDetailView.size()/size+1);
        jGridPage.setRecords(processDetailView.size());
        jGridPage.setRows(processDetailView.subList((page-1)*size, Math.min(size*page, processDetailView.size())));
        return jGridPage;
    }

    @Override
    public JGridPage<ProcessDetailView> ListByState(Integer state, Integer page, Integer rows, String sord, String sidx) {

        JGridPage<ProcessDetailView> jGridPage = new JGridPage<ProcessDetailView>();

        List<ProcessDetailView> processDetailView = customerOrderDao.findProcessDetailViewByState(state);

        jGridPage.setPage(page);
        jGridPage.setTotal(processDetailView.size()/rows+1);
        jGridPage.setRecords(processDetailView.size());
        jGridPage.setRows(processDetailView.subList((page-1)*rows, Math.min(rows*page, processDetailView.size())));
        return jGridPage;
    }
}
