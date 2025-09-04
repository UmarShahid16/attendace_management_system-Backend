package com.ams.AMS.vo.rolesVo;

import com.ams.AMS.entities.roles.Roles;
import com.ams.AMS.vo.baseVo.BaseVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolesVo extends BaseVo {
    private String roleName;
    private String roleDescription;
    private Boolean isActive;

    public static RolesVo getRolesVo(Roles role){
        RolesVo rolesVo = new RolesVo();
        if(role != null){
            rolesVo.setId(role.getId());
            rolesVo.setRoleName(role.getRoleName());
            rolesVo.setRoleDescription(role.getDescription());
            rolesVo.setIsActive(role.getIsActive());
            rolesVo.setCreatedAt(role.getCreatedAt());
            if(role.getModifiedAt() != null){
                rolesVo.setModifiedAt(role.getModifiedAt());
            }
            if(role.getCreatedBy() != null){
                rolesVo.setCreatedBy(role.getCreatedBy());
            }
        }
        return rolesVo;
    }
}
