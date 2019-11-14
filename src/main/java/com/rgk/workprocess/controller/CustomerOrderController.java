package com.rgk.workprocess.controller;

import com.rgk.workprocess.domain.JGridPage;
import com.rgk.workprocess.domain.ProcessDetailView;
import com.rgk.workprocess.domain.ReturnObject;
import com.rgk.workprocess.entity.MCustomOrder;
import com.rgk.workprocess.service.ICustomerOrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/order")
public class CustomerOrderController {

    @Autowired
    ICustomerOrderService customerOrderService;

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ReturnObject submit(@RequestBody MCustomOrder customOrder) {
        return customerOrderService.submitCustomerOrder(customOrder);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ReturnObject update(@RequestBody MCustomOrder customOrder) {
        return customerOrderService.submitCustomerOrder(customOrder);
    }

    @RequestMapping("/listByTask")
    public JGridPage<MCustomOrder> findByTask(
            @RequestParam(value = "taskKey") Integer taskKey,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sidx", required = false, defaultValue = "createdDate") String sidx ,
            @RequestParam(value = "sord", defaultValue = "DESC") String sord) {
//        return customerOrderService.findByPage(page, rows, sord,sidx);
        return null;
    }


    @RequestMapping("/listAll")
    public JGridPage<ProcessDetailView> listAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sidx", required = false, defaultValue = "createdDate") String sidx ,
            @RequestParam(value = "sord", defaultValue = "DESC") String sord) {
        return customerOrderService.ListAll(page, rows, sord, sidx);
    }

    @RequestMapping("/listByState")
    public JGridPage<ProcessDetailView> listAccept(
            @RequestParam(value = "state") Integer state,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sidx", required = false, defaultValue = "createdDate") String sidx ,
            @RequestParam(value = "sord", defaultValue = "DESC") String sord) {

        log.info("state = {}", state);

        return customerOrderService.ListByState(state, page, rows, sord, sidx);
    }


}
