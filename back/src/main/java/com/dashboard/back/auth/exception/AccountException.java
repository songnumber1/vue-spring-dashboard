package com.dashboard.back.auth.exception;

import lombok.Getter;

public class AccountException extends RuntimeException {
	@Getter
    private BaseExceptionType exceptionType;

    public AccountException(BaseExceptionType exceptionType){
        super(exceptionType.getErrorMessage());
        this.exceptionType = exceptionType;
    }
}
