package com.rgk.workprocess.remote;

import com.rgk.workprocess.domain.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("rgkCloud-Iot-admin")
public interface UserService {
    
    @GetMapping("/role/users/{id}")
    public ReturnObject findUsers(@PathVariable String id);

    @GetMapping("/role/all")
    public ReturnObject findRoleAll(@RequestParam(value = "villageId")String villageId);

}
