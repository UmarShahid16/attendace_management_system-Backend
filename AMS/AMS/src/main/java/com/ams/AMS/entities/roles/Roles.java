package com.ams.AMS.entities.roles;

import com.ams.AMS.entities.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Roles  extends BaseEntity {
    private String roleName;
    private String description;
}
