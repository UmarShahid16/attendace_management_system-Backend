package com.ams.AMS.vo.holidayVo;

import com.ams.AMS.entities.holiday.Holidays;
import com.ams.AMS.vo.baseVo.BaseVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HolidayVo  extends BaseVo {
    private String holidayName;
    private Date holidayDate;
    private String holidayDescription;
    private Boolean isActive;


    public static HolidayVo setResponse (Holidays holiday){
        HolidayVo holidayVo = new HolidayVo();
        if(holiday != null){
            holidayVo.setId(holiday.getId());
            holidayVo.setHolidayName(holiday.getName());
            holidayVo.setHolidayDate(holiday.getHolidayDate());
            holidayVo.setHolidayDescription(holiday.getDescription());
            holidayVo.setCreatedAt(holiday.getCreatedAt());
            holidayVo.setIsActive(holiday.getIsActive());
            if(holiday.getCreatedBy() != null){
                holidayVo.setCreatedBy(holiday.getCreatedBy());
            }
            if(holiday.getModifiedAt() != null){
                holidayVo.setModifiedAt(holiday.getModifiedAt());
            }
        }
        return holidayVo;
    }
}
