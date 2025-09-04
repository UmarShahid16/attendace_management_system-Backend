package com.ams.AMS.vo.leavesVo;

import com.ams.AMS.entities.leave.Leaves;
import com.ams.AMS.vo.baseVo.BaseVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeavesVo  extends BaseVo {
    private String leaveType;
    private String leaveDescription;
    private Long  maxDays;


    public static LeavesVo setResponse(Leaves leaves) {
       LeavesVo vo = new LeavesVo();
       if(leaves != null) {
           vo.setId(leaves.getId());
           vo.setLeaveType(leaves.getLeaveType());
           vo.setLeaveDescription(leaves.getDescription());
           vo.setMaxDays(leaves.getMaxDays());
           vo.setCreatedAt(leaves.getCreatedAt());
           if(leaves.getCreatedBy() != null){
               vo.setCreatedBy(leaves.getCreatedBy());
           }
           if(leaves.getModifiedAt() != null){
               vo.setModifiedAt(leaves.getModifiedAt());
           }
       }
        return vo;
    }
}
