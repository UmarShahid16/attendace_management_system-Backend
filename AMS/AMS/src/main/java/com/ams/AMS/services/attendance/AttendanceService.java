package com.ams.AMS.services.attendance;

import com.ams.AMS.entities.User.User;
import com.ams.AMS.entities.attendace.Attendance;
import com.ams.AMS.exceptions.DAOResponse;
import com.ams.AMS.repository.attendance.AttendanceRepository;
import com.ams.AMS.repository.user.UserRepository;
import com.ams.AMS.util.response.Response;
import com.ams.AMS.util.skybiometry.SkyBiometryService;
import com.ams.AMS.vo.attendanceVo.AttendanceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkyBiometryService skyBiometryService;

    public Response markAttendance(AttendanceVo attendanceVo) {
        Response response = new Response();
        try {
            String imageUrl = attendanceVo.getImageUrl();

            if (imageUrl == null || imageUrl.isEmpty()) {
                response.setResponse(DAOResponse.INVALID_REQUEST);
                return response;
            }

            String userIdStr = skyBiometryService.verifyFace(imageUrl);

            if (userIdStr != null) {
                Long userId = Long.valueOf(userIdStr);
                User user = userRepository.findUserById(userId);

                if (user != null) {
                    Attendance attendance = new Attendance();
                    attendance.setUser(user);
                    attendance.setCheck_in_time(LocalTime.now());
                    attendance.setStatus("Present");
                    Attendance save = attendanceRepository.save(attendance);
                    AttendanceVo attendanceVo1 = AttendanceVo.setResponse(save);
                    response.setData("data", attendanceVo1);

                    response.setResponse(DAOResponse.SUCCESS);
                    return response;
                }
            }

            response.setResponse(DAOResponse.FACE_NOT_RECOGNIZED);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(DAOResponse.SYSTEM_ERROR);
        }
        return response;
    }

    public Response markCheckOut(Long userId){
        Response response = new Response();
        try {
            if(userId == null){
                response.setResponse(DAOResponse.INVALID_REQUEST);
                return response;
            }

            Attendance attendance = attendanceRepository.findUserAttendance(userId);
            if(attendance != null) {
                LocalTime checkInTime = attendance.getCheck_in_time();
                LocalTime checkOutTime = LocalTime.now();

                if (checkInTime != null) {
                    int totalSeconds = checkOutTime.toSecondOfDay() - checkInTime.toSecondOfDay();
                    double dailyHours = totalSeconds / 3600.0;

                    int hours = totalSeconds / 3600;
                    int minutes = (totalSeconds % 3600) / 60;
                    int seconds = totalSeconds % 60;

                    String workDuration = String.format("%dh %dm %ds", hours, minutes, seconds);
                    attendance.setWorkHours(workDuration);
                    attendance.setDailyWorkingHours(dailyHours);
                }

                attendance.setCheck_out_time(checkOutTime);

                Attendance save = attendanceRepository.save(attendance);
                AttendanceVo attendanceVo1 = AttendanceVo.setResponse(save);
                response.setData("data", attendanceVo1);
                response.setResponse(DAOResponse.SUCCESS);
            }else{
                response.setResponse(DAOResponse.ATTENDANCE_NOT_FOUND);
            }
        }catch (Exception e) {
            e.printStackTrace();
            response.setResponse(DAOResponse.SYSTEM_ERROR);
        }
        return response;
    }


    public Response monthlyAttendanceByUserId(Long userId, Integer month, Integer year){
        Response response = new Response();
        try {
            if(userId == null || month == null || year == null){
                response.setResponse(DAOResponse.INVALID_REQUEST);
                return response;
            }
            List<Attendance> attendanceList = attendanceRepository.findMonthlyAttendanceByUserId(userId, month, year);
            if(attendanceList.isEmpty()){
                response.setResponse(DAOResponse.NO_DATA_FOUND);
                return response;
            }
            List<AttendanceVo> attendanceVos = attendanceList.stream().map(AttendanceVo::setResponse).toList();
            attendanceVos.forEach(a ->
                    System.out.println("Date: " + a.getCreatedAt() + " Hours: " + a.getDailyWorkingHours()));

            double totalMonthlyHours = attendanceVos.stream()
                    .filter(a -> a.getDailyWorkingHours() != null)
                    .mapToDouble(AttendanceVo::getDailyWorkingHours)
                    .sum();

            String formattedMonthlyHours = AttendanceVo.formatHours(totalMonthlyHours);

            Map<String, Object> map = new LinkedHashMap<>();
            map.put("userId", userId);
            map.put("month", month);
            map.put("year", year);
            map.put("totalDays", attendanceVos.size());
            map.put("totalMonthlyHours", formattedMonthlyHours);
            map.put("attendances", attendanceVos);
            response.setData("data", map);
            response.setResponse(DAOResponse.SUCCESS);
        }catch (Exception e) {
            e.printStackTrace();
            response.setResponse(DAOResponse.SYSTEM_ERROR);
        }
        return response;
    }

    public Response dailyAttendance(String date){
        Response response = new Response();
        try {
            if(date == null || date.isEmpty()){
                response.setResponse(DAOResponse.INVALID_REQUEST);
                return response;
            }
            String formatted = date.formatted("yyyy-MM-dd");
            List<Attendance> attendanceList = attendanceRepository.findDailyAttendance(formatted);
            if(attendanceList.isEmpty()){
                response.setResponse(DAOResponse.NO_DATA_FOUND);
                return response;
            }
            List<AttendanceVo> attendanceVos = attendanceList.stream().map(AttendanceVo::setResponse).toList();
            response.setData("data", attendanceVos);
            response.setResponse(DAOResponse.SUCCESS);
        }catch (Exception e) {
            e.printStackTrace();
            response.setResponse(DAOResponse.SYSTEM_ERROR);
        }
        return response;
    }

    public Response allAttendance(Long pageNo, Long pageSize){
        Response response = new Response();
        try {
            pageNo = (pageNo == null || pageNo < 0) ? 0 : pageNo;
            pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
            Pageable pageable = PageRequest.of(Math.toIntExact(pageNo), Math.toIntExact(pageSize));

             Page<Attendance> attendanceList = attendanceRepository.findAll(pageable);

            if(attendanceList.isEmpty()){
                response.setResponse(DAOResponse.NO_DATA_FOUND);
                return response;
            }
            List<AttendanceVo> attendanceVos = attendanceList.stream().map(AttendanceVo::setResponse).toList();
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("data", attendanceVos);
            map.put("currentPage", attendanceList.getNumber());
            map.put("totalItems", attendanceList.getTotalElements());
            map.put("totalPages", attendanceList.getTotalPages());
            map.put("pageSize", attendanceList.getSize());
            map.put("pageNo", attendanceList.getNumber());
            response.setData("data", map);
            response.setResponse(DAOResponse.SUCCESS);
        }catch (Exception e) {
            e.printStackTrace();
            response.setResponse(DAOResponse.SYSTEM_ERROR);
        }
        return response;
    }

    //monthly report of all users
    public Response monthlyReportAllUsers(Integer month, Integer year, Long pageNo, Long pageSize){
        Response response = new Response();
        try {
            if(month == null || year == null){
                response.setResponse(DAOResponse.INVALID_REQUEST);
                return response;
            }
            pageNo = (pageNo == null || pageNo < 0) ? 0 : pageNo;
            pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
            Pageable pageable = PageRequest.of(Math.toIntExact(pageNo), Math.toIntExact(pageSize));

            Page<User> allUsers = userRepository.findAll(pageable);

            Map<String, Object> reportMap = new LinkedHashMap<>();
            for(User user : allUsers){
                List<Attendance> attendanceList = attendanceRepository.findMonthlyAttendanceByUserId(user.getId(), month, year);
                if(!attendanceList.isEmpty()){
                    List<AttendanceVo> attendanceVos = attendanceList.stream().map(AttendanceVo::setResponse).toList();
                    double totalMonthlyHours = attendanceVos.stream()
                            .filter(a -> a.getDailyWorkingHours() != null)
                            .mapToDouble(AttendanceVo::getDailyWorkingHours)
                            .sum();

                    String formattedMonthlyHours = AttendanceVo.formatHours(totalMonthlyHours);

                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("userId", user.getId());
                    map.put("name", user.getFirstName());
                    map.put("email", user.getEmail());
                    map.put("month", month);
                    map.put("year", year);
                    map.put("totalDays", attendanceVos.size());
                    map.put("totalMonthlyHours", formattedMonthlyHours);
                    map.put("attendances", attendanceVos);
                    reportMap.put("userId "+ user.getId(), map);
                }
            }
            if(reportMap.isEmpty()){
                response.setResponse(DAOResponse.NO_DATA_FOUND);
                return response;
            }
            response.setData("data", reportMap);
            response.setResponse(DAOResponse.SUCCESS);
        }catch (Exception e) {
            e.printStackTrace();
            response.setResponse(DAOResponse.SYSTEM_ERROR);
        }
        return response;
    }

}
