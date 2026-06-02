package com.ams.AMS.vo.leavesVo;

import com.ams.AMS.entities.leave.LeavesLog;
import com.ams.AMS.vo.baseVo.BaseVo;
import lombok.Data;

@Data
public class LeavesLogVo extends BaseVo {
    private Long userId;
    private String userName;
    private Long totalLeaves;
    private Long sickLeaves;
    private Long casualLeaves;
    private Long annualLeaves;
    private Boolean isAllLeavesEncashed;

    public static LeavesLogVo setResponse(LeavesLog leavesLog) {
        LeavesLogVo vo = new LeavesLogVo();
        if(leavesLog != null) {
            vo.setId(leavesLog.getId());
            vo.setCreatedAt(leavesLog.getCreatedAt());
            if(leavesLog.getUser() != null){
                vo.setUserId(leavesLog.getUser().getId());
                vo.setUserName(leavesLog.getUser().getFirstName() + " " + leavesLog.getUser().getLastName());
            }
            if(leavesLog.getCreatedBy() != null){
                vo.setCreatedBy(leavesLog.getCreatedBy());
            }
            if(leavesLog.getModifiedAt() != null){
                vo.setModifiedAt(leavesLog.getModifiedAt());
            }
            vo.setTotalLeaves(leavesLog.getTotalLeaves());
            vo.setSickLeaves(leavesLog.getSickLeaves());
            vo.setCasualLeaves(leavesLog.getCasualLeaves());
            vo.setAnnualLeaves(leavesLog.getAnnualLeaves());
            vo.setIsAllLeavesEncashed(leavesLog.getIsAllLeavesEncashed());
        }
        return vo;
    }
}
