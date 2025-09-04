package com.ams.AMS.controller.leave;

import com.ams.AMS.entities.leave.LeavesLog;
import com.ams.AMS.services.leave.LeaveLogService;
import com.ams.AMS.util.response.Response;
import com.ams.AMS.vo.leavesVo.LeavesLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leaveLog")
public class LeaveLogController {

    @Autowired
    private LeaveLogService leaveLogService;

    @PostMapping("/create")
    public ResponseEntity<?> createLeaveLog(LeavesLogVo leavesLogVo){
        Response response = leaveLogService.createLeaveLog(leavesLogVo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/remainingLeaves/{userId}")
    public ResponseEntity<?> leaveBalance(@PathVariable Long userId) {
        Response response = leaveLogService.leaveBalance(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
