package com.rgk.workprocess.remote;

import com.rgk.workprocess.domain.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("rgkCloud-IoT-lock")
public interface PositionService {

    @GetMapping("/building/findByVillage")
    public ReturnObject findBuilding(@RequestParam(value = "villageId")String villageId);

}
