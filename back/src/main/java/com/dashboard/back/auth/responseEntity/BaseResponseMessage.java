package com.dashboard.back.auth.responseEntity;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class BaseResponseMessage {
	private int status;
    private String message;
    private Object data;

    public BaseResponseMessage() {
        this.status = HttpStatus.OK.value();
        this.data = null;
        this.message = null;
    }
}
