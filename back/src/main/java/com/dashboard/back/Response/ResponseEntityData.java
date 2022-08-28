package com.dashboard.back.response;

import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseEntityData {
    public static ResponseEntity<?> CreateReponse(int httpStatus, String message, Object data,
            Map<String, String> headerData) {
        BaseResponseMessage responseMessage = new BaseResponseMessage();
        HttpHeaders headers = new HttpHeaders();

        if (headerData != null) {
            headerData.forEach((key, value) -> {
                if (value != null)
                    headers.add(key, value);
            });
        }
        ;

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        responseMessage.setStatus(httpStatus);
        responseMessage.setMessage(message);
        responseMessage.setData(data);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK.value());
    }
}
