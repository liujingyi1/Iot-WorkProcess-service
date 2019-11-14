package com.rgk.workprocess.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Table(name = "aca_process_state")
@EntityListeners(value = {AuditingEntityListener.class})
public class MProcessState {
    @Id
    @Column(length = 100,name = "id_")
    private Integer id;

    @Column(name = "state_")
    private String state;

    @Column(name = "content_")
    private String content;

    @Column(name = "task_id_")
    private String taskId;

}
