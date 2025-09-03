package com.ams.AMS.exceptions;

import lombok.Getter;

@Getter
public class DAOException extends RuntimeException{
    private final DAOResponse error;

    public DAOException(DAOResponse error) {
        super(error.getMessage());
        this.error = error;
    }

    public String getCode() {
        return error.getCode();
    }

    public String getErrorMessage() {
        return error.getMessage();
    }

}
