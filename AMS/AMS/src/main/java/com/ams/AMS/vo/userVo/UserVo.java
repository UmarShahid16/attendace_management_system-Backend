package com.ams.AMS.vo.userVo;

import com.ams.AMS.entities.User.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserVo {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int phone;
    private String address;
    private Long departmentId;
    private String departmentName;
    private Long roleId;
    private String roleName;
    private String designation;
    private Date dateOfJoining;
    private String imageName;
    private String imageUrl;
    private String imageStr;
    private Boolean isActive;

    public static UserVo setResponse(User user) {
        UserVo vo = new UserVo();
        vo.setId(user.getId());
        vo.setFirstName(user.getFirstName());
        vo.setLastName(user.getLastName());
        vo.setEmail(user.getEmail());
        vo.setPhone(user.getPhone());
        vo.setAddress(user.getAddress());
        vo.setDepartmentId(user.getDepartment() != null ? user.getDepartment().getId() : null);
        vo.setDepartmentName(user.getDepartment() != null ? user.getDepartment().getDepartmentName() : null);
        vo.setRoleId(user.getRoles() != null ? user.getRoles().getId() : null);
        vo.setRoleName(user.getRoles() != null ? user.getRoles().getRoleName() : null);
        vo.setDesignation(user.getDesignation());
        vo.setDateOfJoining(user.getDate_of_joining());
        vo.setImageName(user.getImageName());
        vo.setImageUrl(user.getImageUrl());
        vo.setIsActive(user.getIsActive());
        return vo;
    }
}

