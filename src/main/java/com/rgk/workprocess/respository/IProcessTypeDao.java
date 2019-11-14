package com.rgk.workprocess.respository;

import com.rgk.workprocess.entity.MProcessType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProcessTypeDao extends JpaRepository<MProcessType, String> {

    List<MProcessType> findByProKey(String key);
    void  deleteByProKey(String key);
}
