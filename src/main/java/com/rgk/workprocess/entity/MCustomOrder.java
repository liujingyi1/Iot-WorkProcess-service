package com.rgk.workprocess.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "aca_custom_order")
@EntityListeners(AuditingEntityListener.class)
public class MCustomOrder {

    @Id
    @GenericGenerator(name="uuidGenerator",strategy="uuid")
    @GeneratedValue(generator="uuidGenerator")
    @Column(length = 100,name = "ID_")
    private String id;

    @Column(name = "USER_ID_",length = 64, nullable = false)
    private String userId;

    @Column(name = "CUSTOMER_NAME_")
    private String customerName;

    @Column(name = "CUSTOMER_PHONE_")
    private String customerPhone;

    @Column(name = "ORDER_TITLE_")
    private String orderTitle;

    @Column(name = "ORDER_SUMMARY_")
    private String orderSummary;

    @Column(name = "PROCESS_TYPE_")
    private String processType;

    @Column(name = "POSITION_ID_",length = 64)
    private String positionID;

    @Column(name = "PIC_IDS_")
    private String picIDs;

    @Column(name = "BUSINESS_KEY_")
    private String businessKey;

    @Column(name = "PROCESS_INSTANCE_ID_")
    private String processInstanceId;

    @Column(name = "STATE_")
    private Integer state = 0;

    @Column(name = "NUMBER_")
    private String number;

    @Column(name = "PRIORITY_")
    private Integer priority = 0;

    @Column(name = "TASK_ID_")
    private String taskId;
    
    @Column(name = "HANDLE_PERSON_")
    private String handlePerson;

    @CreatedDate
    public Date createdDate;

    @LastModifiedDate
    public Date modifiedDate;

    @LastModifiedBy
    public String modifiedBy;
}
