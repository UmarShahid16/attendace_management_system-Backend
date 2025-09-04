package com.ams.AMS.controller.role;

import com.ams.AMS.services.role.RoleService;
import com.ams.AMS.util.response.Response;
import com.ams.AMS.vo.rolesVo.RolesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity<?> addRole(@RequestBody RolesVo roleVo){
        Response response = roleService.addRole(roleVo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/byId/{id}")
    public ResponseEntity<?> getRoleById(@RequestBody Long id){
        Response response = roleService.getRoleById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllRoles(@RequestParam(required = false) Boolean isActive, @RequestParam(required = false) Long pageNo, @RequestParam(required = false) Long pageSize){
        Response response = roleService.getAllRoles(isActive, pageNo, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
