package com.rgk.workprocess.service;

import com.rgk.workprocess.domain.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("rgkCloud-Iot-lock")
public interface TestService {

    @GetMapping("/room/findViewById")
    public ReturnObject findViewById(@RequestBody String id);
    
}
