package com.ams.AMS.entities.attendace;

import com.ams.AMS.entities.User.User;
import com.ams.AMS.entities.base.BaseEntity;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "attendance")
@Data
public class Attendance extends BaseEntity {

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @ToString.Exclude
    private User user;

    private Date date;

    private LocalTime check_in_time;

    private LocalTime check_out_time;

    private String status;

    private String workHours;

    private Double dailyWorkingHours;
}
