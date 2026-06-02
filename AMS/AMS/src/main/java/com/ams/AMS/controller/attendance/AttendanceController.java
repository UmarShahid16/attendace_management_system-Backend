package com.ams.AMS.controller.attendance;

import com.ams.AMS.services.attendance.AttendanceService;
import com.ams.AMS.util.response.Response;
import com.ams.AMS.vo.attendanceVo.AttendanceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    public AttendanceService attendanceService;

    @PostMapping("/checkIn")
    public ResponseEntity<?> markAttendance(@RequestBody AttendanceVo attendanceVo){
        Response response = attendanceService.markAttendance(attendanceVo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/checkOut")
    public ResponseEntity<?> markCheckOut(@RequestParam("userId") Long userId, LocalDate date){
        Response response = attendanceService.markCheckOut(userId, date);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/mark")
    public ResponseEntity<?> mark(@RequestBody AttendanceVo attendanceVo){
        Response response = attendanceService.markAttendance1(attendanceVo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/monthlyAttendanceByUserId")
    public ResponseEntity<?> monthlyAttendanceByUserId(@RequestParam("userId") Long userId, @RequestParam("month") Integer month, @RequestParam("year") Integer year){
        Response response = attendanceService.monthlyAttendanceByUserId(userId, month, year);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/dailyAttendance")
    public ResponseEntity<?> dailyAttendance(@RequestParam("date") String date){
        Response response = attendanceService.dailyAttendance(date);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> allAttendance(@RequestParam(value = "pageNo", required = false) Long pageNo, @RequestParam(value = "pageSize", required = false) Long pageSize){
        Response response = attendanceService.allAttendance(pageNo, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/monthlyReport")
    public ResponseEntity<?> monthlyReportAllUsers(@RequestParam(value = "month") Integer month, @RequestParam(value = "year") Integer year, @RequestParam(value = "pageNo", required = false) Long pageNo, @RequestParam(value = "pageSize", required = false) Long pageSize){
        Response response = attendanceService.monthlyReportAllUsers(month, year, pageNo,pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/manualMarkAttendance")
    public ResponseEntity<?> manualMarkAttendance(@RequestBody AttendanceVo attendanceVo){
        Response response = attendanceService.manualMarkAttendance(attendanceVo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/dailyAttendanceCounts")
    public ResponseEntity<?> dailyAttendanceCounts(){
        Response response = attendanceService.dailyAttendanceCount();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
