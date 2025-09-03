package com.ams.AMS.vo.attendanceVo;

import com.ams.AMS.entities.attendace.Attendance;
import com.ams.AMS.vo.baseVo.BaseVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttendanceVo extends BaseVo {
    private Long id;
    private Long userId;
    private String userName;
    private Date date;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private String status;
    private Double workHours;

    public static AttendanceVo setResponse(Attendance attendance) {
        AttendanceVo attendanceVo = new AttendanceVo();
        if (attendance != null) {
            attendanceVo.setId(attendance.getId());
            if (attendance.getUser() != null) {
                attendanceVo.setUserId(attendance.getUser().getId());
                attendanceVo.setUserName(attendance.getUser().getFirstName());
            }
            attendanceVo.setDate(attendance.getDate());
            attendanceVo.setCheckInTime(attendance.getCheck_in_time());
            attendanceVo.setCheckOutTime(attendance.getCheck_out_time());
            attendanceVo.setStatus(attendance.getStatus());
            attendanceVo.setWorkHours(attendance.getWorkHours());
        }
        return attendanceVo;
    }
}
