package com.ams.AMS.exceptions;

import lombok.Getter;

@Getter
public enum DAOResponse {
    USER_NOT_FOUND("AMS_ERROR_05", "User not found"),
    SUCCESS("AMS_SUCCESS_00", "Success"),
    INVALID_REQUEST("AMS_ERROR_97", "Invalid request parameters"),
    SYSTEM_ERROR("AMS_ERROR_98","System Under Maintenance" ),
    NO_DATA_FOUND("AMS_ERROR_2","No Data Found" ),
    USER_NOT_ACTIVE("AMS_ERROR_06", "User is not active"),
    INVALID_CREDENTIALS("AMS_ERROR_07", "Invalid credentials"),
    EMAIL_ALREADY_EXISTS("AMS_ERROR_08", "Email Already exists"),
    UNAUTHORIZED("AMS_ERROR_09", "Unauthorized access"),
    ROLE_NOT_FOUND("AMS_ERROR_10", "Role not found"),
    DEPARTMENT_NOT_FOUND("AMS_ERROR_11", "Department not found");

    private final String code;
    private final String message;

    DAOResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
