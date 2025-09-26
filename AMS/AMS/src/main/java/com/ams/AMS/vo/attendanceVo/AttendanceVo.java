package com.ams.AMS.vo.attendanceVo;

import com.ams.AMS.entities.attendace.Attendance;
import com.ams.AMS.vo.baseVo.BaseVo;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Karachi")
    private Date createdAt;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private String status;
    private String workHours;
    private Double dailyWorkingHours;
    private String imageUrl;

    public static AttendanceVo setResponse(Attendance attendance) {
        AttendanceVo attendanceVo = new AttendanceVo();

        if (attendance != null) {
            attendanceVo.setId(attendance.getId());
            if (attendance.getUser() != null) {
                attendanceVo.setUserId(attendance.getUser().getId());
                attendanceVo.setUserName(attendance.getUser().getFirstName());
            }
            attendanceVo.setCheckInTime(attendance.getCheck_in_time());
            if(attendance.getCheck_out_time() != null){
                attendanceVo.setCheckOutTime(attendance.getCheck_out_time());
            }
            if(attendance.getDailyWorkingHours() != null){
                String formatted = formatHours(attendance.getDailyWorkingHours());
                attendanceVo.setWorkHours(formatted);
                attendanceVo.setDailyWorkingHours(attendance.getDailyWorkingHours());
            }
            if(attendance.getCreatedAt() != null){
                attendanceVo.setCreatedAt(attendance.getCreatedAt());
            }
            attendanceVo.setStatus(attendance.getStatus());
        }
        return attendanceVo;
    }

    public static String formatHours(double hours) {
        long totalSeconds = (long) (hours * 3600); // convert hours → seconds
        long h = totalSeconds / 3600;
        long m = (totalSeconds % 3600) / 60;
        long s = totalSeconds % 60;
        return String.format("%dh %dm %ds", h, m, s);
    }


}
