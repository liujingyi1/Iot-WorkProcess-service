package com.rgk.workprocess.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Table(name = "aca_process_type")
@EntityListeners(value = {AuditingEntityListener.class})
public class MProcessType {

//    tiger in act_re_procdef
//    -----
//    begin
//    insert into aca_process_type (pro_id,pro_key,pro_name,pro_ver) values (new.ID_,new.KEY_,new.NAME_,new.VERSION_);
//    end
//    -----
//    begin
//    delete from aca_process_type where pro_id = old.ID_;
//    end

    @Id
    @GenericGenerator(name="uuidGenerator",strategy="uuid")
    @GeneratedValue(generator="uuidGenerator")
    @Column(length = 100,name = "id_")
    private String id;

    @Column(name = "pro_id")
    private String proId;

    @Column(name = "pro_key")
    private String proKey;

    @Column(name = "pro_name")
    private String proName;

    @Column(name = "pro_ver")
    private Integer version;
}
