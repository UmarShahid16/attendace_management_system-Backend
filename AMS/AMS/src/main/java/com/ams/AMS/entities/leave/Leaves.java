package com.ams.AMS.entities.leave;

import com.ams.AMS.entities.User.User;
import com.ams.AMS.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "leaves")
@Data
public class Leaves extends BaseEntity {

    private String leaveType;

    private String description;

    private Long maxDays;

    private String status;

    private Date startDate;

    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
