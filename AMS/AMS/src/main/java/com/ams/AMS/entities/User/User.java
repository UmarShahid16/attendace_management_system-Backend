package com.ams.AMS.entities.User;

import com.ams.AMS.entities.Department.Department;
import com.ams.AMS.entities.attendace.Attendance;
import com.ams.AMS.entities.base.BaseEntity;
import com.ams.AMS.entities.roles.Roles;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User extends BaseEntity {

    private String firstName;

    private String lastName;

    private String email;

    private int phone;

    private String address;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Roles roles;

    private String designation;

    private Date date_of_joining;

    private String imageName;

    private String imageUrl;

    @Transient
    private String imageStr;

    private Boolean isActive;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendances = new ArrayList<>();
}
