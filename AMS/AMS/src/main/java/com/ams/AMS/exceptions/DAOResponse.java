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
    DEPARTMENT_NOT_FOUND("AMS_ERROR_11", "Department not found")
    ,USER_ID_REQUIRED("AMS_ERROR_12", "User ID is required"),
    LEAVE_LOG_NOT_FOUND("AMS_ERROR_13", "Leave log not found"),
    RECORD_NOT_FOUND("AMS_ERROR_14", "Record not found"),
    LEAVE_CANNOT_BE_CANCELLED("AMS_ERROR_15", "Approved leaves cannot be cancelled"),
    IMAGE_URL_REQUIRED("AMS_ERROR_16", "Image URL is required"),
    FACE_NOT_RECOGNIZED("AMS_ERROR_17", "Face not recognized or confidence too low"),
    ATTENDANCE_NOT_FOUND("AMS_ERROR_18", "Attendance record not found"),;

    private final String code;
    private final String message;

    DAOResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
