package com.rgk.workprocess.respository;

import com.rgk.workprocess.entity.MProcessState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProcessStateDao extends JpaRepository<MProcessState, String> {
    MProcessState findByTaskId(String taskId);
}
