package com.ams.AMS.entities.attendace;

import com.ams.AMS.entities.User.User;
import com.ams.AMS.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "attendance")
@Data
public class Attendance extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private Date date;

    private LocalTime check_in_time;

    private LocalTime check_out_time;

    private String status;

    private Double workHours;
}
