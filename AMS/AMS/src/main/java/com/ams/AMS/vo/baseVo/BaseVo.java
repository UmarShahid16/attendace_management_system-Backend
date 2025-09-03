package com.ams.AMS.vo.baseVo;

import com.ams.AMS.util.dateFormate.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

@Data
public class BaseVo {
    private Long id;
    private String createdBy;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdAt;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date modifiedAt;
}
