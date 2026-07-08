package com.ams.AMS.vo.leavesVo;

import com.ams.AMS.entities.leave.Leaves;
import com.ams.AMS.vo.baseVo.BaseVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeavesVo  extends BaseVo {
    private Long userId;
    private String userName;
    private String leaveType;
    private String leaveDescription;
    private Long  maxDays;
    private String status;
    private Date startDate;
    private Date endDate;
    private String email;




    public static LeavesVo setResponse(Leaves leaves) {
       LeavesVo vo = new LeavesVo();
       if(leaves != null) {
           vo.setId(leaves.getId());
           vo.setLeaveType(leaves.getLeaveType());
           vo.setLeaveDescription(leaves.getDescription());
           vo.setMaxDays(leaves.getMaxDays());
           vo.setCreatedAt(leaves.getCreatedAt());
           vo.setStatus(leaves.getStatus());
           if(leaves.getUser() != null){
               vo.setUserId(leaves.getUser().getId());
               vo.setUserName(leaves.getUser().getFirstName() + " " + leaves.getUser().getLastName());
               vo.setEmail(leaves.getUser().getEmail());
           }
           if(leaves.getCreatedBy() != null){
               vo.setCreatedBy(leaves.getCreatedBy());
           }
           if(leaves.getModifiedAt() != null){
               vo.setModifiedAt(leaves.getModifiedAt());
           }
           if(leaves.getStartDate() != null){
                vo.setStartDate(leaves.getStartDate());
           }
           if(leaves.getEndDate() != null){
               vo.setEndDate(leaves.getEndDate());
           }
       }
        return vo;
    }
}
