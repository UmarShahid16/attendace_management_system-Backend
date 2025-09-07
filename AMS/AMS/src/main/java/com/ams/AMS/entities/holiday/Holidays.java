package com.ams.AMS.entities.holiday;

import com.ams.AMS.entities.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "holidays")
@Data
public class Holidays extends BaseEntity {

    private Date holidayDate;

    private String name;

    private String description;

    private Boolean isActive;
}
