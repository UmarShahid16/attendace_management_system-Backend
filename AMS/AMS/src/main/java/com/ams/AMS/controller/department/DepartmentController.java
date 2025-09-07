package com.ams.AMS.controller.department;

import com.ams.AMS.services.department.DepartmentService;
import com.ams.AMS.util.response.Response;
import com.ams.AMS.vo.departmentVo.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/add")
    public ResponseEntity<?> addDepartment(DepartmentVo departmentVo){
        Response response = departmentService.addDepartment(departmentVo);
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllDepartments(@RequestParam(required = false) Long pageNo, @RequestParam(required = false) Long pageSize) {
        Response response = departmentService.getAllDepartments(pageNo, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id){
        Response response = departmentService.getDepartmentById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
