package com.ams.AMS.controller.user;

import com.ams.AMS.vo.userVo.UserVo;
import com.ams.AMS.services.user.UserService;
import com.ams.AMS.util.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody UserVo user) {
        Response response = userService.saveUser(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserVo userVo) {
        Response loginResponse = userService.login(userVo);
        return ResponseEntity.ok(loginResponse);
    }
    @GetMapping("/list")
    public ResponseEntity<?> getAllUsers( @RequestParam(required = false) Boolean isActive, @RequestParam(required = false) Long pageNo, @RequestParam(required = false) Long pageSize) {
        Response response = userService.getAllUsers(isActive, pageNo, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/byId/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Response response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employeeCounts")
    public ResponseEntity<?> getEmployeeCounts() {
        Response response = userService.getEmployeeCounts();
        return ResponseEntity.ok(response);
    }


}
