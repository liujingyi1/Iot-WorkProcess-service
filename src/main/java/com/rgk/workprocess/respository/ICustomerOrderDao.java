package com.rgk.workprocess.respository;

import com.rgk.workprocess.domain.ProcessDetailView;
import com.rgk.workprocess.entity.MCustomOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import java.util.List;

public interface ICustomerOrderDao extends JpaRepository<MCustomOrder, String> {

    List<MCustomOrder> findByBusinessKey(String businessKey);

    List<MCustomOrder> findByProcessInstanceId(String processInstanceId);

    List<MCustomOrder> findByStateLessThan(Integer state);

    @Query(value = "SELECT new com.rgk.workprocess.domain.ProcessDetailView(co,ps,pt) FROM MCustomOrder co,MProcessState ps,MProcessType pt WHERE co.state = ps.state AND co.processType = pt.id AND co.state < 4")
    public List<ProcessDetailView> findProcessDetailView();

    @Query(value = "SELECT new com.rgk.workprocess.domain.ProcessDetailView(co,ps,pt) FROM MCustomOrder co,MProcessState ps,MProcessType pt WHERE co.state = ps.state AND co.processType = pt.id AND co.id = :id")
    public ProcessDetailView findProcessDetailViewById(String id);

    @Query(value = "SELECT new com.rgk.workprocess.domain.ProcessDetailView(co,ps,pt) FROM MCustomOrder co,MProcessState ps,MProcessType pt WHERE co.state = ps.state AND co.processType = pt.id AND co.state = :state")
    public List<ProcessDetailView> findProcessDetailViewByState(Integer state);
    
}
