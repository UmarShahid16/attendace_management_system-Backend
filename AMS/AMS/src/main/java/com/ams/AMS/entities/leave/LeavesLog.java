package com.ams.AMS.entities.leave;

import com.ams.AMS.entities.User.User;
import com.ams.AMS.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "leaves_log")
@Data
public class LeavesLog extends BaseEntity {

    private Long totalLeaves;
    private Long sickLeaves;
    private Long casualLeaves;
    private Long earnedLeaves;
    private Boolean isAllLeavesEncashed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
}
