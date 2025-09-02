package com.ams.AMS.entities.leave;

import com.ams.AMS.entities.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "leaves")
@Data
public class Leaves extends BaseEntity {

    private String leaveType;

    private String description;

    private Long maxDays;
}
