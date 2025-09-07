package com.ams.AMS.services.holiday;

import com.ams.AMS.entities.holiday.Holidays;
import com.ams.AMS.exceptions.DAOResponse;
import com.ams.AMS.repository.holiday.HolidayRepository;
import com.ams.AMS.util.response.Response;
import com.ams.AMS.vo.holidayVo.HolidayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class HolidayService {
    public static final Logger logger = Logger.getLogger(HolidayService.class.getName());
    @Autowired
    private HolidayRepository holidayRepository;


    public Response addHoliday(HolidayVo holidayVo) {
        Response response = new Response();
        try{
            if(holidayVo.getId() == null){
                Holidays holiday = new Holidays();
                holiday.setName(holidayVo.getHolidayName());
                holiday.setHolidayDate(holidayVo.getHolidayDate());
                holiday.setDescription(holidayVo.getHolidayDescription());
                holiday.setIsActive(holidayVo.getIsActive());
                Holidays save = holidayRepository.save(holiday);
                response.setResponse(DAOResponse.SUCCESS);
                response.setData("holiday", HolidayVo.setResponse(save));
            }else{
                Holidays holidays = holidayRepository.findHolidaysById(holidayVo.getId());
                if(holidays != null) {
                    holidays.setName(holidayVo.getHolidayName());
                    holidays.setHolidayDate(holidayVo.getHolidayDate());
                    holidays.setDescription(holidayVo.getHolidayDescription());
                    holidays.setIsActive(holidayVo.getIsActive());
                    Holidays save = holidayRepository.save(holidays);
                    response.setResponse(DAOResponse.SUCCESS);
                    response.setData("holiday", HolidayVo.setResponse(save));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.severe("Error adding holiday: " + e.getMessage());
        }
        return response;
    }

    public Response holidayList(Long pageNo, Long pageSize) {
        Response response = new Response();
        try{
            pageNo = pageNo == null ? 0 : pageNo;
            pageSize = pageSize == null ? 10 : pageSize;
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            Pageable pageable = PageRequest.of(Math.toIntExact(pageSize), Math.toIntExact(pageNo), sort);
            Page<Holidays> page;

            page = holidayRepository.findAll(pageable);
            List<HolidayVo> list = page.getContent().stream().map(HolidayVo::setResponse).toList();
            Map<String , Object> map = new LinkedHashMap<>();
            map.put("holidays", list);
            map.put("currentPage", page.getNumber());
            map.put("totalElements", page.getTotalElements());
            map.put("totalPages", page.getTotalPages());

            response.setResponse(DAOResponse.SUCCESS);
            response.setData("holiday", map);
        }catch (Exception e){
            e.printStackTrace();
            logger.severe("Error fetching holiday list: " + e.getMessage());
        }
        return response;
    }

    // get by ID
    public Response getHolidayById(Long holidayId) {
        Response response = new Response();
        try{
            Holidays holidays = holidayRepository.findHolidaysById(holidayId);
            if(holidays != null){
                response.setResponse(DAOResponse.SUCCESS);
                response.setData("holiday", HolidayVo.setResponse(holidays));
            }else{
                response.setResponse(DAOResponse.NO_DATA_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.severe("Error fetching holiday by ID: " + e.getMessage());
        }
        return response;
    }
//    what more left?
}
