package com.ams.AMS.vo.departmentVo;

import com.ams.AMS.entities.Department.Department;
import com.ams.AMS.vo.baseVo.BaseVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentVo  extends BaseVo {
    private String departmentName;
    private String description;

    public static DepartmentVo setResponse (Department department) {
        DepartmentVo departmentVo = new DepartmentVo();
        if(department != null){
            departmentVo.setId(department.getId());
            departmentVo.setDepartmentName(department.getDepartmentName());
            departmentVo.setDescription(department.getDescription());
            departmentVo.setCreatedAt(department.getCreatedAt());
            if(department.getCreatedBy() != null){
                departmentVo.setCreatedBy(department.getCreatedBy());
            }
            if(department.getModifiedAt() != null){
                departmentVo.setModifiedAt(department.getModifiedAt());
            }
        }
        return departmentVo;
    }
}
