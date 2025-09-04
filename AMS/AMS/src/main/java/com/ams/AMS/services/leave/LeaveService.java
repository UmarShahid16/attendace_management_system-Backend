package com.ams.AMS.services.leave;

import com.ams.AMS.entities.User.User;
import com.ams.AMS.entities.leave.Leaves;
import com.ams.AMS.exceptions.DAOResponse;
import com.ams.AMS.repository.leave.LeaveRepository;
import com.ams.AMS.repository.user.UserRepository;
import com.ams.AMS.util.response.Response;
import com.ams.AMS.vo.leavesVo.LeavesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private UserRepository userRepository;

    public static final Logger logger = Logger.getLogger(LeaveService.class.getName());

    public Response applyLeave(LeavesVo leavesVo){
        Response response = new Response();
        Leaves leaves = new Leaves();
        try{
            if(leavesVo == null){
                response.setResponse(DAOResponse.INVALID_REQUEST);
                return response;
            }
            if(leavesVo.getUserId() == null){
                response.setResponse(DAOResponse.USER_ID_REQUIRED);
                return response;
            }

            User userById = userRepository.findUserById(leavesVo.getUserId());
            if(userById == null){
                response.setResponse(DAOResponse.USER_NOT_FOUND);
                return response;
            }
            leaves.setUser(userById);
            leaves.setLeaveType(leavesVo.getLeaveType());
            leaves.setDescription(leavesVo.getLeaveDescription());
            leaves.setMaxDays(leavesVo.getMaxDays());
            leaves.setStatus(leavesVo.getStatus());
            leaves.setCreatedAt(new Date());
            if(leavesVo.getEndDate() != null){
                leaves.setEndDate(leavesVo.getEndDate());
            }
            leaves.setStartDate(leavesVo.getStartDate());

            Leaves savedLeave = leaveRepository.save(leaves);
            LeavesVo leavesResponse = LeavesVo.setResponse(savedLeave);

            response.setResponse(DAOResponse.SUCCESS);
            response.setData("leaves", leavesResponse);
        }catch (Exception e){
            e.printStackTrace();
            logger.severe("Error in applyLeave: " + e.getMessage());
        }
        return response;
    }

    public Response leaveList(Long pageNo, Long pageSize){
        Response response = new Response();
        try{
            pageNo = pageNo == null ? 0 : pageNo;
            pageSize = pageSize == null ? 10 : pageSize;
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            Pageable pageable = PageRequest.of(Math.toIntExact(pageNo), Math.toIntExact(pageSize), sort);
            Page<Leaves> page;

            page = leaveRepository.findAll(pageable);
            if(page == null || page.getContent().isEmpty()){
                response.setResponse(DAOResponse.NO_DATA_FOUND);
                return response;
            }

            List<LeavesVo> list = page.getContent().stream().map(LeavesVo::setResponse).toList();
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("data", list);
            map.put("total", page.getTotalElements());
            map.put("totalPages", page.getTotalPages());
            map.put("currentPage", page.getNumber());

            response.setResponse(DAOResponse.SUCCESS);
            response.setData("leaves", map);
        }catch (Exception e){
            e.printStackTrace();
            logger.severe("Error in leaveList: " + e.getMessage());
        }
        return response;
    }


    public Response leavesByUserId(Long byUserId, Long pageNo, Long pageSize){
        Response response = new Response();
        try{
            if(byUserId == null){
                response.setResponse(DAOResponse.USER_ID_REQUIRED);
                return response;
            }
            pageNo = pageNo == null ? 0 : pageNo;
            pageSize = pageSize == null ? 10 : pageSize;
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            Pageable pageable = PageRequest.of(Math.toIntExact(pageNo), Math.toIntExact(pageSize), sort);
            Page<Leaves> page;


            page = leaveRepository.findLeavesByUserId(byUserId, pageable);
            if(page == null){
                response.setResponse(DAOResponse.NO_DATA_FOUND);
                return response;
            }

            List<LeavesVo> list = page.getContent().stream().map(LeavesVo::setResponse).toList();
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("data", list);
            map.put("total", page.getTotalElements());
            map.put("totalPages", page.getTotalPages());
            map.put("currentPage", page.getNumber());

            response.setResponse(DAOResponse.SUCCESS);
            response.setData("leaves", map);
        }catch (Exception e){
            e.printStackTrace();
            logger.severe("Error in leaveByUserId: " + e.getMessage());
        }
        return response;
    }

    public Response updateLeaveStatus(LeavesVo leavesVo){
        Response response = new Response();
        try{
            if(leavesVo == null){
                response.setResponse(DAOResponse.INVALID_REQUEST);
                return response;
            }
            Leaves leavesById = leaveRepository.findLeavesById(leavesVo.getId());
            if(leavesById == null){
                response.setResponse(DAOResponse.NO_DATA_FOUND);
                return response;
            }
            leavesById.setStatus(leavesVo.getStatus());
            Leaves updatedLeave = leaveRepository.save(leavesById);

            LeavesVo leaves = LeavesVo.setResponse(updatedLeave);

            response.setResponse(DAOResponse.SUCCESS);
            response.setData("leaves", leaves);
        }catch (Exception e){
            e.printStackTrace();
            logger.severe("Error in updateLeaveStatus: " + e.getMessage());
        }
        return response;
    }

    public Response getLeaveById(Long leaveId){
        Response response = new Response();
        try{
            if(leaveId == null){
                response.setResponse(DAOResponse.INVALID_REQUEST);
                return response;
            }
            Leaves leavesById = leaveRepository.findLeavesById(leaveId);
            if(leavesById == null){
                response.setResponse(DAOResponse.NO_DATA_FOUND);
                return response;
            }
            LeavesVo leaves = LeavesVo.setResponse(leavesById);

            response.setResponse(DAOResponse.SUCCESS);
            response.setData("leaves", leaves);
        }catch (Exception e){
            e.printStackTrace();
            logger.severe("Error in getLeaveById: " + e.getMessage());
        }
        return response;
    }

    public Response pendingLeaves(String status, Long pageNo, Long pageSize){
        Response response = new Response();
        try{
            if(status == null || status.isEmpty()){
                response.setCode("400");
                response.setMessage("Status is required");
                return response;
            }
            pageNo = pageNo == null ? 0 : pageNo;
            pageSize = pageSize == null ? 10 : pageSize;
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            Pageable pageable = PageRequest.of(Math.toIntExact(pageNo), Math.toIntExact(pageSize), sort);
            Page<Leaves> page;

            page = leaveRepository.findLeavesByStatus(status, pageable);
            if(page == null || page.getContent().isEmpty()){
                response.setResponse(DAOResponse.NO_DATA_FOUND);
                return response;
            }

            List<LeavesVo> list = page.getContent().stream().map(LeavesVo::setResponse).toList();
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("data", list);
            map.put("total", page.getTotalElements());
            map.put("totalPages", page.getTotalPages());
            map.put("currentPage", page.getNumber());

            response.setResponse(DAOResponse.SUCCESS);
            response.setData("leaves", map);
        }catch (Exception e){
            e.printStackTrace();
            logger.severe("Error in pendingLeaves: " + e.getMessage());
        }
        return response;
    }

    public Response leavesByDateRange(String startDate, String endDate, Long pageNo, Long pageSize){
        Response response = new Response();
        try{
            if(startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()){
                response.setResponse(DAOResponse.INVALID_REQUEST);
                return response;
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate1 = simpleDateFormat.parse(startDate);
            Date endDate1 = simpleDateFormat.parse(endDate);

            pageNo = pageNo == null ? 0 : pageNo;
            pageSize = pageSize == null ? 10 : pageSize;
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            Pageable pageable = PageRequest.of(Math.toIntExact(pageNo), Math.toIntExact(pageSize), sort);
            Page<Leaves> page;

            page = leaveRepository.findLeavesByStartDateAndEndDate(startDate1, endDate1, pageable);
            if (page == null || page.getContent().isEmpty()) {
                response.setResponse(DAOResponse.NO_DATA_FOUND);
                return response;
            }

            List<LeavesVo> list = page.getContent().stream().map(LeavesVo::setResponse).toList();
            Map<String, Object> map = new LinkedHashMap<>();

            map.put("data", list);
            map.put("total", page.getTotalElements());
            map.put("totalPages", page.getTotalPages());
            map.put("currentPage", page.getNumber());
            response.setResponse(DAOResponse.SUCCESS);
            response.setData("leaves", map);
        }catch (Exception e){
            e.printStackTrace();
            logger.severe("Error in leavesByDateRange: " + e.getMessage());
        }
        return response;
    }

    public Response cancelLeave(LeavesVo leavesVo){
        Response response = new Response();
        try{
            if(leavesVo == null){
                response.setResponse(DAOResponse.INVALID_REQUEST);
                return response;
            }
            Leaves leavesById = leaveRepository.findLeavesById(leavesVo.getId());
            if(leavesById == null){
                response.setResponse(DAOResponse.NO_DATA_FOUND);
                return response;
            }
            leavesById.setStatus(leavesVo.getStatus());
            Leaves cancelledLeave = leaveRepository.save(leavesById);

            LeavesVo leaves = LeavesVo.setResponse(cancelledLeave);

            response.setResponse(DAOResponse.SUCCESS);
            response.setData("leaves", leaves);
        }catch (Exception e){
            e.printStackTrace();
            logger.severe("Error in cancelLeave: " + e.getMessage());
        }
        return response;
    }
}
