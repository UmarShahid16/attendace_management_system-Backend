package com.ams.AMS.controller.holiday;

import com.ams.AMS.services.holiday.HolidayService;
import com.ams.AMS.util.response.Response;
import com.ams.AMS.vo.holidayVo.HolidayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/holidays")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;


    @PostMapping("/add")
    public ResponseEntity<?> addHoliday(HolidayVo holidayVo){
        Response response = holidayService.addHoliday(holidayVo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listHoliday(@RequestParam(required = false) Long pageNo, @RequestParam(required = false) Long pageSize){
        Response response = holidayService.holidayList(pageNo, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHoliday(@PathVariable Long id){
        Response response = holidayService.getHolidayById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
