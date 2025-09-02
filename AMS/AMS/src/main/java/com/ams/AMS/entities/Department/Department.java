package com.ams.AMS.entities.Department;

import com.ams.AMS.entities.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "department")
@Data
public class Department extends BaseEntity {

    private String departmentName;

    private String description;
}
