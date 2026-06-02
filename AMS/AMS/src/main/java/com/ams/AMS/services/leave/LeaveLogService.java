package com.ams.AMS.services.leave;

import com.ams.AMS.entities.User.User;
import com.ams.AMS.entities.leave.LeavesLog;
import com.ams.AMS.exceptions.DAOResponse;
import com.ams.AMS.repository.leave.LeaveLogRepository;
import com.ams.AMS.repository.user.UserRepository;
import com.ams.AMS.util.response.Response;
import com.ams.AMS.vo.leavesVo.LeavesLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class LeaveLogService {
    public static final Logger logger = Logger.getLogger(LeaveLogService.class.getName());

    @Autowired
    private LeaveLogRepository leaveLogRepository;

    @Autowired
    private UserRepository userRepository;

    public Response createLeaveLog(LeavesLogVo leavesLogVo){
        Response response = new Response();
        try{
            if(leavesLogVo.getId() == null) {
                LeavesLog leavesLog = new LeavesLog();
                User user = userRepository.findUserById(leavesLogVo.getUserId());
                if(user == null) {
                    response.setResponse(DAOResponse.USER_NOT_FOUND);
                    return response;
                }
                leavesLog.setUser(user);
                leavesLog.setTotalLeaves(leavesLogVo.getTotalLeaves());
                leavesLog.setSickLeaves(0L);
                leavesLog.setCasualLeaves(0L);
                leavesLog.setAnnualLeaves(0L);
                leavesLog.setIsAllLeavesEncashed(false);
                leavesLog.setCreatedAt(new Date());
                leavesLog.setCreatedBy("System");

                leavesLog = leaveLogRepository.save(leavesLog);
                response.setResponse(DAOResponse.SUCCESS);
                response.setData("leavesLog", LeavesLogVo.setResponse(leavesLog));
            }else{
                LeavesLog leavesLog = leaveLogRepository.findLeavesLogByUserId(leavesLogVo.getUserId());
                if(leavesLog == null) {
                    response.setResponse(DAOResponse.LEAVE_LOG_NOT_FOUND);
                    return response;
                }
                leavesLog.setTotalLeaves(leavesLogVo.getTotalLeaves());
                leavesLog.setModifiedAt(new Date());
                leavesLog.setCreatedBy("System");

                leavesLog = leaveLogRepository.save(leavesLog);
                response.setResponse(DAOResponse.SUCCESS);
                response.setData("leavesLog", LeavesLogVo.setResponse(leavesLog));
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.severe("Error in leaveBalance: " + e.getMessage());
        }
        return response;
    }

    public Response leaveBalance(Long userId){
        Response response = new Response();
        Map<String, Object> leaveBalance = new LinkedHashMap<>();
        try{
            List<Object[]> stat = leaveLogRepository.getLeaveBalanceByUserId(userId);
            if(!stat.isEmpty()){
              Object[] stats = stat.get(0);
                Long totalLeaves = stats[0] != null ? ((Number) stats[0]).longValue() : 0L;
                Long sickLeaves = stats[1] != null ? ((Number) stats[1]).longValue() : 0L;
                Long casualLeaves = stats[2] != null ? ((Number) stats[2]).longValue() : 0L;
                Long earnedLeaves = stats[3] != null ? ((Number) stats[3]).longValue() : 0L;
                Long takenLeaves = stats[4] != null ? ((Number) stats[4]).longValue() : 0L;
                Long remainingLeaves = stats[5] != null ? ((Number) stats[5]).longValue() : 0L;

                leaveBalance.put("totalLeaves", totalLeaves);
                leaveBalance.put("sickLeaves", sickLeaves);
                leaveBalance.put("casualLeaves", casualLeaves);
                leaveBalance.put("earnedLeaves", earnedLeaves);
                leaveBalance.put("takenLeaves", takenLeaves);
                leaveBalance.put("remainingLeaves", remainingLeaves);
            }

            response.setResponse(DAOResponse.SUCCESS);
            response.setData("leaveBalance", leaveBalance);
        }catch (Exception e){
            e.printStackTrace();
            logger.severe("Error in leaveBalance: " + e.getMessage());
        }
        return response;
    }
    
    public Response getLeaves(){
        Response response = new Response();
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String email = authentication.getName();

            User user = userRepository.findByEmail(email);

            LeavesLog leaves = leaveLogRepository.findLeavesLogByUserId(user.getId());
            LeavesLogVo leavesLogVo = LeavesLogVo.setResponse(leaves);

            response.setResponse(DAOResponse.SUCCESS);
            response.setData("data", leavesLogVo);
            return response;

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
