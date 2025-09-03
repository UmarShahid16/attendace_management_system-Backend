package com.ams.AMS.controller.user;

import com.ams.AMS.vo.userVo.UserVo;
import com.ams.AMS.services.user.UserService;
import com.ams.AMS.util.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
