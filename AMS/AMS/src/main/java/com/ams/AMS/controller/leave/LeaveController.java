package com.ams.AMS.controller.leave;

import com.ams.AMS.services.leave.LeaveService;
import com.ams.AMS.util.response.Response;
import com.ams.AMS.vo.leavesVo.LeavesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @PostMapping("/apply")
    public ResponseEntity<?> applyLeave(@RequestBody LeavesVo leavesVo){
        Response response = leaveService.applyLeave(leavesVo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> leaveList(@RequestParam (required = false) Long pageNo, @RequestParam(required = false) Long pageSize){
        Response response = leaveService.leaveList(pageNo, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{leaveId}")
    public ResponseEntity<?> getLeaveById(@PathVariable Long leaveId) {
        Response response = leaveService.getLeaveById(leaveId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/byId")
    public ResponseEntity<?> leavesByUserId(@RequestParam Long byUserId, @RequestParam(required = false) Long pageNo, @RequestParam(required = false) Long pageSize) {
        Response response = leaveService.leavesByUserId(byUserId, pageNo, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateLeaveStatus(@RequestBody LeavesVo leavesVo){
        Response response = leaveService.updateLeaveStatus(leavesVo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<?> pendingLeaves(@RequestParam String status, @RequestParam(required = false) Long pageNo, @RequestParam(required = false) Long pageSize) {
        Response response = leaveService.pendingLeaves(status ,pageNo, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/byDate")
    public ResponseEntity<?> leavesByDate(@RequestParam String startDate, @RequestParam String endDate, @RequestParam(required = false) Long pageNo, @RequestParam(required = false) Long pageSize) {
        Response response = leaveService.leavesByDateRange(startDate, endDate, pageNo, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelLeave(@RequestBody LeavesVo leavesVo) {
        Response response = leaveService.cancelLeave(leavesVo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/leaveBalance")
    public ResponseEntity<?> leaveBalance(@RequestParam(required = false) Long pageNo, @RequestParam(required = false) Long pageSize) {
        Response response = leaveService.leaveBalance(pageNo, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
