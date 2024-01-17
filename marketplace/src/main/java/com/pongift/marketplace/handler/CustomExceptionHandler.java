package com.pongift.marketplace.handler;

import com.pongift.common.data.model.domain.res.Response;
import com.pongift.common.error.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = {Exception.class, ApiException.class})
    protected ResponseEntity<Response> handleApiException(Exception ex, WebRequest request) {
        Response responseResult = new Response();
        if (ex instanceof ApiException) {
            responseResult.Error();
            responseResult.setMessage(ex.getMessage());
        } else {
            responseResult.Error();
            responseResult.setMessage("오류가 발생하였습니다.");
        }
        ex.printStackTrace();

        return new ResponseEntity<Response>(responseResult, HttpStatus.OK);
    }
}
