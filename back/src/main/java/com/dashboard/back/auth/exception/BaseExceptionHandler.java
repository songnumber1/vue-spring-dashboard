package com.dashboard.back.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ControllerAdvice
public class BaseExceptionHandler {
	@ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(AccountException.class)
    public ResponseEntity<Error> exception(AccountException exception){
        return new ResponseEntity<>(Error.create(exception.getExceptionType()), HttpStatus.OK);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Error{
        private int code;
        private int status;
        private String message;

        static Error create(BaseExceptionType exception){
           return new Error(exception.getErrorCode(), exception.getHttpStatus(), exception.getErrorMessage());
        }
    }
}
